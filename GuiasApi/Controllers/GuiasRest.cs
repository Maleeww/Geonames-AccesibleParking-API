
using Guias.Modelo;
using Guias.Servicio;
using Microsoft.AspNetCore.Mvc;

namespace GuiasApi.Controllers {

    [Route("api/guias")]
    [ApiController]
    public class GuiasController : ControllerBase
    {

        private readonly IServicioGuias _servicio;

        public GuiasController(IServicioGuias servicio) {

            this._servicio = servicio;
        }

        [HttpPost]
        public ActionResult<Guia> AltaGuia([FromForm] string email, [FromForm] string nombre){
            var guia = _servicio.Alta(email, nombre);
            Console.WriteLine("Alta guia"+ email);
            return CreatedAtRoute("GetGuia", new { id = guia.Id }, guia);

        }


        [HttpGet("{id}", Name = "GetGuia")]
        public ActionResult<Guia> Get(string id)
        {
            var entidad = _servicio.Get(id);

            if (entidad == null)
            {
                return NotFound();
            }

            return entidad;
        }

        [HttpDelete("{id}")]
        public IActionResult BajaGuia(string id)
        {
            var entidad = _servicio.Get(id);

            if (_servicio.Get(id)!= null)
            {          
                _servicio.BajaGuia(id);
                return NoContent();
            }
                return NotFound();

        }

        [HttpGet("{url}")]
       // [HttpGet] // [FromQuery]
        public ActionResult<Guia> GetBySitio(string url)
        {
            Lista<Guia> lista = _servicio.GetGuiasSitio(url);

            if (!lista.Any())
            {
                return NotFound();
            }

            return lista;
        }

        [HttpPost("{id}/sitio")]
        public ActionResult<Guia> AddSitioInteres( string id, [FromForm] string urlSitio){

            bool add = _servicio.AddSitioInteres(id, urlSitio);
            if(add) return NoContent();
            NotFound();


    }

            [HttpDelete("{id}/sitio/{url}")] // no es post, tenemos que meterla en url
        public ActionResult<Guia> RemoveSitioInteres( string id, string urlSitio){

            bool remove = _servicio.RemoveSitioInteres(id, urlSitio);
            if(remove) return NoContent();
            NotFound();


    }

}
}