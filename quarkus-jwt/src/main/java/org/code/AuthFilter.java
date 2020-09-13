package org.code;

import org.apache.commons.lang3.StringUtils;
import org.code.model.BaseResponseBuilder;
import org.code.model.User;
import org.code.service.Jwt.IJwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Provider
public class AuthFilter implements ContainerRequestFilter {
    @Inject
    IJwtTokenUtil jwtTokenUtil;
    private static Logger LOG = LoggerFactory.getLogger(AuthFilter.class);
    private static String AUTHORIZATION = "Authorization";

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        UriInfo info = containerRequestContext.getUriInfo();
        LOG.info("url path {} ", info.getPath());
        if (info.getPath().startsWith("/v1")) {
            Optional<User> user = getAuthentication(containerRequestContext.getHeaders());
            if (user.isEmpty()) {
                Response response = Response.status(Response.Status.FORBIDDEN).entity(new BaseResponseBuilder().ofError("Token error ").createBaseResponse()).build();
                containerRequestContext.abortWith(response);
                return;
            }
            containerRequestContext.setSecurityContext(new JwtSecurityContext(user.get(), true));

        }
    }

    private Optional<User> getAuthentication(MultivaluedMap<String, String> headers) {

        final List<String> authorization = headers.get(AUTHORIZATION);

        if (authorization == null || authorization.isEmpty()) {
            return Optional.empty();
        }
        final String token = new String(authorization.get(0).replaceFirst("Bearer ", ""));
        try {
            User user = jwtTokenUtil.verifyToken(token);
            if (user == null || StringUtils.isEmpty(user.getId())) {
                return Optional.empty();
            }
            return Optional.of(user);
        } catch (RuntimeException e) {
            LOG.error("error on token validation ", e);
            return Optional.empty();
        }

    }

    public static class JwtSecurityContext implements SecurityContext {
        private final User user;
        private final boolean secured;

        public JwtSecurityContext(User user, boolean secured) {
            this.user = user;
            this.secured = secured;
        }

        @Override
        public Principal getUserPrincipal() {
            return this.user;
        }

        @Override
        public boolean isUserInRole(String s) {
            return Boolean.TRUE; // not checking the roles
        }

        @Override
        public boolean isSecure() {
            return this.secured;
        }

        @Override
        public String getAuthenticationScheme() {
            return SecurityContext.FORM_AUTH;
        }
    }
}
