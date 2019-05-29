package brainstorming.repository.estrutura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import brainstorming.model.estrutura.No;

@Repository
public interface NoRepository<T extends No> extends JpaRepository<T, Integer> {

}
