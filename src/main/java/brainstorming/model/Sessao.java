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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import brainstorming.model.estrutura.Estrutura;

@Entity
@Table(name = "sessao")
public class Sessao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "tema")
	private String tema;
	
	@Column(name = "detalhes")
	private String detalhes;
	
	@Column(name = "limite_ideias")
	private Integer limite_ideias;
	
	@Column(name = "finalizado")
	private boolean finalizado;

	
	public Boolean getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}

	@ManyToOne
	@JoinColumn(name = "id_grupo")
	private Grupo grupo;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Estrutura estrutura;
	
	@OneToMany(mappedBy = "sessaoSolucionada", cascade = CascadeType.ALL)
	private List<Ideia> solucao;

	public List<Ideia> getSolucao() {
		return solucao;
	}

	public void setSolucao(List<Ideia> solucao) {
		this.solucao = solucao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}


	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Integer getLimite_ideias() {
		return limite_ideias;
	}

	public void setLimite_ideias(Integer limite_ideias) {
		this.limite_ideias = limite_ideias;
	}

	public Estrutura getEstrutura() {
		return estrutura;
	}

	public void setEstrutura(Estrutura estrutura) {
		this.estrutura = estrutura;
	}
	
}
