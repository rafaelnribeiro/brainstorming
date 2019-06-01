package brainstorming.model;

import java.io.Serializable;
import java.util.ArrayList;
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

@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;	
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "active")
	private int active;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), 
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
	private List<Ideia> Ideias;
	
	@OneToMany(mappedBy = "participante", cascade = CascadeType.ALL)
	private List<Participacao> participacoes;
	
	@ManyToMany(mappedBy = "moderadores")
	private List<Grupo> gruposModerados;
	
	@OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL)
	private List<Grupo> gruposAdministrados;
	
	@OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
	private List<Comentario> comentarios;
	
	@ManyToMany(mappedBy = "votantes")
	private List<Ideia> ideiasVotadas;

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

	public List<Grupo> getGruposAdministrados() {
		return gruposAdministrados;
	}

	public void setGruposAdministrados(List<Grupo> gruposAdministrados) {
		this.gruposAdministrados = gruposAdministrados;
	}

	public List<Grupo> getGruposModerados() {
		return gruposModerados;
	}

	public void setGruposModerados(List<Grupo> gruposModerados) {
		this.gruposModerados = gruposModerados;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public List<Ideia> getIdeiasVotadas() {
		return ideiasVotadas;
	}

	public void setIdeiasVotadas(List<Ideia> ideiasVotadas) {
		this.ideiasVotadas = ideiasVotadas;
	}

	public List<Participacao> getParticipacoes() {
		return participacoes;
	}

	public void setParticipacoes(List<Participacao> participacoes) {
		this.participacoes = participacoes;
	}
	
	public List<Grupo> getGrupos() {
		List<Grupo> g = new ArrayList<Grupo>();
		for (Participacao part : this.participacoes) {
			g.add(part.getGrupo());
		}
		return g;
	}

	@Override
	public String toString() {
		return this.getName();
	}
	
	
}
