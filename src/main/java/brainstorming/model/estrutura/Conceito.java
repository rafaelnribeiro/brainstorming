package brainstorming.model.estrutura;

import java.util.List;

@Entity
@Table(name = "conceito")
public class Conceito extends No{
	@JoinColumn(name = "id_pai")
	private Conceito pai;
	
	@OneToMany(mappedBy = "pai", cascade = CascadeType.ALL)
	private List<Conceito> filhos;
	
	@OneToOne(mappedBy = "raiz")
	private Mindmapping mindmapping;
}