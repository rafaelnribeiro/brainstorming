package brainstorming.service.SolucaoStrategy;

import java.util.List;

import brainstorming.model.Ideia;
import brainstorming.model.Sessao;

public interface SolucaoStrategy {
	public List<Ideia> calcSolucao(Sessao sessao);

}
