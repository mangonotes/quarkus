package org.code.controller;

import org.code.exception.UserNotFoundException;
import org.code.model.*;
import org.code.service.Jwt.IJwtTokenUtil;
import org.code.service.user.IUserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/")
public class LoginController {

    @Inject
    IJwtTokenUtil jwtTokenUtil;
    @Inject
    IUserService userService;
    @GET
    @Path("/token")
    @Produces(MediaType.TEXT_PLAIN)
    public String tokenGenerate(){
        User  user = new UserBuilder().setId("1").setName("test").setRoles("Admin").createUser();
        return  jwtTokenUtil.generateToken(user);

    }
    @GET
    @Path("/verify")
    @Produces(MediaType.APPLICATION_JSON)
    public User verification(@QueryParam("token") String token){
        return jwtTokenUtil.verifyToken(token);
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest login) throws UserNotFoundException {
        final User user = userService.findUser(login.getUserId(), login.getPassword());
        final String token = jwtTokenUtil.generateToken(user);
        BaseResponse<LoginResponse> baseResponse = new BaseResponseBuilder<LoginResponse>().setData(new LoginResponse(token)).setStatus(Status.SUCCESS).createBaseResponse();
        return Response.ok(baseResponse).build();
    }
}
