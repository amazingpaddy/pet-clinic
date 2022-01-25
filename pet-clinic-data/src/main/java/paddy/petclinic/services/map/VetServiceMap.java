package paddy.petclinic.services.map;

import org.springframework.stereotype.Service;
import paddy.petclinic.model.Specialty;
import paddy.petclinic.model.Vet;
import paddy.petclinic.services.SpecialtyService;
import paddy.petclinic.services.VetService;

import java.util.Objects;
import java.util.Set;

@Service
public class VetServiceMap extends AbstractMapService<Vet, Long> implements VetService {
    private final SpecialtyService specialtyService;

    public VetServiceMap(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Vet object) {
        super.delete(object);
    }

    @Override
    public Vet findById(Long id) {
        return super.findByID(id);
    }

    @Override
    public Vet save(Vet vet) {
        vet.getSpecialties()
                .forEach(specialty -> {
                    if (Objects.isNull(specialty.getId())) {
                        Specialty savedSpecialty = specialtyService.save(specialty);
                        specialty.setId(savedSpecialty.getId());
                    }
                });
        return super.save(vet);
    }
}
