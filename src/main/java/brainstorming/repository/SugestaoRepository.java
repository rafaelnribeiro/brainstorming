package brainstorming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import brainstorming.model.Comentario;
import brainstorming.model.Sugestao;

@Repository
public interface SugestaoRepository extends JpaRepository<Sugestao, Integer>{
	
		

}
 