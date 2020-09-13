package org.code.service.Jwt;

import org.code.model.User;

public interface IJwtTokenUtil {
    public String generateToken(User user);
    public User verifyToken(String token);

}
