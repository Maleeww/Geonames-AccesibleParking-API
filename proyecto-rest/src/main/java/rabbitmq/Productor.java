package rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Productor {

	public static void main(String[] args) throws Exception {

		String uri = "amqps://jwrctuai:nqs_d_DfoxuIANsa94_Vis2-TFNqDjYE@whale.rmq.cloudamqp.com/jwrctuai";

		ConnectionFactory factory = new ConnectionFactory();
	    factory.setUri(uri);


		Connection connection = factory.newConnection();

		Channel channel = connection.createChannel();

		/** Declaración del Exchange **/

		final String exchangeName = "amq.direct";
		
		boolean durable = true;
		channel.exchangeDeclare(exchangeName, "direct", durable);

		/** Envío del mensaje **/
		
		String mensaje = "hola";

		String routingKey = "arso";
		channel.basicPublish(exchangeName, routingKey, new AMQP.BasicProperties.Builder()
				// .contentType("application/json")
				.build(), mensaje.getBytes());

		channel.close();
		connection.close();

		System.out.println("fin.");

	}
}