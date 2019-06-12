package brainstorming.service.rankingStrategy;

import brainstorming.model.Comentario;
import brainstorming.model.Grupo;
import brainstorming.model.Ideia;
import brainstorming.model.Participacao;
import brainstorming.model.Sessao;

public class RankingQuantitative implements RankingStrategy {

	public void updateRanking(Grupo grupo, Sessao sessao) {
		int pIdeia = 100;
		int pComentario = 10;
		for (Participacao p : grupo.getParticipacoes()) {
			int pontuacao = p.getPontos();
			for (Ideia i : p.getParticipante().getIdeias()) {
				if (i.getNo().getEstrutura().getSessao().getId().equals(sessao.getId()))
					pontuacao = pontuacao + pIdeia;
			}
			for (Comentario c : p.getParticipante().getComentarios()) {
				if(c.getIdeia().getNo().getEstrutura().getSessao().getId().equals(sessao.getId()))
					pontuacao = pontuacao + pComentario;
			}
			
			p.setPontos(pontuacao);
		}
		
	}

}
