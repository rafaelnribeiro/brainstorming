package brainstorming.controller.estrutura_factory;

import java.io.File;

import brainstorming.model.estrutura.Estrutura;
import brainstorming.model.estrutura.Step;
import brainstorming.model.estrutura.Storyboard;

public class StoryboardFactory implements EstruturaFactory {

	public Estrutura create(File f) {
		// TODO Temporario
		Storyboard e = new Storyboard();
		Step s1 = new Step();
		Step s2 = new Step();
		Step s3 = new Step();
		Step s4 = new Step();
		e.setFirst(s1);
		s1.setStoryboard(e);
		s1.setNome("Passo 1");
		s1.setRight(s2);
		s2.setNome("Passo 2");
		s2.setLeft(s1);
		s2.setRight(s3);
		s3.setNome("Passo 3");
		s3.setLeft(s2);
		s3.setRight(s4);
		s4.setNome("Passo 4");
		s4.setLeft(s3);
		return e;
	}

}
