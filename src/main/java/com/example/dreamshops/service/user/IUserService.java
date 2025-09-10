package com.example.dreamshops.service.user;

import com.example.dreamshops.exceptions.AlreadyExistsException;
import com.example.dreamshops.model.User;
import com.example.dreamshops.request.user.CreateUserRequest;
import com.example.dreamshops.request.user.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);

    User createUser(CreateUserRequest request) throws AlreadyExistsException;

    User updateUser(Long userId, UpdateUserRequest request);

    void deleteUser(Long userId);

}
