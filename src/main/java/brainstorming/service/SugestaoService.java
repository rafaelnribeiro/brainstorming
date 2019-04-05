package brainstorming.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import brainstorming.model.Comentario;
import brainstorming.model.Sugestao;
import brainstorming.repository.ComentarioRepository;
import brainstorming.repository.SugestaoRepository;
import brainstorming.util.exceptions.BusinessException;
import net.bytebuddy.asm.Advice.Return;

@Service
@Transactional(readOnly = true)
public class SugestaoService {
	
	@Autowired
	private SugestaoRepository sugestaoRepository;
	
	@Transactional(readOnly = false)
	public Sugestao save(Sugestao suges) throws BusinessException{
		return sugestaoRepository.save(suges);
	}
	
	public Optional<Sugestao> findOne(Integer id){
		return sugestaoRepository.findById(id);
	}
	
	public List<Sugestao> findAll(){
		return sugestaoRepository.findAll();
	}
	
}

 