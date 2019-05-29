package brainstorming.controller.estrutura_factory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import brainstorming.model.estrutura.Divisor;
import brainstorming.model.estrutura.Estrutura;
import brainstorming.model.estrutura.Quadro;

public class DivisorFactory implements EstruturaFactory{
	
	public Estrutura create(File f) {
		//Temporario
		Divisor e = new Divisor();
		Quadro q1 = new Quadro();
		Quadro q2 = new Quadro();
		q1.setNome("Quadro 01");
		q2.setNome("Quadro 02");
		e.setQuadros(new ArrayList<Quadro>());
		e.getQuadros().add(q1);
		e.getQuadros().add(q2);
		return e;
	}
}

