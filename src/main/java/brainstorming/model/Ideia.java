package brainstorming.model;

import java.io.Serializable;
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

import brainstorming.model.estrutura.No;

@Entity
@Table(name = "ideia")
public class Ideia implements Serializable, Comparable<Ideia>{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "descricao")
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private User autor;
	
	@ManyToOne
	@JoinColumn(name = "id_sessao")
	private Sessao sessaoSolucionada;
	
	@ManyToOne
	@JoinColumn(name = "id_no")
	private No no;
	
	@OneToMany(mappedBy = "ideia", cascade = CascadeType.ALL)
	private List<Comentario> comentarios;
	
	@OneToMany(mappedBy = "ideia", cascade = CascadeType.ALL)
	private List<Sugestao> sugestoes;
	
	@ManyToMany
	@JoinTable(name="votante_ideia")
	private List<User> votantes;
	
	@OneToMany(mappedBy = "ideia", cascade = CascadeType.ALL)
	private List<Solicitacao> solicitacoes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public User getAutor() {
		return autor;
	}

	public void setAutor(User autor) {
		this.autor = autor;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public List<User> getVotantes() {
		return votantes;
	}

	public void setVotantes(List<User> votantes) {
		this.votantes = votantes;
	}

	public List<Sugestao> getSugestoes() {
		return sugestoes;
	}

	public void setSugestoes(List<Sugestao> sugestoes) {
		this.sugestoes = sugestoes;
	}

	public No getNo() {
		return no;
	}

	public void setNo(No no) {
		this.no = no;
	}

	public Sessao getSessaoSolucionada() {
		return sessaoSolucionada;
	}

	public void setSessaoSolucionada(Sessao sessaoSolucionada) {
		this.sessaoSolucionada = sessaoSolucionada;
	}

	public List<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(List<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

	public int compareTo(Ideia o) {
		return o.getVotantes().size() - getVotantes().size();
	}
	
}
