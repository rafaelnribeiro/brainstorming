package brainstorming.controller.estrutura_factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

import brainstorming.model.estrutura.Conceito;
import brainstorming.model.estrutura.Estrutura;
import brainstorming.model.estrutura.Mindmapping;
import brainstorming.model.estrutura.No;
import javassist.expr.NewArray;

public class MindmappingFactory implements EstruturaFactory {

	public Estrutura create(MultipartFile f) throws IOException {
		Mindmapping mp = new Mindmapping();
		mp.setNos(new ArrayList<No>());
		Scanner sc = new Scanner(f.getInputStream());
		
		Pattern conceito = Pattern.compile("[a-zA-Z0-9]+");
		Pattern abreChaves = Pattern.compile("\\{");
		Pattern fechaChaves = Pattern.compile("\\}");
		
		Conceito pai;
		Conceito filho;
		
		if(sc.hasNext(conceito)) {
			pai = new Conceito();
			pai.setFilhos(new ArrayList<Conceito>());
			pai.setNome(sc.next(conceito));
			pai.setMindmapping(mp);
			pai.setEstrutura(mp);
			mp.getNos().add(pai);
			mp.setRaiz(pai);
			
			if(sc.hasNext(abreChaves)) {
				sc.next(abreChaves);
				int nivel = 1;
				while(nivel > 0) {
					if(sc.hasNext(conceito)) {
						filho = new Conceito();
						filho.setFilhos(new ArrayList<Conceito>());
						filho.setNome(sc.next(conceito));
						filho.setEstrutura(mp);
						filho.setPai(pai);
						pai.getFilhos().add(filho);
						mp.getNos().add(filho);
						
						if(sc.hasNext(abreChaves)) {
							sc.next(abreChaves);
							pai = filho;
							nivel ++;						
						}
					}
					
					if(sc.hasNext(fechaChaves)) {
						sc.next(fechaChaves);
						if(nivel > 1)
							pai = pai.getPai();
						nivel --;
					}
				}
			}
		}		
		sc.close();
		
		return mp;
	}

}
