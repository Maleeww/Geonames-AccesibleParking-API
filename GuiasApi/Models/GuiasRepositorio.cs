
using Repositorio;
using MongoDB.Driver;
using System.Collections.Generic;
using System.Linq;
using MongoDB.Bson;
using Guias.Modelo;

namespace Guias.Repositorio
{

    public interface RepositorioGuias : Repositorio<Guia, string>
    {

        // Se pueden incluir métodos adicionales
    }

    public class RepositorioGuiasMongoDB : RepositorioGuias
    {

        
        private readonly IMongoCollection<Guia> guias;

        public RepositorioGuiasMongoDB()
        {
            var uri = "mongodb+srv://malew:arso@cluster0.y5vc0.mongodb.net/?retryWrites=true&w=majority";
            var client = new MongoClient(uri);
            var database = client.GetDatabase("arso");

            guias = database.GetCollection<Guia>("guias.net");
        }

        public string Add(Guia entity)
        {
            guias.InsertOne(entity);

            return entity.Id;
        }

        public void Update(Guia entity)
        {
            guias.ReplaceOne(guia => guia.Id == entity.Id, entity);
        }

        public void Delete(Guia entity)
        {
            guias.DeleteOne(guia => guia.Id == entity.Id);
        }

        public List<Guia> GetAll()
        {
            return guias.Find(_ => true).ToList();
        }

        public Guia GetById(string id)
        {
            return guias
                .Find(guia => guia.Id == id)
                .FirstOrDefault();
        }

        
        public Guia GetByEmail(string email)
        {
            return guias
                .Find(guia => guia.Correo == email)
                .FirstOrDefault();
        }

        public List<string> GetIds()
        {
            var todas =  guias.Find(_ => true).ToList();

            return todas.Select(p => p.Id).ToList();

        }
    }
}