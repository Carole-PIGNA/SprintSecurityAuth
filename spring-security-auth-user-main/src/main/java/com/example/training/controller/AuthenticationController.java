package com.example.training.controller;

import com.example.training.authRequestResponse.AuthenticationRequest;
import com.example.training.authRequestResponse.AuthenticationResponse;
import com.example.training.authRequestResponse.RegisterRequest;
import com.example.training.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/training/student")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService service;
	boolean isTesting = false;
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(
			@RequestBody RegisterRequest request
	){
		return ResponseEntity.ok(service.register(request, isTesting));
	}
	@PostMapping(path = "/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(
			@RequestBody AuthenticationRequest request
	){
		return ResponseEntity.ok(service.authenticate(request));
	}
}
