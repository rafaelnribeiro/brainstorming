package brainstorming.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import brainstorming.model.Sessao;
import brainstorming.repository.SessaoRepository;
import brainstorming.util.exceptions.BusinessException;

@Service
@Transactional(readOnly = true)
public class SessaoService {

	@Autowired
	private SessaoRepository sessaoRepository;
	
	public List<Sessao> findAll() {
		return sessaoRepository.findAll();
	}
	
	public Optional<Sessao> findOne(Integer id) {
		return sessaoRepository.findById(id);
	}
	
	@Transactional(readOnly = false)
	public Sessao save(Sessao entity) throws BusinessException {
		List<Sessao> list_sessao = sessaoRepository.findAll();
		for (Sessao sessao: list_sessao) {
			if(sessao.getTema() == entity.getTema()) {
				throw new BusinessException("Já existe uma sessão com esse tema");
			}
		}
		
		/*if (condition) {
			
		}*/
		return sessaoRepository.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void delete(Sessao entity) {
		sessaoRepository.delete(entity);
	}
}
