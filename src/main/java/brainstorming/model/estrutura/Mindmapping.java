package brainstorming.model.estrutura;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mindmapping")
public class Mindmapping extends Estrutura{
	private static final long serialVersionUID = 1L;
	
	@OneToOne
	private Conceito raiz;

	public Conceito getRaiz() {
		return raiz;
	}

	public void setRaiz(Conceito raiz) {
		this.raiz = raiz;
	}

}
