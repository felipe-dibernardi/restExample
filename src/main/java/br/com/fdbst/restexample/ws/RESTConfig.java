/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fdbst.restexample.ws;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Classe que implementa as configurações para utilização de WebService.
 *
 * @author Felipe Di Bernardi S Thiago
 */
@ApplicationPath("webresources")
public class RESTConfig extends Application {

    public RESTConfig() {
        BeanConfig conf = new BeanConfig();
        conf.setTitle("RestExample API");
        conf.setDescription("Exemplo de API RESTful");
        conf.setVersion("1.0.1-SNAPSHOT");
        conf.setHost("localhost:8080");
        conf.setBasePath("/restExample/webresources");
        conf.setSchemes(new String[]{"http"});
        conf.setResourcePackage("br.com.fdbst.restexample");
        conf.setScan(true);

    }

    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(UsuarioWS.class);

        //classes do swagger...
        resources.add(ApiListingResource.class);
        resources.add(SwaggerSerializers.class);
        return resources;
    }

}
