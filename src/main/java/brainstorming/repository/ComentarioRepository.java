package brainstorming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import brainstorming.model.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Integer>{
	
		

}
