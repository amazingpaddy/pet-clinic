package paddy.petclinic.services.springdatajpa;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import paddy.petclinic.model.Vet;
import paddy.petclinic.repositories.VetRepository;
import paddy.petclinic.services.VetService;

import java.util.Set;

@Service
@Profile("springdatajpa")
public class VetSDJPAService implements VetService {
  private final VetRepository vetRepository;

  public VetSDJPAService(VetRepository vetRepository) {
    this.vetRepository = vetRepository;
  }

  @Override
  public Set<Vet> findAll() {
    return Sets.newHashSet(vetRepository.findAll());
  }

  @Override
  public Vet findById(Long aLong) {
    return vetRepository.findById(aLong).orElse(null);
  }

  @Override
  public Vet save(Vet object) {
    return vetRepository.save(object);
  }

  @Override
  public void delete(Vet object) {
    vetRepository.delete(object);
  }

  @Override
  public void deleteById(Long aLong) {
    vetRepository.deleteById(aLong);
  }
}
