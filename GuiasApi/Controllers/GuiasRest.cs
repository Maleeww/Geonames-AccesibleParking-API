
using Guias.Modelo;
using Guias.Servicio;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;

namespace GuiasApi.Controllers {

    [Route("api/guias")]
    [ApiController]
    public class GuiasController : ControllerBase
    {

        private readonly IServicioGuias _servicio;

        public GuiasController(IServicioGuias servicio) {

            this._servicio = servicio;
        }


        // curl -i -X POST --data "email=david.almagroc@um.es&nombre=david" localhost:8090/guias
        // localhost:5000/api/guias

        [HttpPost]
        public ActionResult<Guia> AltaGuia([FromForm] string email, [FromForm] string nombre){
            var guia = _servicio.AltaGuia(email, nombre);
            Console.WriteLine("Alta guia"+ email);
            return CreatedAtRoute("GetGuia", new { id = guia.Id }, guia);

        }

        // curl -i localhost:5000/api/guias/id/%ID%
        [HttpGet("id/{id}", Name = "GetGuia")]
        public ActionResult<Guia> Get(string id)
        {
            var entidad = _servicio.Get(id);

            if (entidad == null)
            {
                return NotFound();
            }

            return entidad;
        }
        // curl -i -X DELETE localhost:5000/api/guias/%ID%
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
        // curl -i localhost:5000/guias/sitio/Castillo_de_Lorca

        [HttpGet("sitio/{urlSitio}")]
        public ActionResult<Guia> GetBySitio(string urlSitio)
        {
            List<Guia> lista = _servicio.GetGuiasSitio(urlSitio);

            if (lista.Count==0)
            {
                return NotFound();
            }

            return Ok(lista);
        }

        // curl -i -X POST --data "urlSitio=Castillo_de_Lorca" localhost:5000/api/guias/%ID%/sitio

        [HttpPost("{id}/sitio")]
        public ActionResult<Guia> AddSitioInteres( string id, [FromForm] string urlSitio){

            bool add = _servicio.AddSitioInteres(id, urlSitio);
            if(add) return NoContent();
            return NotFound();


    }

        // curl -i -X DELETE localhost:5000/api/guias/%ID%/sitio/Castillo_de_Lorca
            [HttpDelete("{id}/sitio/{urlSitio}")] // no es post, tenemos que meterla en url
        public ActionResult<Guia> RemoveSitioInteres( string id, string urlSitio){

            bool remove = _servicio.RemoveSitioInteres(id, urlSitio);
            if(remove) return NoContent();
            return NotFound();


    }

}
}