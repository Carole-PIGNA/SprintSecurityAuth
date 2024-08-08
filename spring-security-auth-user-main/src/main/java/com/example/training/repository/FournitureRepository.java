package com.example.training.repository;

import com.example.training.model.Fourniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FournitureRepository extends JpaRepository<Fourniture, Long> {
}
