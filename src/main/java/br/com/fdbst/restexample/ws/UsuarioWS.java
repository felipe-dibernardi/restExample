package br.com.fdbst.restexample.ws;

import br.com.fdbst.restexample.entity.Usuario;
import br.com.fdbst.restexample.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@SuppressWarnings("checkstyle:designforextension")
@Api
@Stateless
@Path("/usuario")
public class UsuarioWS {
    
    @EJB
    private UsuarioService usuarioService;
    
    /**
     * Lista todos os Usuarios do sistema.
     * @return Lista de Usuarios.
     */
    @ApiResponses(
        @ApiResponse(
            code = 200,
            message = "Usuários listados com sucesso."))
    @ApiOperation(value = "Lista todos os Usuários do sistema", httpMethod = "GET")
    @GET
    @Produces("application/json")
    public List<Usuario> listAll() {
        return usuarioService.listAll();
    }
    
    /**
     * Recupera um Usuario do sistema através do Id.
     * @param id Id do Usuario selecionado.
     * @return Usuario selecionado.
     */
    @ApiResponses(
        @ApiResponse(
            code = 200,
            message = "Usuário buscado com sucesso.",
            response = Usuario.class))
    @ApiOperation(value = "Busca um Usuário específico no sistema através de seu id", httpMethod = "GET", 
            response = Usuario.class)
    @Path("{id}")
    @GET
    @Produces("application/json")
    public Usuario find(@ApiParam(value = "Id do Usuário", required = true)@PathParam("id") final Integer id) {
        return usuarioService.find(id);
    }
    
    /**
     * Lista os Usuarios do sistema atraves de parametros.
     * @param nome Nome do Usuario.
     * @return Lista de Usuarios.
     */
    @ApiResponses(
        @ApiResponse(
            code = 200,
            message = "Usuários listados com sucesso."))
    @ApiOperation(value = "Lista Usuários do sistema através de parâmetros", httpMethod = "GET")
    @GET
    @Path("/filtro")
    @Produces("application/json")
    public List<Usuario> listByParams(
            @ApiParam(value = "Nome dos Usuários", required = true) @QueryParam("nome") final String nome) {
        return usuarioService.listByParams(nome);
    }
    
    /**
     * Insere um Usuario o sistema.
     * @param usuario Usuario a ser inserido.
     * @return Usuario inserido.
     */
    @ApiResponses(
        @ApiResponse(
            code = 200,
            message = "Usuário inserido com sucesso.",
            response = Usuario.class))
    @ApiOperation(value = "Insere um Usuário no sistema", httpMethod = "POST", response = Usuario.class)
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Usuario insert(final Usuario usuario) {
        return usuarioService.insert(usuario);
    }
    
    /**
     * Atualiza um Usuario do sistema.
     * @param usuario Usuario a ser atualizado.
     * @return Usuario atualizado.
     */
    @ApiResponses(
        @ApiResponse(
            code = 200,
            message = "Usuário atualizado com sucesso",
            response = Usuario.class))
    @ApiOperation(value = "Atualiza um Usuário no sistema", httpMethod = "PUT", response = Usuario.class)
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Usuario update(final Usuario usuario) {
        return usuarioService.update(usuario);
    }
    
    /**
     * Remove um Usuario do sistema através do Id.
     * @param id Id do Usuario a ser removido.
     * @return Usuario removido.
     */
    @ApiResponses(
        @ApiResponse(
            code = 200,
            message = "Usuario removido com sucesso.",
            response = Usuario.class))
    @ApiOperation(value = "Remove um Usuário do sistema atraves do Id", httpMethod = "DELETE", response = Usuario.class)
    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public Usuario remove(
            @ApiParam(value = "Id do Usuário no sistema", required = true) @PathParam("id") final Integer id) {
        return usuarioService.remove(id);
    }
    
}
