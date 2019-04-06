package brainstorming.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "solicitacao")
public class Solicitacao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(name = "novoTitulo")
	private String novoTitulo;
	
	@Column(name = "novaDescricao")
	private String novaDescricao;
	
	@OneToOne
	private Sugestao sugestao;
	
	@ManyToOne
	@JoinColumn(name = "id_ideia")
	private Ideia ideia;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNovoTitulo() {
		return novoTitulo;
	}

	public void setNovoTitulo(String novoTitulo) {
		this.novoTitulo = novoTitulo;
	}

	public String getNovaDescricao() {
		return novaDescricao;
	}

	public void setNovaDescricao(String novaDescricao) {
		this.novaDescricao = novaDescricao;
	}

	public Sugestao getSugestao() {
		return sugestao;
	}

	public void setSugestao(Sugestao sugestao) {
		this.sugestao = sugestao;
	}

	public Ideia getIdeia() {
		return ideia;
	}

	public void setIdeia(Ideia ideia) {
		this.ideia = ideia;
	}	
}
