package com.example.dreamshops.controller;

import com.example.dreamshops.exceptions.AlreadyExistsException;
import com.example.dreamshops.exceptions.UserNotFoundException;
import com.example.dreamshops.request.user.CreateUserRequest;
import com.example.dreamshops.request.user.UpdateUserRequest;
import com.example.dreamshops.response.ApiResponse;
import com.example.dreamshops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse("User found",
                            userService.convertToDTO(
                                    userService.getUserById(userId)
                            )
                    )
            );
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), NOT_FOUND));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/createUser")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request) {
        try {
            return ResponseEntity.status(CREATED)
                    .body(new ApiResponse("Success",
                                    userService.convertToDTO(userService.createUser(request))
                            )
                    );
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse(e.getMessage(), CONFLICT));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request) {
        try {
            return ResponseEntity.ok(new ApiResponse("Updated",
                            userService.convertToDTO(userService.updateUser(userId, request)
                            )
                    )
            );
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), NOT_FOUND));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.status(NO_CONTENT)
                    .body(new ApiResponse("Deleted", null));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), NOT_FOUND));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }
}
