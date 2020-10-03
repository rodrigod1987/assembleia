package assembleia.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import assembleia.domain.dto.PautaDTO;
import assembleia.domain.dto.ResumoVotacaoDTO;
import assembleia.domain.model.Associado;
import assembleia.domain.model.Pauta;
import assembleia.exception.AssociadoNaoEncontradoException;
import assembleia.exception.CpfInvalidoException;
import assembleia.exception.PautaNotFoundException;
import assembleia.service.PautaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/pauta")
public class PautaController {
	
	@Autowired
	private PautaService service;
	
	@PostMapping
	public Pauta nova(@RequestBody Pauta pauta) {
		return service.nova(pauta);
	}
	
	@PostMapping("/{id}")
	public Pauta ingressar(@PathVariable int id, @RequestBody Associado associado) 
			throws PautaNotFoundException, CpfInvalidoException, AssociadoNaoEncontradoException {
		return this.service.ingressar(id, associado);
	}
	
	@GetMapping
	public List<Pauta> pautas() {
		return service.buscar();
	}
	
	@GetMapping("/{id}")
	public Pauta pauta(@PathVariable int id) throws PautaNotFoundException {
		return service.buscar(id);
	}
	
	@PostMapping(value = {"/iniciar/{id}", "/iniciar/{id}/{seconds}"})
	public Pauta iniciar(@PathVariable int id, @PathVariable(required = false) Integer seconds) throws PautaNotFoundException {
		return this.service.iniciar(id, seconds);
	}
	
	@MessageMapping("/iniciar")
	@SendTo("/topics/iniciar")
	public Pauta iniciarByMessage(PautaDTO pauta) throws PautaNotFoundException {
		return this.service.iniciar(pauta.getId(), pauta.getSeconds());
	}
	
	@GetMapping("/resumo/{id}")
	public ResumoVotacaoDTO computarVotos(@PathVariable int id) throws PautaNotFoundException {
		return this.service.getVotos(id);
	}
	
	@MessageMapping("/resumo")
	@SendTo("/topics/resumo")
	public ResumoVotacaoDTO computarVotosByMessage(int id) throws PautaNotFoundException {
		return this.service.getVotos(id);
	}

}
