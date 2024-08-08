package com.example.training.service;

import com.example.training.authRequestResponse.AuthenticationResponse;
import com.example.training.util.StudentUtil;
import com.example.training.dto.StudentDTO;
import com.example.training.dto.StudentDTOMapper;
import com.example.training.model.Student;
import com.example.training.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StudentService {
	private  final StudentRepository studentRepository;
	private  final StudentUtil studentUtil;
	private  final StudentDTOMapper studentDTOMapper;
	JwtService jwtService;
	private final PasswordEncoder passwordEncoder;


	public List<StudentDTO> getAllStudentsDTO() {
		return studentRepository.findAll().stream().map(studentDTOMapper).collect(Collectors.toList());
	}

	public StudentDTO getStudentByIdDTO(int studentId) {
		return studentRepository.findById(studentId).map(studentDTOMapper)
				.orElseThrow(() -> new IllegalStateException("Student with id %s doesn't exist".formatted(studentId)));
	}

	public void delAllStudents() {
		studentRepository.deleteAll();
	}

	public void delStudentById(int studentId) {
		 StudentDTO student = getStudentByIdDTO(studentId);
		studentRepository.deleteById(student.id());
	}

	@Transactional
	public void updateStudent(int studentId, String firstname, String lastname,
							  String email, String password, Student student,
							  PasswordEncoder passwordEncoder, JwtService jwtService, boolean isTesting) {
		if(!isTesting){
			student = studentUtil.checkStudentExist(studentId);
		}

		if (firstname != null && firstname.length() > 0 && !Objects.equals(firstname, student.getFirstname())) {
			student.setFirstname(firstname);
		}
		if (lastname != null && lastname.length() > 0 && !Objects.equals(lastname, student.getLastname())) {
			student.setLastname(lastname);
		}
		if (email != null && email.length() > 0 && !Objects.equals(email, student.getEmail())) {
			student.setEmail(email);
		}

		if(!isTesting){
			updatePwd(password, student,this.passwordEncoder, this.jwtService, false);
		}
		if(isTesting){
			updatePwd(password, student,passwordEncoder, jwtService, true);
		}
	}

	private AuthenticationResponse updatePwd(String password, Student student, PasswordEncoder passwordEncoder, JwtService jwtService, boolean isTesting) {
		if ((password != null && password.length() > 0 && !Objects.equals(password, student.getPassword())) && !isTesting) {
			student.setPassword(this.passwordEncoder.encode(password));
		}
		if ((password != null && password.length() > 0 && !Objects.equals(password, student.getPassword())) && isTesting) {
			student.setPassword(passwordEncoder.encode(password));
		}
		var jwtToken = jwtService.generateToken(new HashMap<>(),student);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

}
