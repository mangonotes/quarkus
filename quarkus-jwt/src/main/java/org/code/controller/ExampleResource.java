package org.code.controller;

import org.code.model.User;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

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
@SecurityScheme(securitySchemeName = "jwt", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "jwt")
public class ExampleResource {
    @GET
    @Path("/testing")
    @Tags(value = @Tag( name="Token", description = "Its token testing path"))
    @SecurityRequirement(name="jwt", scopes = {})
    @Operation(summary = "this just testing url with token")
    @APIResponses(value = {@APIResponse(responseCode = "403", description = "Token not valid"),
    @APIResponse(responseCode = "200", description = "return data", content = @Content(schema = @Schema(implementation = Map.class)))})
    @Produces(MediaType.APPLICATION_JSON)
    public Response test(@Context SecurityContext context){
        Map<String, String> test= new HashMap<>();
        test.put("testing", "hello");
        test.put("user", ((User) context.getUserPrincipal()).getName() );
       return  Response.ok().entity(test).build();
    }

}