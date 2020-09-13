package org.code.controller;

import org.code.model.User;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.HashMap;
import java.util.Map;

@Path("/v1")
public class ExampleResource {
    @GET
    @Path("/testing")
    @Produces(MediaType.APPLICATION_JSON)
    public Response test(@Context SecurityContext context){
        Map<String, String> test= new HashMap<>();
        test.put("testing", "hello");
        test.put("user", ((User) context.getUserPrincipal()).getName() );
       return  Response.ok().entity(test).build();
    }

}