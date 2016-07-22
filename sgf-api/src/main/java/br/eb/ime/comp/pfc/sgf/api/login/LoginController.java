package br.eb.ime.comp.pfc.sgf.api.login;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Client getClient(@RequestBody Client id) {
		/*
		 * id.getUsername() contém o email ou o número do usuário
		 */
		Client client = null;
		List<String> roles = new ArrayList<>();
		if (Utils.isEmail(id.getUsername())) {
			Professor professor = professorService.getByEmail(id.getUsername());
			if (!professor.equals(null)) {
				roles.add(Client.ROLE_PROFESSOR);
				if (professor.getCoordenador().equals(Professor.IS_COORDEADOR)) {
					roles.add(Client.ROLE_COORDENADOR);
				}
				client = new Client(professor.getEmail(), professor.getPassword(), roles);
			}
		} else if (Utils.isNumber(id.getUsername())) {
			Aluno aluno = alunoService.getByNumero(id.getUsername());
			if(!aluno.equals(null)){
				roles.add(Client.ROLE_ALUNO);
				client = new Client(aluno.getNumero(), aluno.getPassword(), roles);
			}
		}

		return client;
	}

}
