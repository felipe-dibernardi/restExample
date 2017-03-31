/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fdbst.restexample.service.test;

import br.com.fdbst.restexample.entity.Usuario;
import br.com.fdbst.restexample.service.UsuarioService;
import br.com.fdbst.restexample.ws.UsuarioWS;
import java.util.List;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.Archive;
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
public class UsuarioServiceTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "restexample-service-test.war")
                .addPackage(UsuarioService.class.getPackage())
                .addPackage(Usuario.class.getPackage())
                .addPackage(UsuarioWS.class.getPackage())
                .addAsWebInfResource("test-web.xml", "web.xml")
                .addAsWebInfResource("glassfish-resources.xml", "glassfish-resources.xml")
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }
    
    private static Integer userId;
    
    @EJB
    private UsuarioService usuarioService;

    @Test
    @InSequence(1)
    public void shouldInsertUser() {
        Usuario u = new Usuario("Teste", 30, "MASCULINO");
        
        usuarioService.insert(u);
        
        userId = u.getId();
        
        u = usuarioService.find(userId);
        
        assertEquals(u.getNome(), "Teste");
        assertEquals(u.getIdade().intValue(), 30);
        assertEquals(u.getSexo(), "MASCULINO");
        
    }
    
    @Test
    @InSequence(2)
    public void shouldUpdateUser() {
        Usuario u = usuarioService.find(userId);
        u.setIdade(40);
        
        usuarioService.update(u);
        
        u = usuarioService.find(userId);
        
        assertEquals(u.getNome(), "Teste");
        assertEquals(u.getIdade().intValue(), 40);
        assertEquals(u.getSexo(), "MASCULINO");
    }
    
    @Test
    @InSequence(3)
    public void shouldListUsersBySpecificParameters() {
        List<Usuario> usuarios = usuarioService.listByParams("Teste");
        
        assertNotNull(usuarios);
    }
    
    @Test
    @InSequence(4)
    public void shouldListAllUsersWhenParameterIsNull() {
        List<Usuario> usuarios = usuarioService.listByParams(null);
        
        assertNotNull(usuarios);
    }
    
    @Test
    @InSequence(5)
    public void shouldListAllUsersWhenParameterIsEmpty() {
        List<Usuario> usuarios = usuarioService.listByParams("");
        
        assertNotNull(usuarios);
    }
    
    @Test
    @InSequence(6)
    public void shouldRemoveUser() {
        usuarioService.remove(userId);
        
        assertNull(usuarioService.find(userId));
    }
}
