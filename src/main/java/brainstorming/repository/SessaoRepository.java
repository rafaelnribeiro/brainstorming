package brainstorming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import brainstorming.model.Sessao;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Integer>{
	
	boolean existsByTema(String tema);
}
