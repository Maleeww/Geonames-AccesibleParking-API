package rabbitmq;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.json.bind.Jsonb;
import javax.json.bind.spi.JsonbProvider;
import javax.servlet.http.HttpServlet;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import ciudades.repositorio.FactoriaRepositorioCiudades;
import ciudades.repositorio.RepositorioCiudades;
import es.um.ciudad.Aparcamiento;
import es.um.ciudad.Ciudad;
import opiniones.eventos.EventoValoracion;
import opiniones.repositorio.FactoriaRepositorioOpiniones;
import opiniones.repositorio.RepositorioOpiniones;
import repositorio.ElementoNoEncontrado;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;

public class Consumidor extends HttpServlet {

	public Consumidor() throws Exception {
		// TODO uri
		String uri = "amqps://jwrctuai:nqs_d_DfoxuIANsa94_Vis2-TFNqDjYE@whale.rmq.cloudamqp.com/jwrctuai";

		ConnectionFactory factory = new ConnectionFactory();
	    factory.setUri(uri);

	    Connection connection = factory.newConnection();

	    Channel channel = connection.createChannel();
	    
	    /** Declaración de la cola y enlace con el exchange **/

		final String exchangeName = "amq.direct";
		final String queueName = "q";
		final String bindingKey = "ciudades";

		boolean durable = true;
		boolean exclusive = false;
		boolean autodelete = false;
		Map<String, Object> properties = null; // sin propiedades
		channel.queueDeclare(queueName, durable, exclusive, autodelete, properties);

		channel.queueBind(queueName, exchangeName, bindingKey);
	    
		/** Configuración del consumidor **/
		
		boolean autoAck = false;
		String cola = "q";
		String etiquetaConsumidor = "arso-consumidor";
		
		// Consumidor push
		
		channel.basicConsume(cola, autoAck, etiquetaConsumidor, 
		  
		  new DefaultConsumer(channel) {
		    @Override
		    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
		            byte[] body) throws IOException {
		        
		        String routingKey = envelope.getRoutingKey();
		        String contentType = properties.getContentType();
		        long deliveryTag = envelope.getDeliveryTag();

		        String contenido = new String(body);
		        

				Jsonb contexto = JsonbProvider.provider().create().build();

			    EventoValoracion evento = contexto.fromJson(contenido, EventoValoracion.class);

			    // Procesamos el evento
			    // TODO Ciudad procesa el evento y actualiza sus aparcamientos

			    //Aparcamiento aparcamiento = evento.getUrl();
			    System.out.println(evento.getUrl());
			    
			    
				RepositorioCiudades repositorio = FactoriaRepositorioCiudades.getRepositorio();
				String ciudadString = evento.getUrl().split("/puntos")[0].split("ciudades/")[1];
				Ciudad ciudad = null;
				try {
					ciudad = repositorio.getById(ciudadString);
				} catch (RepositorioException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EntidadNoEncontrada e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				double lat = Double.parseDouble(evento.getUrl().split("aparcamientos/")[1].split("_")[0]);
	        	double log = Double.parseDouble(evento.getUrl().split("_")[1]);
	        	Aparcamiento ap = null;
	        	
	        	try {
					ap = repositorio.getAparcamiento(ciudad, lat, log);
				} catch (ElementoNoEncontrado e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
	        	ap.setCalificacionMedia(evento.getMedia());
	        	ap.setNumeroValoraciones(evento.getnumValoraciones());
	        	ap.setUrlOpinion(evento.getUrl());
	        	
		        System.out.println(contenido);
		        
		        // Confirma el procesamiento
		        channel.basicAck(deliveryTag, false);
		    }
		});
		
		}
	}
