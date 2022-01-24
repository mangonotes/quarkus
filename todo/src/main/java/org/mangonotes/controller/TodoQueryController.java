package org.mangonotes.controller;

import org.mangonotes.services.todo.ITodoQueryService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/todos")
public class TodoQueryController {
    @Inject
    ITodoQueryService queryService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(queryService.getAll()).build();
    }
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(queryService.findById(id)).build();
    }
}
