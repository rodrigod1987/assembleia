package assembleia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import assembleia.domain.dto.CPFResponseDTO;
import assembleia.domain.enums.CpfStatus;
import assembleia.domain.model.Associado;
import assembleia.exception.AssociadoNaoEncontradoException;
import assembleia.exception.CpfInvalidoException;
import assembleia.repository.AssociadoRepository;

@Service
public class AssociadoService {

	@Autowired
	private AssociadoRepository repository;
	
	public Associado gravar(Associado associado) {
		return this.repository.save(associado);
	}

	public List<Associado> buscarPorPauta(int votacaoId) {
		return this.repository.findByPautasId(votacaoId);
	}

	public Associado entrar(Associado associado) throws CpfInvalidoException, AssociadoNaoEncontradoException {
		
//		if (!isCpfValido(associado.getCpf())) {
//			throw new CpfInvalidoException(associado.getCpf());
//		}
		
		Associado found = this.buscarPorCpf(associado.getCpf());
		
		if (found == null) {
			return this.repository.save(associado);
		}
		
		return found;
	}

	public Associado buscarPorCpf(long cpf) throws AssociadoNaoEncontradoException {
		return this.repository.findByCpf(cpf).orElse(null);
	}
	
	public boolean isCpfValido(long cpf) {
		
		CPFResponseDTO entity = null;
		
		//Se eu passar o cpf 123 tenho 404 de resposta, ou seja, cpf inválido pra minha regra de negócio
		//Creio que tenha um bug no app do cpf, pois meu cpf na primeira vez dá como inválido
		//na segunda funciona, e no meio do percurso acontece novamente.
		try {
			RestTemplate rest = new RestTemplate();
			ResponseEntity<CPFResponseDTO> response = rest.getForEntity("https://user-info.herokuapp.com/users/" + cpf, CPFResponseDTO.class);
			entity = response.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return entity.getStatus().equals(CpfStatus.ABLE_TO_VOTE);
	}
	
}
