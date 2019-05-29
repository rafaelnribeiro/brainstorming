package brainstorming.model.estrutura;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "divisor")
public class Divisor extends Estrutura{
	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "divisor", cascade = CascadeType.ALL)
	private List<Quadro> quadros;

	public List<Quadro> getQuadros() {
		return quadros;
	}

	public void setQuadros(List<Quadro> quadros) {
		this.quadros = quadros;
	}
}
