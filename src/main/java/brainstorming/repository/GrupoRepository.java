package brainstorming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import brainstorming.model.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Integer>{
	
	public boolean existsByNome(String nome);
}
