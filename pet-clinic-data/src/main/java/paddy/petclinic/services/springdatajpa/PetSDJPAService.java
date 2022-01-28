package paddy.petclinic.services.springdatajpa;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import paddy.petclinic.model.Pet;
import paddy.petclinic.repositories.PetRepository;
import paddy.petclinic.services.PetService;

import java.util.Set;

@Service
@Profile("springdatajpa")
public class PetSDJPAService implements PetService {
  private final PetRepository petRepository;

  public PetSDJPAService(PetRepository petRepository) {
    this.petRepository = petRepository;
  }

  @Override
  public Set<Pet> findAll() {
    return Sets.newHashSet(petRepository.findAll());
  }

  @Override
  public Pet findById(Long aLong) {
    return petRepository.findById(aLong).orElse(null);
  }

  @Override
  public Pet save(Pet object) {
    return petRepository.save(object);
  }

  @Override
  public void delete(Pet object) {
    petRepository.delete(object);
  }

  @Override
  public void deleteById(Long aLong) {
    petRepository.deleteById(aLong);
  }
}
