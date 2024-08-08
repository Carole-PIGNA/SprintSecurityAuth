package com.example.training.dto;

import com.example.training.model.Fourniture;

import java.util.List;

public record StudentDTO(int id,
						 String firstName,
						 String lastName,
						 Integer age,
						 String email,
						 List<Fourniture> fournitureList) { }