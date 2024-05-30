package com.example.storefinderbackend.controller;

import com.example.storefinderbackend.config.JwtProvider;
import com.example.storefinderbackend.entity.User;
import com.example.storefinderbackend.exception.UserException;
import com.example.storefinderbackend.repository.UserRepository;
import com.example.storefinderbackend.request.LoginRequest;
import com.example.storefinderbackend.response.AuthResponse;
import com.example.storefinderbackend.service.CustomeUserServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private JwtProvider jwtProvider;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private CustomeUserServiceImplementation customUserService;


    public AuthController(UserRepository userRepository, CustomeUserServiceImplementation customUserService, PasswordEncoder passwordEncoder,JwtProvider jwtProvider){
        this.userRepository=userRepository;
        this.customUserService=customUserService;
        this.passwordEncoder=passwordEncoder;
        this.jwtProvider=jwtProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>createUserHandler(@RequestBody User user) throws UserException{

        String email=user.getEmail();
        String password= user.getPassword();
        String username= user.getUsername();
        String role=user.getRole();

        User isEmailExist=userRepository.findByEmail(email);

        if(isEmailExist!=null){
            throw new UserException("Email is already in use");
        }

        User createdUser=new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setUsername(username);
        createdUser.setRole(role);

        User savedUser=userRepository.save(createdUser);

        Authentication authentication= new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token=jwtProvider.generateToken(authentication);

        AuthResponse authResponse= new AuthResponse(null,null);
        authResponse.setJwt(token);
        authResponse.setMessage("Signup Success");

        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) {
        try {
            String username = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            // Authenticate user
            Authentication authentication = authenticate(username, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            String token = jwtProvider.generateToken(authentication);

            // Respond with successful authentication
            AuthResponse authResponse = new AuthResponse(token, "Signin Success");
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            // Respond with unauthorized status for failed authentication
            AuthResponse authResponse = new AuthResponse(null, "Invalid email or password.");
            return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
        }
    }


    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customUserService.loadUserByUsername(username);

        if(userDetails==null){
            throw new BadCredentialsException("Invalid Username");
        }

        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
