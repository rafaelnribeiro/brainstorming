package brainstorming.controller.estrutura_factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.web.multipart.MultipartFile;

import brainstorming.model.estrutura.Estrutura;
import brainstorming.model.estrutura.No;
import brainstorming.model.estrutura.Quadro;
import brainstorming.model.estrutura.Step;
import brainstorming.model.estrutura.Storyboard;

public class StoryboardFactory implements EstruturaFactory {

	public Estrutura create(MultipartFile f) throws IOException {
		Storyboard storyBoard = new Storyboard();
		storyBoard.setNos(new ArrayList<No>());
		Step current, next;
		
		Scanner sc = new Scanner(f.getInputStream());
		
		if(sc.hasNextLine()) {
			current = new Step();
			current.setNome(sc.nextLine());
			current.setStoryboard(storyBoard);
			current.setEstrutura(storyBoard);
			storyBoard.setFirst(current);
			storyBoard.getNos().add(current);
			
			while(sc.hasNextLine()) {
				next = new Step();
				next.setNome(sc.nextLine());
				next.setLeft(current);
				current.setRight(next);
				next.setEstrutura(storyBoard);
				storyBoard.getNos().add(next);
				current = next;
			}
		}		
		sc.close();
		
		return storyBoard;
	}

}
