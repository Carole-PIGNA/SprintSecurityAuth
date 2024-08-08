package com.example.training.repository;

import com.example.training.model.Fourniture;
import com.example.training.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class StudentRepositoryTest {
	@Autowired
	Environment environment;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	 FournitureRepository fournitureRepository;
	Student student;
	Fourniture fourniture1;
	List<Fourniture> fournitureList = new ArrayList<>();
	boolean containsTest;
	@BeforeEach
	void setup(){
		containsTest = Arrays.asList(environment.getActiveProfiles()).contains("test");
		String email = "tatalia@gmail.com";
		//given
		fourniture1 = new  Fourniture(1L,"",5,25, student);
		fournitureList.add(fourniture1);
		student  = new Student(1, "firstname",
				"lastNameD",email,"123456", LocalDate.now(),fournitureList);
		studentRepository.save(student);
	}

	@AfterEach
	void tearDown(){
		student = null;
		fournitureList = null;
		fourniture1 = null;
		fournitureRepository.deleteAll();
		studentRepository.deleteAll();
	}
	@Test
	void selectExistsEmail() {
		if(containsTest) {
			// When
			boolean exists = studentRepository.selectExistsEmail(student.getEmail());
			// Then
			Assertions.assertThat(exists).isTrue();
		}
	}
	@Test
	void findByEmail(){
		if(containsTest) {
			Assertions.assertThat(studentRepository.findByEmail(student.getEmail())).isNotEmpty();
		}
	}
}