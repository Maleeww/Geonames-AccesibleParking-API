
using System;
using System.Collections.Generic;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace Guias.Modelo {

    public class Guia {

        // Declaramos las propiedades

        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id { get; set;}
        public string Nombre { get; set; }
        public string Correo { get; set;}

        public List<String> SitiosInteres { get; set; } = new List<String>();


        public int NumValoraciones{get;set;}
        public string OpinionUrl{get;set;}
        public double MediaValoraciones{get;set;}

    }

    public class Valoracion
    {
        public string Email {get; set;}
        public int Nota{get; set;}
        public DateTime Fecha{get;set;}
    }

}