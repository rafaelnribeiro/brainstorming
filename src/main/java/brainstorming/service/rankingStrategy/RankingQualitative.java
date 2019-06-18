package brainstorming.service.rankingStrategy;


import brainstorming.model.Grupo;
import brainstorming.model.Ideia;
import brainstorming.model.Participacao;
import brainstorming.model.Sessao;

public class RankingQualitative implements RankingStrategy{

	public void updateRanking(Grupo grupo, Sessao sessao) {
		for (Participacao p : grupo.getParticipacoes()) {
			int pontuacao = p.getPontos();
			for (Ideia i : p.getParticipante().getIdeias()) {
				if (i.getNo().getEstrutura().getSessao().getId().equals(sessao.getId()))
					pontuacao = pontuacao + i.getVotantes().size();
			}
						
			p.setPontos(pontuacao);
		}
	}

}
