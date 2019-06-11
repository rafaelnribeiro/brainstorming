package brainstorming.service.SolucaoStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import brainstorming.model.Ideia;
import brainstorming.model.Sessao;
import brainstorming.model.estrutura.No;

public class SolucaoLimitePorNo implements SolucaoStrategy{
	
	// Precisa de uma entrada do numero de ideia por nó
	public List<Ideia> calcSolucao(Sessao sessao) {
		
		// Valor definido pelo ADM
		int numSolporNo = 3;
		int qtd_sol = 6;
		List<Ideia> sol = new ArrayList<Ideia>();
		List<Ideia> aux = new ArrayList<Ideia>();
		
		for (No no : sessao.getEstrutura().getNos()) {
			aux.addAll(no.getIdeias());
			
			Collections.sort(aux);
			
			if(aux.size() > numSolporNo) {
				sol.addAll(aux.subList(0, numSolporNo));
			}else {
				sol.addAll(aux);
			}
			
			aux.clear();
					
		}
		// qtd_ideia = 3*qtd_no
		Collections.sort(sol);
		
		if(sol.size() > qtd_sol) {
			sol = sol.subList(0, qtd_sol);
		}
			
		return sol;
		
	}
}


