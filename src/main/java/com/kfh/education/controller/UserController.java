package com.kfh.education.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kfh.education.config.JwtService;
import com.kfh.education.request.AuthRequestDTO;
import com.kfh.education.request.RegistrationRequest;
import com.kfh.education.response.JwtResponseDTO;
import com.kfh.education.service.UserService;
import com.kfh.education.serviceimpl.UserServiceImpl;

@RestController
@RequestMapping("user")
public class UserController {
	
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserService userService;
	
	public UserController(AuthenticationManager authenticationManager,
			JwtService jwtService,
			UserServiceImpl userService) {

		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.userService = userService;
	}
	
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody RegistrationRequest request) {
    	userService.createUserWithRole(request.getUsername(), request.getPassword(), request.getRole());
        return ResponseEntity.ok("User created successfully");
    }

	@PostMapping("/login")
	public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
	    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
	    if(authentication.isAuthenticated()){
	    	String accessToken=jwtService.GenerateToken(authRequestDTO.getUsername());
	    	JwtResponseDTO jwtResponseDTO = new JwtResponseDTO();
	    	jwtResponseDTO.setAccessToken(accessToken);
	    	return jwtResponseDTO;
	    	
	    } else {
	        throw new UsernameNotFoundException("invalid user request..!!");
	    }
	}

}
