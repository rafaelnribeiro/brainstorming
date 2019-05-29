package brainstorming.controller.estrutura_factory;

import java.io.File;

import brainstorming.model.estrutura.Estrutura;

public interface EstruturaFactory {
	public Estrutura create(File f);
}
