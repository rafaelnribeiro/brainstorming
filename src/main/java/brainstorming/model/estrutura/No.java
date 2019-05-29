package brainstorming.model.estrutura;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import brainstorming.model.Ideia;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class No implements Serializable{	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@OneToMany(mappedBy = "no", cascade = CascadeType.ALL)
	private List<Ideia> ideias; 
	
	@Column(name = "nome")
	private String nome;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Ideia> getIdeias() {
		return ideias;
	}

	public void setIdeias(List<Ideia> ideias) {
		this.ideias = ideias;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
