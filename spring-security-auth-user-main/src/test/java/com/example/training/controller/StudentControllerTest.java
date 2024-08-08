package com.example.training.controller;

import com.example.training.dto.StudentDTO;
import com.example.training.dto.StudentDTOMapper;
import com.example.training.model.Fourniture;
import com.example.training.model.Student;
import com.example.training.repository.StudentRepository;
import com.example.training.service.JwtService;
import com.example.training.service.StudentService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Autowired
    Environment environment;
    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Mock
    private StudentRepository studentRepository;
    boolean containsTest;
    private StudentService studentService;
    private JwtService jwtServiceMock;
    StudentDTO studentDTO;
    StudentDTOMapper studentDTOMapper;
    Student student;
    List<Fourniture> fournitureList = new ArrayList<>();
    Fourniture fourniture;
    StudentController studentController;
    @BeforeEach
    void setUp() {
        studentDTOMapper = new StudentDTOMapper();
        containsTest = Arrays.asList(environment.getActiveProfiles()).contains("test");
        fourniture = new  Fourniture(1L,"",5,25,student);
        fournitureList.add(fourniture);
        student  = new Student(1, "firstname",
                "lastNameD","email","123456", LocalDate.now(),fournitureList);


        studentDTO = studentDTOMapper.apply(student);
        studentService = new StudentService(studentRepository,
                null,
                studentDTOMapper,
                null,passwordEncoder);
        studentController = new StudentController(studentService);


    }

    @AfterEach
    void tearDown() {
        studentService = null;
        fournitureList = null;
        fourniture = null;
        studentController = null;
        jwtServiceMock = null;
    }

    @Test
    void getStudentById() {
        if (containsTest){
            studentService = Mockito.mock(StudentService.class);
            studentController = new StudentController(studentService);
            Mockito.when(studentService.getStudentByIdDTO(1)).thenReturn(studentDTO);
            StudentDTO actualStudentDTO = studentController.getStudentById(1);
            Mockito.verify(studentService, Mockito.times(1)).getStudentByIdDTO(1);
            Assertions.assertEquals(studentDTO, actualStudentDTO);
        }
    }

    @Test
    void getAllStudents() {
        if (containsTest){
            studentService = Mockito.mock(StudentService.class);
            studentController = new StudentController(studentService);
            studentController.getAllStudents();
            Mockito.verify(studentService, Mockito.times(1)).getAllStudentsDTO();
        }
    }

    @Test
    void delAllStudents() {
        if(containsTest){
            studentService = Mockito.mock(StudentService.class);
            studentController = new StudentController(studentService);
            studentController.delAllStudents();
            Mockito.verify(studentService, Mockito.times(1)).delAllStudents();
        }
    }

    @Test
    void delStudentById() {
        if (containsTest){
            studentService = Mockito.mock(StudentService.class);
            studentController = new StudentController(studentService);
            studentController.delStudentById(1);
            Mockito.verify(studentService, Mockito.times(1)).delStudentById(1);
        }
    }

    @Test
    void updateStudent() {
        if (containsTest) {
            studentService = Mockito.mock(StudentService.class);

            passwordEncoder = Mockito.mock(PasswordEncoder.class);
            jwtServiceMock = Mockito.mock(JwtService.class);
            studentController = new StudentController(studentService);
            studentController.updateStudent(1, "John", "Doe", "john.doe@example.com", "password123", null, passwordEncoder, jwtServiceMock);
            Mockito.verify(studentService, Mockito.times(1))
                            .updateStudent(
                                    eq(1),
                                    eq("John"),
                                    eq("Doe"),
                                    eq("john.doe@example.com"),
                                    eq("password123"), any(),
                                    eq(passwordEncoder),
                                    eq(jwtServiceMock),
                                    eq(false));
        }

    }
}