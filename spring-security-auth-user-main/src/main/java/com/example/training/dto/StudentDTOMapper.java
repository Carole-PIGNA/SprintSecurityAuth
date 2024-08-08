package com.example.training.dto;

import com.example.training.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.function.Function;

@Service
public record StudentDTOMapper() implements Function<Student, StudentDTO> {
	@Override
	public StudentDTO apply(Student student) {
		return new StudentDTO(
				student.getId(),
				student.getFirstname(),
				student.getLastname(),
				student.getAge(),
				student.getEmail(),
				new ArrayList<>(student.getFournitureList()));
	}
}
