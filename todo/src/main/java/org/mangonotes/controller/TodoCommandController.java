package org.mangonotes.controller;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.mangonotes.model.dto.req.TodoReqDTO;
import org.mangonotes.services.todo.ITodoCommandService;
import org.mangonotes.services.todo.ValidationServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/todos")
public class TodoCommandController {
    Logger logger = LoggerFactory.getLogger(TodoCommandController.class);
    @Inject
    ITodoCommandService todoServices;
    @Inject
    ValidationServices validationServices;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@RequestBody TodoReqDTO todoReqDTO) {
        validationServices.validate(todoReqDTO);
        return Response.status(Response.Status.CREATED).entity(todoServices.create(todoReqDTO)).build();
    }

    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@RequestBody TodoReqDTO todoReqDTO, @PathParam("id") Long id) {
        validationServices.validate(todoReqDTO);
        logger.info("toReq{}", todoReqDTO);
        return Response.status(Response.Status.OK).entity(todoServices.update(todoReqDTO, id)).build();
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        todoServices.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

