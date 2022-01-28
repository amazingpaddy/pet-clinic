package paddy.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import paddy.petclinic.model.Specialty;

public interface SpecialityRepository extends CrudRepository<Specialty, Long> {
}
