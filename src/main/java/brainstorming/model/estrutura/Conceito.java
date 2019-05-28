package brainstorming.model.estrutura;

import java.util.List;import javax.persistence.CascadeType;import javax.persistence.Entity;import javax.persistence.JoinColumn;import javax.persistence.ManyToOne;import javax.persistence.OneToMany;import javax.persistence.OneToOne;import javax.persistence.Table;

@Entity
@Table(name = "conceito")
public class Conceito extends No{
	@ManyToOne
	@JoinColumn(name = "id_pai")
	private Conceito pai;
	
	@OneToMany(mappedBy = "pai", cascade = CascadeType.ALL)
	private List<Conceito> filhos;
	
	@OneToOne(mappedBy = "conceito")
	private Mindmapping mindmapping;

}
