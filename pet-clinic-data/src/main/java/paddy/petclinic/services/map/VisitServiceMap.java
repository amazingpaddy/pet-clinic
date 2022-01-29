package paddy.petclinic.services.map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import paddy.petclinic.model.Visit;
import paddy.petclinic.services.VisitService;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class VisitServiceMap extends AbstractMapService<Visit, Long> implements VisitService {

  @Override
  public Set<Visit> findAll() {
    return super.findAll();
  }

  @Override
  public void deleteById(Long id) {
    super.deleteById(id);
  }

  @Override
  public void delete(Visit object) {
    super.delete(object);
  }

  @Override
  public Visit save(Visit visit) {
    if (ObjectUtils.anyNull(
        visit.getPet(),
        visit.getPet().getOwner(),
        visit.getPet().getId(),
        visit.getPet().getOwner().getId())) {
      throw new RuntimeException("Not a valid visit");
    }
    return super.save(visit);
  }

  @Override
  public Visit findById(Long aLong) {
    return super.findByID(aLong);
  }
}
