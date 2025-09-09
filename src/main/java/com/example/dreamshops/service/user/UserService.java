package com.example.dreamshops.service.user;

import com.example.dreamshops.exceptions.AlreadyExistsException;
import com.example.dreamshops.exceptions.ResourceNotFoundException;
import com.example.dreamshops.model.User;
import com.example.dreamshops.repository.user.UserRepository;
import com.example.dreamshops.request.user.CreateUserRequest;
import com.example.dreamshops.request.user.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User with id: " + userId + " not found")
                );
    }

    @Override
    public User createUser(CreateUserRequest request) throws AlreadyExistsException {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail())
                ).map(requestUser -> {
                    User user = new User();
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEmail(request.getEmail());
                    user.setEmail(request.getEmail());
                    return userRepository.save(user);
                }).orElseThrow(() ->
                        new AlreadyExistsException("User with email: " + request.getEmail() + " already exists")
                );
    }

    @Override
    public User updateUser(Long userId, UpdateUserRequest request) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                            existingUser.setFirstName(request.getFirstName());
                            existingUser.setLastName(request.getLastName());
                            return userRepository.save(existingUser);
                        }
                ).orElseThrow(() ->
                        new ResourceNotFoundException("User with id: " + userId + " not found")
                );
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(
                userRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("User with id: " + userId + " not found");
                }
        );

    }
}
