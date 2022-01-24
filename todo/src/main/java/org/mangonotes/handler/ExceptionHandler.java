package org.mangonotes.handler;

import org.mangonotes.exception.NotFoundException;
import org.mangonotes.exception.TodoException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(),ErrorType.ITEM_NOT_FOUND);
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}
