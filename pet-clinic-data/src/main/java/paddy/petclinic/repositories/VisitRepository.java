package paddy.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import paddy.petclinic.model.Visit;

public interface VisitRepository extends CrudRepository<Visit, Long> {
}
