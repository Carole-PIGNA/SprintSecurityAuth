package com.example.training.service;

import com.example.training.dto.*;
import com.example.training.model.Student;
import com.example.training.model.Fourniture;
import com.example.training.repository.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Autowired
    Environment environment;
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService studentService;
    StudentDTO studentDTO;
    StudentDTOMapper studentDTOMapper;
    Student student;
    Fourniture fourniture1;
    List<Fourniture> fournitureList = new ArrayList<>();
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    boolean containsTest;

    @BeforeEach
    void setUp() {
        containsTest = Arrays.asList(environment.getActiveProfiles()).contains("test");

        studentDTOMapper = new StudentDTOMapper();
        fourniture1 = new  Fourniture(1L,"",5,25,student);
        fournitureList.add(fourniture1);
        student  = new Student(1, "firstname",
                "lastNameD","email","123456", LocalDate.now(),fournitureList);
        studentDTO = studentDTOMapper.apply(student);
        studentService = new StudentService(studentRepository,
                null,
                studentDTOMapper,
                null,passwordEncoder);
        studentRepository.save(student);
    }

    @AfterEach
    void tearDown() {
        student = null;
        studentDTO = null;
        studentService = null;
        fournitureList = null;
        fourniture1 = null;
        studentRepository.deleteAll();
    }

    @Test
    void getAllStudentsDTO() {
        if(containsTest) {
            studentService.getAllStudentsDTO();
            Mockito.verify(studentRepository).findAll();
        }
    }

    @Test
    void getStudentByIdDTO() {
        if(containsTest) {
            Mockito.when(studentRepository.findById(1)).thenReturn(Optional.ofNullable(student));
            Assertions.assertThat(studentService.getStudentByIdDTO(student.getId())).isNotNull();
        }
    }

    @Test
    void delAllStudents() {
        if(containsTest) {
            Mockito.doNothing().when(studentRepository).deleteAll();
            studentService.delAllStudents();
            Mockito.verify(studentRepository).deleteAll();
        }
    }

    @Test
    void delStudentById() {
        if(containsTest) {
            Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.ofNullable(student));
            studentService.delStudentById(student.getId());
            Mockito.verify(studentRepository).deleteById(student.getId());
        }
    }

    @Test
    void updateStudent() {
        if(containsTest) {
            studentService.updateStudent(student.getId(), "newName", "fName", "janeS@js.js", "25878",
                    student, passwordEncoder, jwtService, true);
            org.junit.jupiter.api.Assertions.assertEquals("newName", student.getFirstname());
            org.junit.jupiter.api.Assertions.assertEquals("fName", student.getLastname());
            org.junit.jupiter.api.Assertions.assertEquals("janeS@js.js", student.getEmail());
        }
    }
}