package com.example.training.repository;

import com.example.training.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
	Optional <Student>findByEmail(String email);
	// For Testing
	@Query(
	"SELECT CASE WHEN COUNT(s) > 0 THEN " +
			"TRUE ELSE FALSE END " +
			"FROM Student s " +
			"WHERE s.email = ?1"
	)
	Boolean selectExistsEmail(String email);
}
