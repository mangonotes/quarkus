package org.mangonotes.handler;

import org.mangonotes.exception.NotFoundException;
import org.mangonotes.exception.ValidationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationHandler implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException e) {
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(),ErrorType.VALIDATION_ERROR);
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}
