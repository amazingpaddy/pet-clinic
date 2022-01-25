package paddy.petclinic.services.map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import paddy.petclinic.model.Owner;
import paddy.petclinic.model.Pet;
import paddy.petclinic.services.OwnerService;
import paddy.petclinic.services.PetService;
import paddy.petclinic.services.PetTypeService;

import java.util.Objects;
import java.util.Set;

@Service
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    public OwnerServiceMap(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Owner owner) {
        super.delete(owner);
    }

    @Override
    public Owner findById(Long id) {
        return super.findByID(id);
    }

    @Override
    public Owner save(Owner owner) {
        if (Objects.nonNull(owner)) {
            if (CollectionUtils.isNotEmpty(owner.getPets())) {
                owner.getPets().forEach(pet -> {
                    if (Objects.nonNull(pet.getPetType())) {
                        if (Objects.isNull(pet.getPetType().getId())) {
                            pet.setPetType(petTypeService.save(pet.getPetType()));
                        }
                    } else {
                        throw new RuntimeException("Pet Type is required");
                    }

                    if (Objects.isNull(pet.getId())) {
                        Pet savedPet = petService.save(pet);
                        pet.setId(savedPet.getId());
                    }
                });
            }
            return super.save(owner);
        } else {
            return null;
        }
    }

    @Override
    public Set<Owner> findByLastName(String lastName) {
        return null;
    }
}
