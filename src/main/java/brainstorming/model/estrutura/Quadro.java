package brainstorming.model.estrutura;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "quadro")
public class Quadro extends No{
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name = "id_divisor")
	private Divisor divisor;

	public Divisor getDivisor() {
		return divisor;
	}

	public void setDivisor(Divisor divisor) {
		this.divisor = divisor;
	}	
}
