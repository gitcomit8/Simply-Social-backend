package com.mirza.simplysocial;

import com.mirza.simplysocial.model.*;
import com.mirza.simplysocial.repository.PostRepository;
import com.mirza.simplysocial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        if(userRepository.existsByUsername(registrationRequest.getUsername())){
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"), HttpStatus.CONFLICT);
        }

        User newUser=new User(
                registrationRequest.getRealName(),
                registrationRequest.getUsername(),
                registrationRequest.getPassword() //TODO: Hash password
        );
        userRepository.save(newUser);
        return new ResponseEntity<>(new ApiResponse(true, "User registered successfully!"), HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findById(loginRequest.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(loginRequest.getPassword())) { // Remember: Hashing needed!
                String sessionToken = java.util.UUID.randomUUID().toString();
                LocalDateTime expiry = LocalDateTime.now().plusMinutes(30);
                user.setSessionToken(sessionToken);
                user.setSessionTokenExpiry(expiry);
                userRepository.save(user);
                return ResponseEntity.ok(new LoginResponse(true, "Login successful", sessionToken));
            } else {
                return new ResponseEntity<>(new ApiResponse(false, "Invalid password"), HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Username not found"), HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/posts/create")
    public ResponseEntity<ApiResponse> createPost(@RequestBody PostRequest postRequest,
                                                  @RequestHeader("Authorization") String sessionToken) {
        if (sessionToken == null || sessionToken.trim().isEmpty()) {
            return new ResponseEntity<>(new ApiResponse(false, "Session token is required"), HttpStatus.UNAUTHORIZED);
        }

        Optional<User> userOptional = userRepository.findBySessionToken(sessionToken);

        if (!userOptional.isPresent() || userOptional.get().getSessionTokenExpiry().isBefore(LocalDateTime.now())) {
            return new ResponseEntity<>(new ApiResponse(false, "Invalid or expired session"), HttpStatus.UNAUTHORIZED);
        }

        User poster = userOptional.get();
        Post newPost = new Post(postRequest.getMediaFile(), poster);
        postRepository.save(newPost);
        return new ResponseEntity<>(new ApiResponse(true, "Post created successfully"), HttpStatus.CREATED);
    }
}