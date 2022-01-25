package org.mangonotes.handler;

import org.mangonotes.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RunTimeExceptionHandler implements ExceptionMapper<RuntimeException> {
    private Logger logger = LoggerFactory.getLogger(RunTimeExceptionHandler.class);
    @Override
    public Response toResponse(RuntimeException e) {
        logger.error("", e);
        ErrorMessage errorMessage = new ErrorMessage(e.getMessage(),ErrorType.SERVER_ERROR);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
    }
}
