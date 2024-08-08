package com.example.training.authRequestResponse;

import lombok.*;

@Generated
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class AuthenticationRequest {
	private String email;
	String password;
}
