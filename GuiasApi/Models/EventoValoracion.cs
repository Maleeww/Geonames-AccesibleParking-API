
using System;
using System.Collections.Generic;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace Guias.Modelo {

    public class EventoValoracion {

        // Declaramos las propiedades

        public double MediaValoraciones { get; set;}
        public int NumValoraciones { get; set; }
        public string Url { get; set;}
        public Valoracion ValoracionRealizada { get; set;}

    }

}