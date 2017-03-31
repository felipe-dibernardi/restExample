/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fdbst.restexample.ws.test;

import br.com.fdbst.restexample.entity.Usuario;
import br.com.fdbst.restexample.service.UsuarioService;
import br.com.fdbst.restexample.ws.UsuarioWS;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author felipe
 */
@RunWith(Arquillian.class)
public class UsuarioWSTest {
    
    private static Integer usuarioId;
    
    @Deployment(testable = false)
    public static WebArchive create() {
        return ShrinkWrap
                .create(WebArchive.class)
                .addPackage(Usuario.class.getPackage())
                .addPackage(UsuarioService.class.getPackage())
                .addPackage(UsuarioWS.class.getPackage())
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }
    
    @Test
    @RunAsClient
    @InSequence(1)
    public void shouldfindUser(@ArquillianResteasyResource("webresources") final WebTarget webTarget) {
        final Usuario usuario = webTarget.path("/usuario/1").request(MediaType.APPLICATION_JSON).get(Usuario.class);
        
        assertNotNull(usuario);
    } 
    
    @Test
    @RunAsClient
    @InSequence(2)
    public void shouldlistAllUsers(@ArquillianResteasyResource("webresources") final WebTarget webTarget) {
        final Response response = webTarget.path("/usuario/").request(MediaType.APPLICATION_JSON).get();
        List<Usuario> usuarios = response.readEntity(new GenericType<List<Usuario>>(){});
        
        assertEquals(200, response.getStatus());
        assertNotNull(usuarios);
        
    }
    
    @Test
    @RunAsClient
    @InSequence(3)
    public void shouldInsertUser(@ArquillianResteasyResource("webresources") final WebTarget webTarget) {
        final Response response = webTarget.path("/usuario/").request(MediaType.APPLICATION_JSON).post(
                Entity.json(new Usuario("Teste", 30, "MASCULINO")));
        
        Usuario usuario = response.readEntity(Usuario.class);
        
        usuarioId = usuario.getId();
        
        final Usuario usuarioBD = webTarget.path("/usuario/" + usuarioId).request(MediaType.APPLICATION_JSON)
                .get(Usuario.class);
        
        assertEquals(usuarioBD.getId(), usuario.getId());
    }
    
    @Test
    @RunAsClient
    @InSequence(4)
    public void shouldUpdateUser(@ArquillianResteasyResource("webresources") final WebTarget webTarget) {
        Response response = webTarget.path("/usuario/" + usuarioId).request(MediaType.APPLICATION_JSON).get();
        
        Usuario usuario = response.readEntity(Usuario.class);
        
        usuario.setIdade(40);
        
        response = webTarget.path("/usuario/").request(MediaType.APPLICATION_JSON)
                .put(Entity.json(usuario));
        
        Usuario usuarioAtualizado = response.readEntity(Usuario.class);
        
        usuario = webTarget.path("/usuario/" + usuarioId).request(MediaType.APPLICATION_JSON)
                .get(Usuario.class);
        
        assertEquals(usuario, usuarioAtualizado);
    }
    
    @Test
    @RunAsClient
    @InSequence(5)
    public void shouldRemoveUser(@ArquillianResteasyResource("webresources") final WebTarget webTarget) {
        Response response = webTarget.path("/usuario/" + usuarioId).request(MediaType.APPLICATION_JSON).delete();
        
        response.readEntity(Usuario.class);
        
        Usuario usuario = webTarget.path("/usuario/" + usuarioId).request(MediaType.APPLICATION_JSON)
                .get(Usuario.class);
        
        assertEquals(response.getStatus(), 200);
        assertNull(usuario);
    }
    
    @Test
    @RunAsClient
    @InSequence(6)
    public void listExistingUsers(@ArquillianResteasyResource("webresources") final WebTarget webTarget) {
        final Response response = webTarget.path("/usuario/filtro").queryParam("nome", "Felipe")
                .request(MediaType.APPLICATION_JSON).get();
        List<Usuario> usuarios = response.readEntity(new GenericType<List<Usuario>>(){});
        assertEquals(200, response.getStatus());
        assertNotNull(usuarios);
    }
    
}
