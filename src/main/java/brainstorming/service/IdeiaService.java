package brainstorming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstorming.model.Ideia;
import brainstorming.repository.IdeiaRepository;
import brainstorming.util.exceptions.BusinessException;

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
	public Ideia save(Ideia entity) throws BusinessException {
		if (entity.getTitulo().trim().isEmpty()) {
			throw new BusinessException("Titulo vazio");
		}
		
		List<Ideia> list_ideias = ideiaRepository.findAll();
		for (Ideia ideia : list_ideias) {
			if(ideia.getTitulo() == entity.getTitulo()) {
				throw new BusinessException("J� existe uma ideia com esse titulo");
			}
		}
		
		
		return ideiaRepository.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete (Ideia entity) {
		ideiaRepository.delete(entity);
	}
}
