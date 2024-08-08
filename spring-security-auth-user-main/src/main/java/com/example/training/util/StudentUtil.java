package com.example.training.util;

import com.example.training.model.Student;
import com.example.training.repository.StudentRepository;
import org.springframework.stereotype.Component;

@Component
public record StudentUtil (StudentRepository studentRepository){

	public Student checkStudentExist(int id){
		return  studentRepository.findById(id).orElseThrow(
				()-> new IllegalStateException("Student with id %s does not exist my bobo".formatted(id)));
	}
}
