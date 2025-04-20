package com.mirza.simplysocial;

import com.mirza.simplysocial.model.*;
import com.mirza.simplysocial.repository.PostRepository;
import com.mirza.simplysocial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ApiResponse> loginUser(@RequestBody LoginRequest loginRequest){
        Optional<User> userOptional=userRepository.findById(loginRequest.getUsername());

        if(userOptional.isPresent()){
            User user=userOptional.get();
            if(user.getPassword().equals(loginRequest.getPassword())){
                return new ResponseEntity<>(new ApiResponse(true, "Login successful!"), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ApiResponse(false, "Invalid password!"), HttpStatus.UNAUTHORIZED);
            }
        }else {
            return new ResponseEntity<>(new ApiResponse(false, "User not found!"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/posts/create")
    public ResponseEntity<ApiResponse> createPost(@RequestBody PostRequest postRequest){
        Optional<User> userOptional=userRepository.findById(postRequest.getUsername());
        if(!userOptional.isPresent()){
            return new ResponseEntity<>(new ApiResponse(false, "User not found!"), HttpStatus.NOT_FOUND);
        }
        User poster=userOptional.get();
        Post newPost=new Post(postRequest.getMediaFile(),poster);
        postRepository.save(newPost);
        return new ResponseEntity<>(new ApiResponse(true, "Post created successfully!"), HttpStatus.CREATED);
    }
}