package brainstorming.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sugestao")
public class Sugestao implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(name = "texto")
	private String texto;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private User autor;
	
	@ManyToOne
	@JoinColumn(name = "id_ideia")
	private Ideia ideia;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public User getAutor() {
		return autor;
	}

	public void setAutor(User autor) {
		this.autor = autor;
	}

	public Ideia getIdeia() {
		return ideia;
	}

	public void setIdeia(Ideia ideia) {
		this.ideia = ideia;
	}
	
	
}
