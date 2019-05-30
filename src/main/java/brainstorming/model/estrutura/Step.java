package brainstorming.model.estrutura;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "step")
public class Step extends No {
	private static final long serialVersionUID = 1L;

	@OneToOne(cascade = CascadeType.ALL)
	private Step left;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Step right;
	
	@OneToOne(mappedBy = "first")
	private Storyboard storyboard;

	public Step getLeft() {
		return left;
	}

	public void setLeft(Step left) {
		this.left = left;
	}

	public Step getRight() {
		return right;
	}

	public void setRight(Step right) {
		this.right = right;
	}

	public Storyboard getStoryboard() {
		return storyboard;
	}

	public void setStoryboard(Storyboard storyboard) {
		this.storyboard = storyboard;
	}	
}
