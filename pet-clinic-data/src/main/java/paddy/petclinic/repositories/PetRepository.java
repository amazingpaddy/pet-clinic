package paddy.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import paddy.petclinic.model.Pet;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
