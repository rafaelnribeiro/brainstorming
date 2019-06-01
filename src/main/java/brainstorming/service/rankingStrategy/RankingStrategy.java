package brainstorming.service.rankingStrategy;

import brainstorming.model.Grupo;
import brainstorming.model.Sessao;

public interface RankingStrategy {
	public void updateRanking(Grupo grupo, Sessao sessao);
}
