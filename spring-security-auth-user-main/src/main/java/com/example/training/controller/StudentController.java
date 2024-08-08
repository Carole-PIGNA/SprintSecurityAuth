package com.example.training.controller;

import com.example.training.dto.StudentDTO;
import com.example.training.model.Student;
import com.example.training.service.JwtService;
import com.example.training.service.StudentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/training/student")
public class StudentController{
	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping(path = "{studentId}")
	public StudentDTO getStudentById(@PathVariable int studentId){
		return studentService.getStudentByIdDTO(studentId);
	}
	@GetMapping
	public List<StudentDTO> getAllStudents(){
		return studentService.getAllStudentsDTO();
	}
	@DeleteMapping
	public void delAllStudents(){
		studentService.delAllStudents();
	}
	@DeleteMapping(path = "{studentId}")
	public void delStudentById(@PathVariable int studentId){

		studentService.delStudentById(studentId);
	}

	@PutMapping(path = "{studentId}")
	public void updateStudent(@PathVariable int studentId,
							  @RequestParam(required = false) String firstname,
							  @RequestParam(required = false) String lastname,
							  @RequestParam(required = false) String email,
							  @RequestParam(required = false) String password,
							  @RequestParam(required = false)Student student,
							  @RequestParam(required = false) PasswordEncoder passwordEncoder,
							  @RequestParam(required = false)JwtService jwtService
	) {
		boolean isTesting = false;
		studentService.updateStudent(studentId,firstname,lastname,email, password, student, passwordEncoder, jwtService, isTesting);

		}
}
