package assembleia.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import assembleia.domain.model.Voto;

public interface VotoRepository extends CrudRepository<Voto, Integer> {
	
	List<Voto> findAllByPautaId(int pautaId);
	
}
