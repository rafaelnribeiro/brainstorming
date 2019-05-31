package brainstorming.controller.estrutura_factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.web.multipart.MultipartFile;

import brainstorming.model.estrutura.Divisor;
import brainstorming.model.estrutura.Estrutura;
import brainstorming.model.estrutura.No;
import brainstorming.model.estrutura.Quadro;

public class DivisorFactory implements EstruturaFactory{
	
	public Estrutura create(MultipartFile f) throws IOException{
		Divisor divisor = new Divisor();
		divisor.setQuadros(new ArrayList<Quadro>());
		divisor.setNos(new ArrayList<No>());
		Scanner sc = new Scanner(f.getInputStream());
		
		while(sc.hasNextLine()) {
			Quadro quadro = new Quadro();
			quadro.setNome(sc.nextLine());
			quadro.setDivisor(divisor);
			quadro.setEstrutura(divisor);
			divisor.getQuadros().add(quadro);
			divisor.getNos().add(quadro);
		}		
		sc.close();
		
		return divisor;
	}
}


