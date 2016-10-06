package br.eb.ime.comp.pfc.sgf.web.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;

import br.eb.ime.comp.pfc.sgf.models.Aluno;
import br.eb.ime.comp.pfc.sgf.models.Professor;
import br.eb.ime.comp.pfc.sgf.models.Tempo;
import br.eb.ime.comp.pfc.sgf.models.Disciplina;
import br.eb.ime.comp.pfc.sgf.models.Falta;
import br.eb.ime.comp.pfc.sgf.models.Turma;
import br.eb.ime.comp.pfc.sgf.models.Xerife;
import br.eb.ime.comp.pfc.sgf.models.Ata;
import br.eb.ime.comp.pfc.sgf.models.Coordenador;
import br.eb.ime.comp.pfc.sgf.web.Assinatura;
import br.eb.ime.comp.pfc.sgf.web.User;
import br.eb.ime.comp.pfc.sgf.web.Utils;
import br.eb.ime.comp.pfc.sgf.web.service.AlunoService;
import br.eb.ime.comp.pfc.sgf.web.service.ProfessorService;
import br.eb.ime.comp.pfc.sgf.web.service.DisciplinaService;
import br.eb.ime.comp.pfc.sgf.web.service.TurmaService;
import br.eb.ime.comp.pfc.sgf.web.service.AtaService;

@Controller
@RequestMapping("/ata")
public class AtaController {

