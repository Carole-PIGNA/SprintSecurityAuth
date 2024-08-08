package com.example.training.controller;

import com.example.training.authRequestResponse.AuthenticationRequest;
import com.example.training.authRequestResponse.AuthenticationResponse;
import com.example.training.authRequestResponse.RegisterRequest;
import com.example.training.dto.StudentDTO;
import com.example.training.dto.StudentDTOMapper;
import com.example.training.model.Fourniture;
import com.example.training.model.Student;
import com.example.training.repository.StudentRepository;
import com.example.training.service.AuthenticationService;
import com.example.training.service.JwtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {
    @Autowired
    Environment environment;
    @Mock
    private StudentRepository studentRepository;
    boolean containsTest;
    private AuthenticationService authenticationService;
    private JwtService jwtServiceMock;
    StudentDTO studentDTO;
    StudentDTOMapper studentDTOMapper;
    Student student;
    List<Fourniture> fournitureList = new ArrayList<>();
    Fourniture fourniture;
    AuthenticationController authenticationController;
    @BeforeEach
    void setUp() {
        studentDTOMapper = new StudentDTOMapper();
        containsTest = Arrays.asList(environment.getActiveProfiles()).contains("test");
        fourniture = new  Fourniture(1L,"",5,25,student);
        fournitureList.add(fourniture);
        student  = new Student(1, "firstname",
                "lastNameD","email","123456", LocalDate.now(),fournitureList);


        studentDTO = studentDTOMapper.apply(student);
        authenticationService = new AuthenticationService(studentRepository,
                null,
                jwtServiceMock,
                null);
    }

    @AfterEach
    void tearDown() {
        authenticationService = null;
        fournitureList = null;
        fourniture = null;
        authenticationController = null;
        jwtServiceMock = null;
    }


    @Test
    void register() {
        if (containsTest) {
            authenticationService = Mockito.mock(AuthenticationService.class);
            authenticationController = new AuthenticationController(authenticationService);
            RegisterRequest request = new RegisterRequest("toto",
                    "tata",
                    "tata@gmail.com",
                    "kkdms1165956q**/",
                    LocalDate.of(1981, Month.NOVEMBER, 24),
                    null);

            AuthenticationResponse expectedResponse = new AuthenticationResponse(
                    "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0YXRhQGdtYWlsLmNvbSIsImlhdCI6MTcxMDYzOTI2OSwiZXhwIjoxNzEwNzI1NjY5fQ.YDhx-MSlUFj5lUICJ7zq4NaItNoM0FgDCDI2nemUS8s");
            Mockito.when(authenticationService.register(request,false)).thenReturn(expectedResponse);


            // Appeler la méthode du contrôleur à tester
            ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.register(request);

            // Vérifier que la méthode register() a été appelée sur le mock du service avec les bons paramètres
            Mockito.verify(authenticationService, Mockito.times(1)).register(request, false);

            // Vérifier que la réponse de la méthode du contrôleur est la même que celle retournée par le service
            Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            Assertions.assertEquals(expectedResponse, responseEntity.getBody());
        }
    }

    @Test
    void authenticate() {
        if (containsTest) {
            authenticationService = Mockito.mock(AuthenticationService.class);
            authenticationController = new AuthenticationController(authenticationService);
            RegisterRequest request = new RegisterRequest("toto",
                    "tata",
                    "tata@gmail.com",
                    "kkdms1165956q**/",
                    LocalDate.of(1981, Month.NOVEMBER, 24),
                    null);
            AuthenticationRequest authenticationRequest = new AuthenticationRequest(request.getEmail(), request.getPassword());

             // Appeler la méthode du contrôleur à tester
            ResponseEntity<AuthenticationResponse> responseEntity = authenticationController.authenticate(authenticationRequest);

            // Vérifier que la méthode authenticate() a été appelée sur le mock du service avec les bons paramètres
            Mockito.verify(authenticationService, Mockito.times(1)).authenticate(authenticationRequest);

            // Vérifier que la réponse de la méthode du contrôleur est la même que celle retournée par le service
            Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }
    }
}