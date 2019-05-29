package brainstorming.repository.estrutura;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import brainstorming.model.estrutura.Estrutura;

@Repository
public interface EstruturaRepository<T extends Estrutura> extends JpaRepository<T , Integer> {

}
