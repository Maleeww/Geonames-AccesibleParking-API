using Guias.Modelo;
using System;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System.Text;
using System.Text.Json;
using Guias.Repositorio;


namespace Guias.Servicio {
    public class Consumer{
public static void RunConsumer()
        {
            var factory = new ConnectionFactory()
            {
                Uri = new System.Uri("amqps://jwrctuai:nqs_d_DfoxuIANsa94_Vis2-TFNqDjYE@whale.rmq.cloudamqp.com/jwrctuai")
            };
            var connection = factory.CreateConnection();
            var channel = connection.CreateModel();

            channel.QueueDeclare(queue: "dotnetq",durable: true,exclusive: false, autoDelete: false,arguments: null);

            channel.QueueBind(queue: "dotnetq",exchange: "amq.direct",routingKey: "guias",arguments: null);

            var consumer = new EventingBasicConsumer(channel);
            consumer.Received += (model, ea) =>
            {
                var body = ea.Body.ToArray();
                var message = Encoding.UTF8.GetString(body);
                Console.WriteLine(message);
                Console.WriteLine("------------------------------");
                handleEvento(message);
            };
            channel.BasicConsume(queue: "dotnetq",
                                autoAck: true,
                                consumer: consumer);
            Console.WriteLine("Consumer running...");
        }
    
    public static void handleEvento(string message){
        
        EventoValoracion ev = JsonToEvento(message);
        RepositorioGuias repo = new RepositorioGuiasMongoDB();

        string[] partes = ev.Url.Split("/api/guias/");
                string idd = partes[1];      //split con el ev.url
        Console.WriteLine("Consumidor : Valorado guia con id: "+idd);




        Guia guia = repo.GetById(idd);
        guia.NumValoraciones = ev.NumValoraciones;
        guia.MediaValoraciones = ev.MediaValoraciones;
        string url = "localhost:8090/opiniones" + ev.Url;
        guia.OpinionUrl = url;
        repo.Update(guia);
        
    }

    public static EventoValoracion JsonToEvento(string message){
        JsonSerializerOptions jso = new JsonSerializerOptions{PropertyNameCaseInsensitive=true};
        EventoValoracion ev = JsonSerializer.Deserialize<EventoValoracion>(message,jso);
        return ev;
    }
    }
}