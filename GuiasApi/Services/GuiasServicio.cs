
using Guias.Modelo;
using Guias.Repositorio;

namespace Guias.Servicio
{

    public interface IServicioGuias
    {

        public string Create(Guia guia);
        public Guia Get(string id);
        public string AltaGuia(string email, string nombre);
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

        public string AltaGuia(string email, string nombre)
        {
            Guia guia = repositorio.GetByEmail(email);
            if (guia != null) return guia;

            guia = new Guia(email, nombre);
            return repositorio.Add(guia);
        }

        public Guia Get(string id)
        {
            return repositorio.GetById(id);
        }

        public bool BajaGuia(string id)
        {
            Guia guia = repositorio.GetById(id);
            if (guia != null) return repositorio.Delete(guia);
            return false;
        }

        public List<Guia> GetGuiasSitio(string sitio)
        {
            List<Guia> listaGuias = new List<Guia>();
            foreach (Guia guia in repositorio.getAll())
            {
                if (guia.SitiosInteres.Contains(sitio)) listaGuias.Add(guia);
            }
            return listaGuias;
        }
    }

    public bool AddSitioInteres(string id, string urlSitio)
    {
        Guia guia = repositorio.GetById(id);
        if(guia==null) return false;
        if (guia.SitiosInteres.Contains(urlSitio)) return true;
        else
        {
            guia.SitiosInteres.add(urlSitio);
            repositorio.Update(guia);
            return true;
        }
    }

    public bool RemoveSitioInteres(string id, string urlSitio)
    {
        Guia guia = repositorio.GetById(id);
        if (guia==null) return false;
        if (guia.SitiosInteres.Contains(urlSitio)) 
        {guia.SitiosInteres.Delete(urlSitio);
        Repositorio.Update(guia);
        }
        return true;
    }


}