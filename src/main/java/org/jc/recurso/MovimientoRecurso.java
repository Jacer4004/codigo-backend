/*Jacer */
package org.jc.recurso;

import java.text.ParseException;
import java.util.List;

import org.jc.entidad.CuentaEntidad;
import org.jc.entidad.MovimientoEntidad;
import org.jc.servicio.CuentaServicio;
import org.jc.servicio.MovimientoServicio;

import io.quarkus.logging.Log;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/movimiento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovimientoRecurso {

    @Inject
    MovimientoServicio servicio;

    @Inject
    CuentaServicio servicioC;

        @GET
    public List<MovimientoEntidad> obtenerTodas() {
        return servicio.buscarTodos();
    }
   

    @GET
    @Path("/{id}")
    public MovimientoEntidad obtenerPorId(@PathParam("id") Long id) {
        return servicio.buscarPorId(id);
    }
    
    @GET
    @Path("/reporte/{id}/{desde}/{hasta}")
    public List<MovimientoEntidad> reporte( @PathParam("id") Long id, @PathParam("desde") String desde, @PathParam("hasta") String hasta) throws ParseException {
        CuentaEntidad c = servicioC.buscarPorId(id);
        return servicio.reporte(c, desde, hasta);
    }
    
    @POST
    public MovimientoEntidad crear(MovimientoEntidad entidad) {
        return servicio.guardar(entidad);
    }
     @POST
     @Path("/deposito/{id}/{valor}")
    public Response movimientoD(@PathParam("id") Long id,@PathParam("valor")double valor) {
        String tipo ="CREDITO";
        CuentaEntidad c = servicioC.buscarPorId(id);
        String mensaje = servicio.movimientos(c,valor,tipo);
        if (mensaje!="") {
           return Response.ok(mensaje).build(); 
        }
            return Response.ok("Registrado").build(); 
               
    }

    @POST
     @Path("/retiro/{id}/{valor}")
    public Response movimientoR(@PathParam("id") Long id,@PathParam("valor")double valor) {
        String tipo ="DEBITO";
        CuentaEntidad c = servicioC.buscarPorId(id);
        String mensaje = servicio.movimientos(c,valor,tipo);
        if (mensaje!="") {
           return Response.ok(mensaje).build(); 
        }
            return Response.ok("Registrado").build(); 
               
    }
    
    @DELETE
    @Path("/{id}")
    public void eliminar(@PathParam("id") Long id) {
        servicio.eliminar(id);
    }

    @PUT 
    @Path("/a/{id}")   
    public Response actualizar(@PathParam("id") Long id, MovimientoEntidad entidad) {        
       MovimientoEntidad actualizar= servicio.buscarPorId(id);
        if (actualizar == null) {
            Log.info("La entidad con id " + id + " No actualizado");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        servicio.actualizar(entidad);
        Log.info("La entidad con id " + id + " actualizado");
        return Response.ok("Actualizado").build();
    }

}
