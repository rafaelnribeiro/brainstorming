package brainstorming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import brainstorming.model.User;
import brainstorming.model.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Integer>{
	
		

}
