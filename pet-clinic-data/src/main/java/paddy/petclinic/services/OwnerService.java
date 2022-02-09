package paddy.petclinic.services;

import paddy.petclinic.model.Owner;

import java.util.Collection;

public interface OwnerService extends CrudService<Owner, Long> {
  Collection<Owner> findByLastName(String lastName);

  Collection<Owner> findOwnerByLastNameLike(String lastName);
}
