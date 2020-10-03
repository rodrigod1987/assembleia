package assembleia.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import assembleia.domain.model.Associado;
import assembleia.service.AssociadoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/associado")
public class AssociadoController {
	
	@Autowired
	private AssociadoService service;
	
	@GetMapping("/{pautaId}")
	public List<Associado> buscar(@PathVariable int pautaId) {
		return this.service.buscarPorPauta(pautaId);
	}
	
	@PostMapping
	public Associado entrar(@RequestBody Associado associado) throws Exception {
		return this.service.entrar(associado);
	}
	

}
