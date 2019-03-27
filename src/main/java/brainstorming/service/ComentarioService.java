package brainstorming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstorming.model.Comentario;
import brainstorming.repository.ComentarioRepository;
import brainstorming.util.exceptions.BusinessException;

@Service
@Transactional(readOnly = true)
public class ComentarioService {
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Transactional(readOnly = false)
	public Comentario save(Comentario vote) throws BusinessException{
		return comentarioRepository.save(vote);
	}
	
	public Optional<Comentario> findOne(Integer id) {
		return comentarioRepository.findById(id);
	}
	
	public List<Comentario> findAll(){
		return comentarioRepository.findAll();
	}
	
	
}
