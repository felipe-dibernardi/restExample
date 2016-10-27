/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.fdbst.restexample.service.test;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Classe <b>BaseEJBTester</b>
 * 
 * Essa Classe ...
 * 
 * @author Felipe Di Bernardi S Thiago
 */
public class BaseEJBTester {
    
    private static Context ctx;
    private static EJBContainer ejbContainer;
    
    @BeforeClass
    public static void setUpClass() throws NamingException {
        ejbContainer = EJBContainer.createEJBContainer();
        ctx = ejbContainer.getContext();
    }
    
    @AfterClass
    public static void tearDownClass() {
        ejbContainer.close();
    }
    
    public Object getEJB(String JNDI) throws NamingException {
        return ctx.lookup("java:global/classes/" + JNDI);
    }
    
}
