package paddy.petclinic.services.springdatajpa;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import paddy.petclinic.model.Specialty;
import paddy.petclinic.repositories.SpecialityRepository;
import paddy.petclinic.services.SpecialtyService;

import java.util.Set;

@Service
@Profile("springdatajpa")
public class SpecialitySDJPAService implements SpecialtyService {
  private final SpecialityRepository specialityRepository;

  public SpecialitySDJPAService(SpecialityRepository specialityRepository) {
    this.specialityRepository = specialityRepository;
  }

  @Override
  public Set<Specialty> findAll() {
    return Sets.newHashSet(specialityRepository.findAll());
  }

  @Override
  public Specialty findById(Long aLong) {
    return specialityRepository.findById(aLong).orElse(null);
  }

  @Override
  public Specialty save(Specialty object) {
    return specialityRepository.save(object);
  }

  @Override
  public void delete(Specialty object) {
    specialityRepository.delete(object);
  }

  @Override
  public void deleteById(Long aLong) {
    specialityRepository.deleteById(aLong);
  }
}
