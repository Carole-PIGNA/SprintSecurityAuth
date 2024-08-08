package com.example.training.model;

import jakarta.persistence.*;
import lombok.*;

@Generated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "fourniture")
@Table
public class Fourniture {

	@Id
	@SequenceGenerator(
			name = "fourniture_sequence",
			sequenceName = "fourniture_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			generator = "fourniture_sequence",
			strategy = GenerationType.SEQUENCE
	)
	@Column(updatable = false)
	private Long fId;
	@Column(columnDefinition = "TEXT")
	@NonNull
	private String name;
	@Column(columnDefinition = "bigint")
	@NonNull
	private Integer quantity;
	@Column(columnDefinition = "bigint")
	@NonNull
	private Integer price;

	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;

}
