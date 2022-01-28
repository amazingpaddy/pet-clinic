package paddy.petclinic.services.springdatajpa;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import paddy.petclinic.model.PetType;
import paddy.petclinic.repositories.PetTypeRepository;
import paddy.petclinic.services.PetTypeService;

import java.util.Set;

@Service
@Profile("springdatajpa")
public class PetTypeSDJPAService implements PetTypeService {
  private final PetTypeRepository petTypeRepository;

  public PetTypeSDJPAService(PetTypeRepository petTypeRepository) {
    this.petTypeRepository = petTypeRepository;
  }

  @Override
  public Set<PetType> findAll() {
    return Sets.newHashSet(petTypeRepository.findAll());
  }

  @Override
  public PetType findById(Long aLong) {
    return petTypeRepository.findById(aLong).orElse(null);
  }

  @Override
  public PetType save(PetType object) {
    return petTypeRepository.save(object);
  }

  @Override
  public void delete(PetType object) {
    petTypeRepository.delete(object);
  }

  @Override
  public void deleteById(Long aLong) {
    petTypeRepository.deleteById(aLong);
  }
}
