package brainstorming.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "grupo")
public class Grupo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nome")
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "id_administrador")
	private User administrador;
	
	@OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
	private List<Participacao> participacoes;
	
	@ManyToMany
	@JoinTable(name = "moderador_grupo")
	private List<User> moderadores;
	
	@OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
	private List <Sessao> sessoes;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public User getAdministrador() {
		return administrador;
	}

	public void setAdministrador(User administrador) {
		this.administrador = administrador;
	}

	public List<Sessao> getSessoes() {
		return sessoes;
	}

	public void setSessoes(List<Sessao> sessoes) {
		this.sessoes = sessoes;
	}

	public List<User> getModeradores() {
		return moderadores;
	}
	
	public void setModeradores(List<User> moderadores) {
		this.moderadores = moderadores;
	}

	public List<Participacao> getParticipacoes() {
		return participacoes;
	}

	public void setParticipacoes(List<Participacao> participacoes) {
		this.participacoes = participacoes;
	}
	
	public List<User> getParticipantes(){
		List<User> u = new ArrayList<User>();
		for (Participacao part : this.participacoes) {
			u.add(part.getParticipante());
		}
		return u;
	}

	@Override
	public String toString() {
		return this.nome;
	}
	
}
