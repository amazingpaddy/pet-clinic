package paddy.petclinic.services.springdatajpa;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import paddy.petclinic.model.Visit;
import paddy.petclinic.repositories.VisitRepository;
import paddy.petclinic.services.VisitService;

import java.util.Set;

@Service
@Profile("springdatajpa")
public class VisitSDJPAService implements VisitService {
  private final VisitRepository visitRepository;

  public VisitSDJPAService(VisitRepository visitRepository) {
    this.visitRepository = visitRepository;
  }

  @Override
  public Set<Visit> findAll() {
    return Sets.newHashSet(visitRepository.findAll());
  }

  @Override
  public Visit findById(Long aLong) {
    return visitRepository.findById(aLong).orElse(null);
  }

  @Override
  public Visit save(Visit object) {
    return visitRepository.save(object);
  }

  @Override
  public void delete(Visit object) {
    visitRepository.delete(object);
  }

  @Override
  public void deleteById(Long aLong) {
    visitRepository.deleteById(aLong);
  }
}
