package net.projekt.springboot.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "first_name")
	private String firstName;

	@NotNull
	@Column(name = "last_name")
	private String lastName;

	@Pattern(regexp = "^\\S+@\\S+$")
	private String email;

	@NotNull
	private String country;

	@NotNull
	private String username;

	@NotNull
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "haslo musi zawierac conajmniej 8 znaków" +
			"w tym conajmniej jedna litera mała oraz duża i jedna cyfra ")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(
					name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;

	@JsonBackReference
	@ManyToMany(mappedBy = "userId", fetch = FetchType.EAGER)
	private Set<Application> appId = new HashSet<>();

	public User(String firstName, String lastName, String email, String country, String password, String username, Collection<Role> roles) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.country = country;
		this.password = password;
		this.username = username;
		this.roles = roles;
	}

	@Override
	public String toString() {
		return id +
				"," + firstName +
				"," + lastName +
				"," + email +
				"," + country +
				"," + username;

	}
}
