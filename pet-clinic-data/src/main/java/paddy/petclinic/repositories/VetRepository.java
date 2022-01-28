package paddy.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import paddy.petclinic.model.Vet;

public interface VetRepository extends CrudRepository<Vet, Long> {
}
