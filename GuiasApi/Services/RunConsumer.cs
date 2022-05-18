


namespace Guias.Servicio {
public static void RunConsumer()
        {
            var factory = new ConnectionFactory()
            {
                Uri = new System.Uri("amqps://jwrctuai:nqs_d_DfoxuIANsa94_Vis2-TFNqDjYE@whale.rmq.cloudamqp.com/jwrctuai")
            };
            var connection = factory.CreateConnection();
            var channel = connection.CreateModel();

            channel.QueueDeclare(queue: "dotnetq",durable: true,exclusive: false, autoDelete: false,arguments: null);

            channel.QueueBind(queue: "dotnetq",exchange: "amq.direct",routingKey: "arso",arguments: null);

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
    
    public static void handleEvento(String message){
        
        EventoValoracion ev = JsonToEvento(message);
        RepositorioGuias repo = new RepositorioGuiasMongoDB();

        string[] partes = evento.Url.Split("/api/guias/");
        Console.WriteLine("Consumidor : Valorado guia con id: "+idd);


        string idd = partes[1];      //split con el evento.url

        Guia guia = repo.GetById(idd);
        guia.NumValoraciones = ev.NumValoraciones;
        guia.MediaValoraciones = ev.MediaValoraciones;
        string url = "localhost:8090/opiniones" + ev.Url;
        guia.OpinionUrl = url;
        repo.Update(guia);
        
    }

    public static EventoValoracion JsonToEvento(string message){
        JsonSerializerOptions jso = new JsonSerializerOptions{PropertyNameCaseInsensitive=true};
        EventoValoracion ev = JsonSerializer.Deserialize<EventoValoracion>(message,options);
        return ev;
    }
}