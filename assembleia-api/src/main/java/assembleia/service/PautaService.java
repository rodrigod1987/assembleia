package assembleia.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import assembleia.domain.dto.ResumoVotacaoDTO;
import assembleia.domain.enums.Situacao;
import assembleia.domain.model.Associado;
import assembleia.domain.model.Pauta;
import assembleia.exception.AssociadoNaoEncontradoException;
import assembleia.exception.CpfInvalidoException;
import assembleia.exception.PautaNotFoundException;
import assembleia.repository.PautaRepository;

@Service
public class PautaService {

	@Autowired
	private PautaRepository repository;
	@Autowired
	private AssociadoService associadoService;
	@Autowired
	private VotoService votoService;
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Transactional
	public Pauta nova(Pauta pauta) {
		pauta.setSituacao(Situacao.Criada);
		return repository.save(pauta);
	}

	@Transactional
	public Pauta ingressar(int id, Associado associado) 
			throws PautaNotFoundException, CpfInvalidoException, AssociadoNaoEncontradoException {
		Pauta pauta = this.buscar(id);
		associado = this.associadoService.entrar(associado);
		pauta.addAssociado(associado);
		this.repository.save(pauta);
		return pauta;
	}

	public List<Pauta> buscar() {
		return repository.findByOrderByIdDesc();
	}

	public Pauta buscar(int id) throws PautaNotFoundException {
		Pauta pauta = repository.findById(id).orElse(null);
		if (pauta == null) {
			throw new PautaNotFoundException(id);
		}
		return pauta;
	}

	@Transactional
	public Pauta iniciar(int id, Integer seconds) throws PautaNotFoundException {

		try {
			Pauta pauta = this.buscar(id);
			
			if (seconds != null) {
				pauta.setTempoExecucao(seconds);
			}
			
			pauta.iniciar();
			final Pauta pautaIniciada = this.repository.save(pauta);
			
			runAsync(pautaIniciada.getId());
			
			return pautaIniciada;
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void runAsync(int id) throws InterruptedException, ExecutionException {

		CompletableFuture.runAsync(() -> {
			
			try {
				Pauta pauta = this.buscar(id);

				int timeToRun = pauta.getTempoExecucao();
				int elapsedTime = 0;
				while (elapsedTime < timeToRun) {
					try {
						Thread.sleep(1000);
						elapsedTime++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				this.finalizar(pauta);
			} catch (Exception e) {
				e.printStackTrace();
			}

		});

	}

	private void finalizar(Pauta pauta) throws PautaNotFoundException {
		pauta.finalizar();
		pauta = this.repository.save(pauta);
		
		this.sendMessage(pauta);
	}

	private void sendMessage(Pauta pauta) throws PautaNotFoundException {
		ResumoVotacaoDTO votos = this.computarVotos(pauta);
		this.simpMessagingTemplate.convertAndSend("/topics/resumo", votos);
	}
	
	private ResumoVotacaoDTO computarVotos(Pauta pauta) throws PautaNotFoundException {
		return this.votoService.computarVotos(pauta.getId());
	}
	

	public ResumoVotacaoDTO getVotos(int id) throws PautaNotFoundException {
		Pauta pauta = this.buscar(id);
		return computarVotos(pauta);
	}

}
