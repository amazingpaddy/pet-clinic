package paddy.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import paddy.petclinic.model.Owner;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
}
