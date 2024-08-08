package com.example.training.service;

import com.example.training.authRequestResponse.AuthenticationRequest;
import com.example.training.authRequestResponse.AuthenticationResponse;
import com.example.training.authRequestResponse.RegisterRequest;
import com.example.training.model.Fourniture;
import com.example.training.model.Student;
import com.example.training.repository.StudentRepository;
import com.example.training.role.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ContextConfiguration
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    @Autowired
    Environment environment;
    @Mock
    private StudentRepository studentRepository;
    RegisterRequest request = new RegisterRequest();
    AuthenticationRequest authRequest = new AuthenticationRequest();

    private Student student;
    @Autowired
    AuthenticationService authService;
     PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    Fourniture fourniture1;
    List<Fourniture> fournitureList = new ArrayList<>();
    boolean containsTest;

    @BeforeEach
    void setUp() {
        containsTest = Arrays.asList(environment.getActiveProfiles()).contains("test");

        fourniture1 = new  Fourniture(1L,"",5,25, student);
        fournitureList.add(fourniture1);
        // Préparez les données de test
        request.setFirstname("john_doe");
        request.setLastname("john_doe");
        request.setPassword("password");
        request.setEmail("email");
        request.setFournitureList(fournitureList);
        request.setDob(LocalDate.of(1981, Month.NOVEMBER, 24));
        student = Student.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .dob(request.getDob())
                .fournitureList((request.getFournitureList()))
                .role(Role.TEST)
                .role(Role.TEST)
                .build();
        authRequest.setPassword(request.getPassword());
        authRequest.setEmail(request.getEmail());
    }

    @AfterEach
    void tearDown() {
        request = null;
        authRequest=null;
        student=null;
        authService=null;
        passwordEncoder=null;
        fourniture1=null;
        fournitureList=null;
        studentRepository.deleteAll();
    }

    @Test
    @Transactional
    void register() {
        if(containsTest) {
            AuthenticationResponse response = authService.register(request, true);
            assertNotNull(response);
        }
    }

    @Test
    void authenticate() {
        if(containsTest) {
            AuthenticationResponse response = authService.register(request, true);
            AuthenticationResponse req = authService.authenticate(authRequest);
            assertNotNull(req);
        }
    }
}