package brainstorming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import brainstorming.model.Sessao;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Integer>{
	
	@Query("SELECT count(s)>0 FROM Sessao s WHERE tema = ?1 AND id!=?2")	
	boolean existsAnotherByTema(String tema, Integer id);
	
	boolean existsByTema(String tema);
}
