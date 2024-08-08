package com.example.training.model;

import com.example.training.role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.List;

@Generated
@Data
@Builder // For building Student inside AuthenticationService
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student", uniqueConstraints = @UniqueConstraint(name = "email_unique", columnNames = "email"))
public class Student implements UserDetails {

	@Id
	@SequenceGenerator(
			name = "student_sequence",
			sequenceName = "student_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			generator = "student_sequence",
			strategy = GenerationType.SEQUENCE
	)
	@Column(updatable = false)
	private int id;
	@Column(columnDefinition = "TEXT")
	@NonNull
	private String firstname;
	@Column(columnDefinition = "TEXT")
	@NonNull
	private String lastname;
	@Column(columnDefinition = "TEXT")
	@NonNull
	private String email;
	@Column(columnDefinition = "TEXT")
	@NonNull
	private String password;

	@Column(columnDefinition = "bigint")
	@Transient
	private Integer age;
	@OneToMany(targetEntity = Fourniture.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName = "id", name = "sf_fk")
	private List<Fourniture> fournitureList;

	@Column(columnDefinition = "TEXT")
	@NonNull
	private LocalDate dob;

	// For testing
	public Student(@NonNull Integer id,
				   @NonNull String firstname,
				   @NonNull String lastname,
				   @NonNull String email,
			       @NonNull String password,
				   @NonNull LocalDate dob,
				   @NonNull List<Fourniture> fournitureList
	) {
		this.id         = id;
		this.firstname	= firstname;
		this.lastname 	= lastname;
		this.email		= email;
		this.password	= password;
		this.dob 		= dob;
		this.fournitureList = fournitureList;
	}


	public Integer getAge() {
		return Period.between(this.dob,LocalDate.now()).getYears();
	}
	@Enumerated(EnumType.STRING)
	private Role role;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public @NonNull String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
