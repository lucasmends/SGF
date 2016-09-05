package br.eb.ime.comp.pfc.sgf.web;

import br.eb.ime.comp.pfc.sgf.models.Aluno;
import br.eb.ime.comp.pfc.sgf.models.Ata;
import br.eb.ime.comp.pfc.sgf.models.Professor;
import br.eb.ime.comp.pfc.sgf.models.Tempo;

public class Assinatura {

	public static Ata assinar(Aluno xerife, Ata ata) {
		// se o xerife for difrente da ata
		if (!ata.getXerife().getXerife().equals(xerife))
			return null;
		// verifica se os tempos foram assinados
		for (Tempo tempo : ata.getTempos()) {
			if (tempo.getAssinatura() != null) {
				if (tempo.getAssinatura().getAssinatura() == null || tempo.getAssinatura().getAssinatura().equals("")) {
					return null;
				}
			} else
				return null;
		}
		ata.getXerife().setAssinatura(xerife.assinatura());

		return ata;

	}

	public static Ata assinarCoordenador(Professor coordenador, Ata ata) {
		if (!ata.getCoordenador().getCoordenador().equals(coordenador))
			return null;

		// verifica se os tempos foram assinados
		for (Tempo tempo : ata.getTempos()) {
			if (tempo.getAssinatura() != null) {
				if (tempo.getAssinatura().getAssinatura() == null || tempo.getAssinatura().getAssinatura().equals("")) {
					return null;
				}
			} else
				return null;
		}
		if (ata.getXerife().getAssinatura() == null || ata.getXerife().getAssinatura().equals(""))
			return null;

		ata.getCoordenador().setAssinatura(coordenador.assinatura());

		return ata;
	}

	public static Ata assinarProfessor(Professor professor, Ata ata, int tempo) {
		// se não é o professor do tempo
		if (!ata.getTempos().get(tempo).getAssinatura().getIdProfessor().equals(professor.getId()))
			return null;

		ata.getTempos().get(tempo).getAssinatura().setAssinatura(professor.assinatura());
		return ata;
	}
}
