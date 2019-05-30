package brainstorming.controller.estrutura_factory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import brainstorming.model.Ideia;
import brainstorming.model.estrutura.Divisor;
import brainstorming.model.estrutura.Estrutura;
import brainstorming.model.estrutura.Quadro;

public class DivisorFactory implements EstruturaFactory{
	
	public Estrutura create(File f) {
		//Temporario
		Divisor e = new Divisor();
		Quadro q1 = new Quadro();
		Quadro q2 = new Quadro();
		Quadro q3 = new Quadro();
		Quadro q4 = new Quadro();
		q1.setNome("Quadro 01");
		q2.setNome("Quadro 02");
		q3.setNome("Quadro 03");
		q4.setNome("Quadro 04");
		e.setQuadros(new ArrayList<Quadro>());
		e.getQuadros().add(q1);
		e.getQuadros().add(q2);
		e.getQuadros().add(q3);
		e.getQuadros().add(q4);
		q1.setDivisor(e);
		q2.setDivisor(e);
		q3.setDivisor(e);
		q4.setDivisor(e);
		
		Ideia id1 = new Ideia();
		id1.setTitulo("Ideia de Numero 1");
		Ideia id2 = new Ideia();
		id2.setTitulo("Ideia de Numero 2");
		Ideia id3 = new Ideia();
		id3.setTitulo("Ideia de Numero 3");
		Ideia id4 = new Ideia();
		id4.setTitulo("Ideia de Numero 4");
		Ideia id5 = new Ideia();
		id5.setTitulo("Ideia de Numero 5");
		Ideia id6 = new Ideia();
		id6.setTitulo("Ideia de Numero 6");
		Ideia id7 = new Ideia();
		id7.setTitulo("Ideia de Numero 7");
		Ideia id8 = new Ideia();
		id8.setTitulo("Ideia de Numero 8");
		
		q1.setIdeias(new ArrayList<Ideia>());
		q2.setIdeias(new ArrayList<Ideia>());
		q1.getIdeias().add(id1);
		q1.getIdeias().add(id2);
		q1.getIdeias().add(id3);
		q1.getIdeias().add(id4);
		q2.getIdeias().add(id5);
		q2.getIdeias().add(id6);
		q2.getIdeias().add(id7);
		q2.getIdeias().add(id8);
		
		id1.setNo(q1);
		id2.setNo(q1);
		id3.setNo(q1);
		id4.setNo(q1);
		id5.setNo(q2);
		id6.setNo(q2);
		id7.setNo(q2);
		id8.setNo(q2);

		return e;
	}
}


