package org.mangonotes.controller;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.mangonotes.model.entity.TodoEntity;
import org.mangonotes.model.dto.req.TodoReqDTO;
import org.mangonotes.services.todo.ITodoCommandService;
import org.mangonotes.services.todo.ValidationServices;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/todos")
public class TodoCommandController {
    @Inject
    ITodoCommandService todoServices;
    @Inject
    ValidationServices validationServices;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@RequestBody TodoReqDTO todoReqDTO)
    {
        validationServices.validate(todoReqDTO);
        return Response.ok(todoServices.create(todoReqDTO)).build();
    }
}
