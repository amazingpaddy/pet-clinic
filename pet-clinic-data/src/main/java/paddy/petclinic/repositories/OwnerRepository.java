package paddy.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import paddy.petclinic.model.Owner;

import java.util.Collection;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
  Collection<Owner> findOwnerByLastName(String lastName);

  Collection<Owner> findOwnerByLastNameLike(String lastName);
}
