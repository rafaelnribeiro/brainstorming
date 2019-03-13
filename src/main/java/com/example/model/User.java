package com.example.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "name")
	@NotEmpty(message = "Por favor forne�a seu nome")
	private String name;
	
	@Column(name = "email")
	@Email(message = "*Por favor forne�a um email v�lido")
	@NotEmpty(message = "*Por favor forne�a um email")
	private String email;
	
	@Column(name = "password")
	@Length(min = 5, message = "*A senha deve possuir no m�nimo 5 caracteres")
	@NotEmpty(message = "*Por favor forne�a sua senha")
	private String password;
	
	@Column(name = "active")
	private int active;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), 
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
	List<Ideia> Ideias;
	
	@OneToMany(mappedBy = "moderador", cascade = CascadeType.ALL)
	List<Sessao> sessoesModeradas;
	
	@ManyToMany(mappedBy = "participantes")
	private List<Sessao> sessoes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public List<Ideia> getIdeias() {
		return Ideias;
	}

	public void setIdeias(List<Ideia> ideias) {
		Ideias = ideias;
	}

	public List<Sessao> getSessoesModeradas() {
		return sessoesModeradas;
	}

	public void setSessoesModeradas(List<Sessao> sessoesModeradas) {
		this.sessoesModeradas = sessoesModeradas;
	}

	public List<Sessao> getSessoes() {
		return sessoes;
	}

	public void setSessoes(List<Sessao> sessoes) {
		this.sessoes = sessoes;
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
	
}
