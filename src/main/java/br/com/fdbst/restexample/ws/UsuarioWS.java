package br.com.fdbst.restexample.ws;

import br.com.fdbst.restexample.entity.Usuario;
import br.com.fdbst.restexample.service.UsuarioService;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * Classe que implementa as funcionalidades de WebService para a Entidade Usuario.
 * @author Felipe Di Bernardi S Thiago
 */
@Stateless
@Path("/usuario")
public class UsuarioWS {
    
    @EJB
    private UsuarioService usuarioService;
    
    @GET
    @Produces("application/json")
    public List<Usuario> listAll() {
        return usuarioService.listAll();
    }
    
    @Path("{id}")
    @GET
    @Produces("application/json")
    public Usuario find(@PathParam("id") Integer id) {
        return usuarioService.find(id);
    }
    
    @GET
    @Path("/filtro")
    @Produces("application/json")
    public List<Usuario> listByParams(@QueryParam("nome") String nome) {
        return usuarioService.listByParams(nome);
    }
    
    @POST
    @Consumes("application/json")
    public Usuario insert(Usuario usuario) {
        return usuarioService.insert(usuario);
    }
    
    @PUT
    @Consumes("application/json")
    public Usuario update(Usuario usuario) {
        return usuarioService.update(usuario);
    }
    
    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public Usuario remove(@PathParam("id") Integer id) {
        return usuarioService.remove(id);
    }
    
}
