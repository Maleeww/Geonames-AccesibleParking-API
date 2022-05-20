
using Guias.Modelo;
using Guias.Repositorio;
using System.Collections.Generic;

namespace Guias.Servicio
{

    public interface IServicioGuias
    {

        //public string Create(Guia guia); (Es el alta)
        public Guia Get(string id);
        public bool AddSitioInteres(string id, string urlSitio);
            public bool RemoveSitioInteres(string id, string urlSitio);

        public Guia AltaGuia(string correo, string nombre);
        public bool BajaGuia(string id);
        public List<Guia> GetGuiasSitio(string sitio);
    }


    public class ServicioGuias : IServicioGuias
    {

        private readonly RepositorioGuias repositorio;

        public ServicioGuias(RepositorioGuias repositorio)
        {

            this.repositorio = repositorio;
        }

        public Guia AltaGuia(string correo, string nombre)
        {
            Guia guia = repositorio.GetByEmail(correo);
            if (guia != null) return guia;

            guia = new Guia{Correo= correo, Nombre =nombre};
            repositorio.Add(guia);
            return guia;
        }

        public Guia Get(string id)
        {
            return repositorio.GetById(id);
        }

        public bool BajaGuia(string id)
        {
            Guia guia = repositorio.GetById(id);
            if (guia != null){ repositorio.Delete(guia);
            return true;}
            return false;
        }

        public List<Guia> GetGuiasSitio(string sitio)
        {
            List<Guia> listaGuias = new List<Guia>();
            foreach (Guia guia in repositorio.GetAll())
            {
                if (guia.SitiosInteres.Contains(sitio)) listaGuias.Add(guia);
            }
            return listaGuias;
        }
 

    public bool AddSitioInteres(string id, string urlSitio)
    {
        Guia guia = repositorio.GetById(id);
        if(guia==null) return false;
        if (guia.SitiosInteres.Contains(urlSitio)) return true;
        else
        {
            guia.SitiosInteres.Add(urlSitio);
            repositorio.Update(guia);
            return true;
        }
    }

    public bool RemoveSitioInteres(string id, string urlSitio)
    {
        Guia guia = repositorio.GetById(id);
        if (guia==null) return false;
        if (guia.SitiosInteres.Contains(urlSitio)) 
        {guia.SitiosInteres.Remove(urlSitio);
        repositorio.Update(guia);
        }
        return true;
    }

   }
}