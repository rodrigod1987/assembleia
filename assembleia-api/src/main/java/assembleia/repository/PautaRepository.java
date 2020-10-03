package assembleia.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import assembleia.domain.model.Pauta;

public interface PautaRepository extends CrudRepository<Pauta, Integer> {

	List<Pauta> findAll();
	List<Pauta> findByOrderByIdDesc();
	
}
