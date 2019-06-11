package brainstorming.model.estrutura;

import java.util.List;import javax.persistence.CascadeType;import javax.persistence.Entity;import javax.persistence.JoinColumn;import javax.persistence.ManyToOne;import javax.persistence.OneToMany;import javax.persistence.OneToOne;import javax.persistence.Table;

@Entity
@Table(name = "conceito")
public class Conceito extends No{	private static final long serialVersionUID = 1L;	@ManyToOne
	@JoinColumn(name = "id_pai")
	private Conceito pai;
	
	@OneToMany(mappedBy = "pai", cascade = CascadeType.ALL)
	private List<Conceito> filhos;
	
	@OneToOne(mappedBy = "raiz")
	private Mindmapping mindmapping;	public Conceito getPai() {		return pai;	}	public void setPai(Conceito pai) {		this.pai = pai;	}	public List<Conceito> getFilhos() {		return filhos;	}	public void setFilhos(List<Conceito> filhos) {		this.filhos = filhos;	}	public Mindmapping getMindmapping() {		return mindmapping;	}	public void setMindmapping(Mindmapping mindmapping) {		this.mindmapping = mindmapping;	}
}
