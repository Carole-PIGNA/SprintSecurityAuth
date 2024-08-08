package com.example.training.service;

import com.example.training.authRequestResponse.AuthenticationRequest;
import com.example.training.authRequestResponse.AuthenticationResponse;
import com.example.training.authRequestResponse.RegisterRequest;
import com.example.training.model.Fourniture;
import com.example.training.repository.FournitureRepository;
import com.example.training.role.Role;
import com.example.training.model.Student;
import com.example.training.repository.StudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final StudentRepository studentRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	Student student = new Student();
	public AuthenticationResponse register(RegisterRequest request, boolean isTesting) {
		if(!isTesting) {
			student = Student.builder()
					.firstname(request.getFirstname())
					.lastname(request.getLastname())
					.email(request.getEmail())
					.password(passwordEncoder.encode(request.getPassword()))
					.dob(request.getDob())
					.fournitureList((request.getFournitureList()))
					.role(Role.TEST)
					.build();
		}
		else {
			student = Student.builder()
					.firstname(request.getFirstname())
					.lastname(request.getLastname())
					.email(request.getEmail())
					.password(passwordEncoder.encode(request.getPassword()))
					.dob(request.getDob())
					.role(Role.TEST)
					.build();
		}
		studentRepository.save(student);
		var jwtToken = jwtService.generateToken(new HashMap<>(),student);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
				)
		);
		var student = studentRepository.findByEmail(request.getEmail())
				.orElseThrow(
						() -> new IllegalStateException(
								"The student %s does not exist"
										.formatted(request.getEmail()))
				);
		var jwtToken = jwtService.generateToken(new HashMap<>(),student);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}
}
