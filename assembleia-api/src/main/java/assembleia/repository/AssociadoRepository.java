package assembleia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import assembleia.domain.model.Associado;

public interface AssociadoRepository extends CrudRepository<Associado, Integer> {

	List<Associado> findByPautasId(int id);

	Optional<Associado> findByCpf(long cpf);
	
}
