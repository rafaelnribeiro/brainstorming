package brainstorming.controller.estrutura_factory;

import org.springframework.web.multipart.MultipartFile;

import brainstorming.model.estrutura.Estrutura;

public interface EstruturaFactory {
	public Estrutura create(MultipartFile f);
}
