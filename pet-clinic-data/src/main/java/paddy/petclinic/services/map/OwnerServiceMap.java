package paddy.petclinic.services.map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import paddy.petclinic.model.Owner;
import paddy.petclinic.model.Pet;
import paddy.petclinic.services.OwnerService;
import paddy.petclinic.services.PetService;
import paddy.petclinic.services.PetTypeService;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile({"default", "map"})
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
        owner
            .getPets()
            .forEach(
                pet -> {
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
  public Collection<Owner> findByLastName(String lastName) {
    if (StringUtils.isBlank(lastName)) {
      return super.findAll();
    }
    return super.findAll().stream()
        .filter(owner -> lastName.equalsIgnoreCase(owner.getLastName()))
        .collect(Collectors.toSet());
  }

  @Override
  public Collection<Owner> findOwnerByLastNameLike(String lastName) {
    return findByLastName(lastName);
  }
}
