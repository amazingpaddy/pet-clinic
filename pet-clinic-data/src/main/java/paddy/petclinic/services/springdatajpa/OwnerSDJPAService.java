package paddy.petclinic.services.springdatajpa;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import paddy.petclinic.model.Owner;
import paddy.petclinic.repositories.OwnerRepository;
import paddy.petclinic.services.OwnerService;

import java.util.Collection;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class OwnerSDJPAService implements OwnerService {
  private final OwnerRepository ownerRepository;

  public OwnerSDJPAService(OwnerRepository ownerRepository) {
    this.ownerRepository = ownerRepository;
  }

  @Override
  public Set<Owner> findAll() {
    return Sets.newHashSet(ownerRepository.findAll());
  }

  @Override
  public Owner findById(Long aLong) {
    return ownerRepository.findById(aLong).orElse(null);
  }

  @Override
  public Owner save(Owner object) {
    return ownerRepository.save(object);
  }

  @Override
  public void delete(Owner object) {
    ownerRepository.delete(object);
  }

  @Override
  public void deleteById(Long aLong) {
    ownerRepository.deleteById(aLong);
  }

  @Override
  public Collection<Owner> findByLastName(String lastName) {
    return ownerRepository.findOwnerByLastName(lastName);
  }

  @Override
  public Collection<Owner> findOwnerByLastNameLike(String lastName) {
    return ownerRepository.findOwnerByLastNameLike(lastName);
  }
}
