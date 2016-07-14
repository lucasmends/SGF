package br.eb.ime.comp.pfc.sgf.api.login;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.eb.ime.comp.pfc.sgf.models.Aluno;
import br.eb.ime.comp.pfc.sgf.models.Client;
import br.eb.ime.comp.pfc.sgf.models.Professor;

@RestController
public class LoginController {

	@Autowired
	private ProfessorService professorService;

	@Autowired
	private AlunoService alunoService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Client getClient(@PathVariable("id") String id) {

		Client client = null;
		List<String> roles = new ArrayList<>();
		if (Utils.isEmail(id)) {
			Professor professor = professorService.getByEmail(id);
			if (!professor.equals(null)) {
				roles.add("professor");
				if (professor.getCoordenador().equals(Professor.IS_COORDEADOR)) {
					roles.add("coordenador");
				}
				client = new Client(professor.getEmail(), professor.getPassword(), roles);
			}
		} else if (Utils.isNumber(id)) {
			Aluno aluno = alunoService.getByNumero(id);
			if(!aluno.equals(null)){
				roles.add("aluno");
				client = new Client(aluno.getNumero(), aluno.getPassword(), roles);
			}
		}

		return client;
	}

}
