package brainstorming.service.SolucaoStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import brainstorming.model.Ideia;
import brainstorming.model.Sessao;
import brainstorming.model.estrutura.No;

public class SolucaoMaisVotadaPorNo implements SolucaoStrategy{

		public List<Ideia> calcSolucao(Sessao sessao) {
			int numSolporNo = 2;
			List<Ideia> sol = new ArrayList<Ideia>();
			List<Ideia> aux = new ArrayList<Ideia>();
			
			for (No no : sessao.getEstrutura().getNos()) {
				aux.addAll(no.getIdeias());
				
				Collections.sort(aux);
				
				if(aux.size() > numSolporNo) {
					sol.addAll(aux.subList(0, numSolporNo -1));
				}else {
					sol.addAll(aux);
				}
				
				aux.clear();
				
			}
			
			return sol;
		}

}
