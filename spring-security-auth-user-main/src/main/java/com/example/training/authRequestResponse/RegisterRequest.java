package com.example.training.authRequestResponse;

import com.example.training.model.Fourniture;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Generated
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class RegisterRequest {
	private String firstname;
	private String lastname;
	private String email;
	private String password;
	private LocalDate dob;
	private List<Fourniture> fournitureList;
}
