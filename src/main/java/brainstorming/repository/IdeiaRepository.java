package brainstorming.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import brainstorming.model.Ideia;

@Repository
public interface IdeiaRepository extends JpaRepository<Ideia, Integer> {

	public boolean existsByTitulo(String titulo);
}
