package brainstorming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstorming.model.Ideia;
import brainstorming.repository.IdeiaRepository;

@Service
@Transactional(readOnly = true)
public class IdeiaService {
	
	@Autowired
	private IdeiaRepository ideiaRepository;
	
	public List<Ideia> findAll() {
		return ideiaRepository.findAll();
	}
	
	public Optional<Ideia> findOne(Integer id) {
		return ideiaRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Ideia save(Ideia entity) {
		return ideiaRepository.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete (Ideia entity) {
		ideiaRepository.delete(entity);
	}
}
