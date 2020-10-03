package assembleia.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import assembleia.domain.dto.ResumoVotacaoDTO;
import assembleia.domain.enums.VotoEnum;
import assembleia.domain.model.Associado;
import assembleia.domain.model.Pauta;
import assembleia.domain.model.Voto;
import assembleia.exception.AssociadoNaoEncontradoException;
import assembleia.exception.CpfInvalidoException;
import assembleia.exception.PautaFinalizadaException;
import assembleia.exception.PautaNotFoundException;
import assembleia.repository.VotoRepository;

@Service
public class VotoService {
	
	@Autowired
	private VotoRepository repository;
	@Autowired
	private PautaService pautaService;
	@Autowired
	private AssociadoService associadoService;

	@Transactional
	public Voto votar(int pautaId, long cpf, VotoEnum resposta) 
			throws PautaNotFoundException, 
			PautaFinalizadaException, CpfInvalidoException, AssociadoNaoEncontradoException {
		
		Pauta pauta = this.pautaService.buscar(pautaId);
		
		if (pauta.isFinalizada()) {
			throw new PautaFinalizadaException();
		}
		
		Associado associado = this.associadoService.buscarPorCpf(cpf);
		
		Voto voto = new Voto();
		voto.setPauta(pauta);
		voto.setAssociado(associado);
		voto.responder(resposta);
		
		return this.repository.save(voto);
	}
	
	public ResumoVotacaoDTO computarVotos(int pautaId) {
		
		List<Voto> votos = this.repository.findAllByPautaId(pautaId);
		
		long sim = votos.stream().filter(item -> item.getResposta() == VotoEnum.Sim).count();
		long nao = votos.stream().filter(item -> item.getResposta() == VotoEnum.Nao).count();
		
		return new ResumoVotacaoDTO(sim, nao);
		
	}
	
	

}
