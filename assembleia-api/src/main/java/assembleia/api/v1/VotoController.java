package assembleia.api.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import assembleia.domain.dto.VotoDTO;
import assembleia.exception.AssociadoNaoEncontradoException;
import assembleia.exception.CpfInvalidoException;
import assembleia.exception.PautaFinalizadaException;
import assembleia.exception.PautaNotFoundException;
import assembleia.service.VotoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/voto")
public class VotoController {
	
	@Autowired
	private VotoService service;
	
	@PostMapping
	public void votar(@RequestBody VotoDTO voto) 
			throws PautaNotFoundException, PautaFinalizadaException, 
			CpfInvalidoException, AssociadoNaoEncontradoException {
		this.service.votar(voto.getPautaId(), voto.getCpf(), voto.getResposta());
	}

}
