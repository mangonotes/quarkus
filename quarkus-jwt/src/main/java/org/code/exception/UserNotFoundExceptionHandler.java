package org.code.exception;

import org.code.model.BaseResponseBuilder;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserNotFoundExceptionHandler implements ExceptionMapper<UserNotFoundException> {
    @Override
    public Response toResponse(UserNotFoundException e) {
        return  Response.status (Response.Status.FORBIDDEN).entity(new
                BaseResponseBuilder<String>().ofError("User not found").createBaseResponse()).build();
    }
}