	@Autowired
	private AlunoService alunoService;
	@Autowired
	private ProfessorService professorService;
	@Autowired
	private DisciplinaService disciplinaService;
	@Autowired
	private TurmaService turmaService;
	@Autowired
	private AtaService service;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model, Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);
		List<Ata> atas;

		if (user.isCoordenador()) {
			String email = user.getName();
			Professor coordenador = professorService.getByEmail(email);
			atas = service.getByCoordenador(coordenador);
			
			//as atas que o coordenador é apenas professor
			List<Ata> atasProfessor = service.getByProfessor(coordenador);
			for(Ata ata : atasProfessor){
				if(!atas.contains(ata))
					atas.add(ata);
			}
		} else if (user.isAluno()) {
			String numero = user.getName();
			Aluno xerife = alunoService.getByNumero(numero);
			atas = service.getByXerife(xerife);
		} else {
			String email = user.getName();
			Professor professor = professorService.getByEmail(email);
			atas = service.getByProfessor(professor);
		}

		for (int i = 0; i < atas.size(); i++) {
			atas.get(i).setTurma(turmaService.getById(atas.get(i).getIdTurma()));
		}

		for(int i = 0; i < atas.size(); i++){
			atas.get(i).getCoordenador().setAssinado(Utils.recebeuAssinaturaCoordenador(atas.get(i).getCoordenador()));
		}
		
		model.addAttribute("title", "Atas");
		model.addAttribute("atas", atas);
		model.addAttribute("user", user);
		// return "ata/index";

		return "ata/index";
	}

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public String getById(@PathVariable("id") String id, Model model, Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);

		Ata ata = service.getById(id);

		if (user.isProfessor() || ata.getXerife().getXerife().getNumero().equals(user.getName())) {
			for (int i = 0; i < ata.getTempos().size(); i++) {
				//verificação da assinatura do tempo
					ata.getTempos().get(i).setSaved(Utils.recebeuAssinaturaProfessor(ata.getTempos().get(i)));
				
				/*
				 * Se o tempo não tem assinatura e o professor que irá ver a página é o 
				 * que deve assinar, libera o botão de assinar o tempo
				 */
				if (user.isProfessor()) {
					if(ata.getTempos().get(i).getDisciplina().getProfessor().getEmail().equals(user.getName())){
						ata.getTempos().get(i).setProfessorDoTempo(!ata.getTempos().get(i).isSaved());
					}
				}
			}
			
			//verifica se recebeu a assinatura do coordenador
			if(Utils.recebeuAssinaturaCoordenador(ata.getCoordenador()))
				ata.getCoordenador().setAssinado(true);
			
			ata.getXerife().setAssinado(Utils.recebeuAssinaturaXerife(ata.getXerife()));
			
			model.addAttribute("ata", ata);
			model.addAttribute("title", "Ata");
			model.addAttribute("user", user);

			/*
			 * Colocar a checagem de assinatura for(int i = 0; i <
			 * ata.getTempos().size(); i++){
			 * ata.getTempos().get(i).setSaved(true); }
			 */

			return "ata/ata";
		}
		return "redirect:/403";
	}

	@RequestMapping(value = "/todos", method = RequestMethod.GET)
	public String all(Model model) {
		return "redirect:/ata/";
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String newAta(Model model, Principal u, @RequestParam("data") String data) {
		User user = new User((UsernamePasswordAuthenticationToken) u);

		if (!user.isAluno())
			return "redirect:/403";

		// List<Turma> turmas = turmaService.getAll();
		// obter turma do xerife
		String numero = user.getName();
		Aluno xerife = alunoService.getByNumero(numero);
		Turma turmaDoXerife = turmaService.getByAluno(xerife);
		
		/*
		 * for(int i=0; i<turmas.size(); i++) { List<Aluno> alunosDaTurma =
		 * turmas.get(i).getAlunos(); for(int j=0; j<alunosDaTurma.size(); j++)
		 * { if(alunosDaTurma.get(j).getId() == xerife.getId()) { turmaDoXerife
		 * = turmas.get(i); } } }
		 */

		if (turmaDoXerife == null)
			return "redirect:/403";

		Ata ata = new Ata();
		ata.setData(data);
		ata.setXerife(new Xerife(xerife));
		ata.setTurma(turmaDoXerife.getId());
		ata.setCoordenador(new Coordenador(turmaService.getCoordenador(turmaDoXerife), null));
		ata.getTempos().add(new Tempo("1"));
		ata = service.create(ata);

		return "redirect:" + "/ata/edit/" + ata.getId();
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editAta(@PathVariable("id") String id, Model model, Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);

		if (!user.isAluno())
			return "redirect:/403";

		Ata ata = service.getById(id);

		if (!ata.getXerife().getXerife().getNumero().equals(user.getName()))
			return "redirect:/403";
		//verifica se o xerife já assinou a ata
		if (Utils.recebeuAssinaturaXerife(ata.getXerife())) {
			model.addAttribute("title", "Ata de Faltas");
			model.addAttribute("ata", ata);
			model.addAttribute("user", user);
			return "ata/assinadaXerife";
		}

		Turma turmaDoXerife = turmaService.getById(ata.getIdTurma());

		for (int i = 0; i < ata.getTempos().size(); i++) {
			for (Aluno aluno : turmaDoXerife.getAlunos()) {
				// se o aluno não está na falta
				if (!ata.getTempos().get(i).getFaltas().contains(aluno)) {
					ata.getTempos().get(i).getAlunosAvulsos().add(aluno);
				}
			}

			//para colocar o footer nos tempos
			ata.getTempos().get(i).setSaved(true);
			ata.getTempos().get(i).setDisciplinasAvulsas(turmaDoXerife.getDisciplinas());
			ata.getTempos().get(i).getDisciplinasAvulsas().remove(ata.getTempos().get(i).getDisciplina());
		}

		model.addAttribute("ata", ata);
		model.addAttribute("disciplinas", turmaDoXerife.getDisciplinas());
		model.addAttribute("title", "Editar Ata de Faltas");
		model.addAttribute("user", user);
		return "ata/edit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String saveAta(@PathVariable("id") String id, @RequestParam("ordem") String ordem, Model model,
			@RequestParam(value = "alunos", required = false, defaultValue = "") String[] alunos,
			@RequestParam("disciplina") String idDisciplina, Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);

		if (!user.isAluno())
			return "redirect:/403";

		Ata ata = service.getById(id);
		if (!ata.getXerife().getXerife().getNumero().equals(user.getName()))
			return "redirect:/403";
		//verifica se o xerife já assinou a ata
		if (Utils.recebeuAssinaturaXerife(ata.getXerife())) {
			model.addAttribute("title", "Ata de Faltas");
			model.addAttribute("ata", ata);
			model.addAttribute("user", user);
			return "ata/assinadaXerife";
		}

		Turma turmaDoXerife = turmaService.getById(ata.getIdTurma());
		Tempo tempo = new Tempo();
		tempo.setOrdem(ordem);

		// procurar aluno pelo numero
		for (String numero : alunos) {
			Aluno actual = alunoService.getByNumero(numero);
			if (actual == null)
				continue;
			tempo.getFaltas().add(actual);
			int i;
			for (i = 0; i < ata.getFaltas().size(); i++) {
				if (ata.getFaltas().get(i).getAluno().equals(actual))
					break;
			}
			if (i >= ata.getFaltas().size()) {
				ata.getFaltas().add(new Falta(actual, "Destino Indeterminado"));
			}
		}

		// disciplina
		Disciplina disciplina = disciplinaService.getById(idDisciplina);
		tempo.setDisciplina(disciplina);

		int count;
		// verifica se o tempo já existe
		for (count = 0; count < ata.getTempos().size(); count++) {
			if (ata.getTempos().get(count).getOrdem().equals(ordem)) {
				ata.getTempos().set(count, tempo);
				break;
			}
		}
		// se não adiciona ele
		if (count >= ata.getTempos().size()) {
			ata.getTempos().add(tempo);
		}

		service.update(ata);

		for (int i = 0; i < ata.getTempos().size(); i++) {
			for (Aluno aluno : turmaDoXerife.getAlunos()) {
				// se o aluno não está na falta
				if (!ata.getTempos().get(i).getFaltas().contains(aluno)) {
					ata.getTempos().get(i).getAlunosAvulsos().add(aluno);
				}
			}

			ata.getTempos().get(i).setSaved(true);
			ata.getTempos().get(i).setDisciplinasAvulsas(turmaDoXerife.getDisciplinas());
			ata.getTempos().get(i).getDisciplinasAvulsas().remove(ata.getTempos().get(i).getDisciplina());
		}

		Integer newOrdem = Integer.parseInt(ordem) + 1;
		ata.getTempos().add(new Tempo(newOrdem.toString(), turmaDoXerife.getDisciplinas(), turmaDoXerife.getAlunos()));

		model.addAttribute("ata", ata);
		model.addAttribute("user", user);
		model.addAttribute("title", "Editar Ata de Faltas");
		return "ata/edit";
	}

	@RequestMapping(value = "/justificar/{id}/{numero}", method = RequestMethod.GET)
	public String justificarAta(@PathVariable("id") String id, @PathVariable("numero") String numero, Model model,
			Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);
		if (!user.isAluno())
			return "redirect:/403";

		Ata ata = service.getById(id);
		// if(!ata.getXerife().getXerife().getNumero().equals(user.getName()))
		// return "redirect:/403";
		Falta falta = null;
		for (Falta f : ata.getFaltas()) {
			if (f.getAluno().getNumero().equals(numero)) {
				falta = f;
				break;
			}
		}
		model.addAttribute("title", "Justificar Faltas");
		model.addAttribute("ata", ata);
		model.addAttribute("falta", falta);
		model.addAttribute("user", user);
		return "ata/justificar";
	}

	@RequestMapping(value = "/justificar/{id}", method = RequestMethod.POST)
	public String salvarJustificativa(@PathVariable("id") String id, @RequestParam("numero") String numero,
			@RequestParam("motivo") String motivo, Model model, Principal u) {
		User user = new User((UsernamePasswordAuthenticationToken) u);
		if (!user.isAluno())
			return "redirect:/403";

		Ata ata = service.getById(id);
		for (int i = 0; i < ata.getFaltas().size(); i++) {
			if (ata.getFaltas().get(i).getAluno().getNumero().equals(numero)) {
				ata.getFaltas().get(i).setMotivo(motivo);
				break;
			}
		}

		service.update(ata);
		return "redirect:" + "/ata/edit/" + ata.getId();
	}

	@RequestMapping(value = "/assinar/aluno/{id}", method = RequestMethod.GET)
	public String alunoAssinar(@PathVariable("id") String id, Principal u, Model model) {
		User user = new User((UsernamePasswordAuthenticationToken) u);
		if (!user.isAluno())
			return "redirect:/403";
		Ata ata = service.getById(id);
		//verifica se o xerife é o da ata
		if (!ata.getXerife().getXerife().getNumero().equals(user.getName()))
			return "redirect:/403";

		Aluno xerife = alunoService.getByNumero(user.getName());
		ata = Assinatura.assinar(xerife, ata);
		if(ata == null){
			model.addAttribute("user", user);
			model.addAttribute("title", "Erro");
			return "ata/erro";
		}
		service.update(ata);
		return "redirect:" + "/ata/id/" + ata.getId();

	}

	@RequestMapping(value = "/assinar/coordenador/{id}", method = RequestMethod.GET)
	public String coordenadorAssinar(@PathVariable("id") String id, Principal u, Model model) {
		User user = new User((UsernamePasswordAuthenticationToken) u);
		if (!user.isCoordenador())
			return "redirect:/403";
		Ata ata = service.getById(id);
		//verifica se o coordenador é o da ata
		if (!ata.getCoordenador().getCoordenador().getEmail().equals(user.getName()))
			return "redirect:/403";

		Professor coordenador = professorService.getByEmail(user.getName());
		
		ata = Assinatura.assinarCoordenador(coordenador, ata);
		if(ata == null){
			model.addAttribute("user", user);
			model.addAttribute("title", "Erro");
			return "/ata/erro";
		}
		service.update(ata);
		
		return "redirect:" + "/ata/id/" + ata.getId();
	}
	
	@RequestMapping(value = "/assinar/professor/{id}/tempo/{ordem}", method = RequestMethod.GET)
	public String professorAssinar(@PathVariable("id") String id, @PathVariable("ordem") String ordem, Principal u, Model model) {
		User user = new User((UsernamePasswordAuthenticationToken) u);
		if (!user.isProfessor())
			return "redirect:/403";
		Ata ata = service.getById(id);
		Integer ind = Integer.parseInt(ordem) - 1;
		//verifica se o professor é o do tempo
		if (!ata.getTempos().get(ind).getDisciplina().getProfessor().getEmail().equals(user.getName()))
			return "redirect:/403";

		Professor professor = professorService.getByEmail(user.getName());
		ata = Assinatura.assinarProfessor(professor, ata, ind);
		if(ata == null){
			model.addAttribute("user", user);
			model.addAttribute("title", "Erro");
			return "ata/erro";
		}
		
		service.update(ata);
		
		return "redirect:" + "/ata/id/" + ata.getId();
	}
}
