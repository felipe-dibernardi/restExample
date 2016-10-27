/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fdbst.restexample.service.test;

import br.com.fdbst.restexample.entity.Usuario;
import br.com.fdbst.restexample.service.UsuarioService;
import javax.ejb.EJB;
import javax.naming.NamingException;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author felipe
 */
public class UsuarioServiceIT extends BaseEJBTester {
    
    @EJB
    private UsuarioService usuarioService;
    
    public UsuarioServiceIT() throws NamingException {
        usuarioService = (UsuarioService) super.getEJB("UsuarioService");
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void find_first_user() {
    
        Usuario u = usuarioService.find(1);
        assertNotNull(u);
        
    }
}
