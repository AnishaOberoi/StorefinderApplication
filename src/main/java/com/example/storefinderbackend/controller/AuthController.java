package com.example.storefinderbackend.controller;

import com.example.storefinderbackend.config.JwtProvider;
import com.example.storefinderbackend.entity.User;
import com.example.storefinderbackend.exception.UserException;
import com.example.storefinderbackend.repository.UserRepository;
import com.example.storefinderbackend.request.LoginRequest;
import com.example.storefinderbackend.response.AuthResponse;
import org.springframework.security.authentication.AuthenticationManager;
import com.example.storefinderbackend.service.CustomeUserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;

    private JwtProvider jwtProvider;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private CustomeUserServiceImplementation customUserService;
    @Autowired
    private UserDetailsService userDetailsService;


    public AuthController(UserRepository userRepository, CustomeUserServiceImplementation customUserService, PasswordEncoder passwordEncoder,JwtProvider jwtProvider){
        this.userRepository=userRepository;
        this.customUserService=customUserService;
        this.passwordEncoder=passwordEncoder;
        this.jwtProvider=jwtProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String username = user.getUsername();
        String role = user.getRole();

        // Check if the email already exists
        User isEmailExist = userRepository.findByEmail(email);
        if (isEmailExist != null) {
            throw new UserException("Email is already in use");
        }

        // Create a new user
        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setUsername(username);
        createdUser.setRole(role);

        // Save the user to the repository
        User savedUser = userRepository.save(createdUser);

        // Load full user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());

        // Authenticate the newly created user with full user details
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String token = jwtProvider.generateToken(authentication);

        // Create AuthResponse with all necessary information directly from savedUser
        AuthResponse authResponse = new AuthResponse(token, "Signup Success", savedUser.getRole(), savedUser.getEmail(), savedUser.getUsername());

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
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

            // Load full user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Retrieve user from the repository to get role and username
            User user = userRepository.findByEmail(username);

            // Respond with successful authentication
            AuthResponse authResponse = new AuthResponse(token, "Signin Success", user.getRole(), user.getEmail(), user.getUsername());
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            // Respond with unauthorized status for failed authentication
            AuthResponse authResponse = new AuthResponse(null, "Invalid email or password.",null,null,null);
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
