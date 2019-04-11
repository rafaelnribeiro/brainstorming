package brainstorming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import brainstorming.model.Ideia;

@Repository
public interface IdeiaRepository extends JpaRepository<Ideia, Integer> {

	@Query("SELECT count(i)>0 FROM Ideia i WHERE titulo = ?1 AND id!=?2")	
	boolean existsAnotherByTitulo(String titulo, Integer id);
	
	public boolean existsByTitulo(String titulo);
}
