package org.code.service.user;

import org.code.exception.UserNotFoundException;
import org.code.model.User;

import java.util.Optional;

public interface IUserService {
    public User findUser(String id, String password) throws UserNotFoundException;
    public void registerUser(User user);
}
