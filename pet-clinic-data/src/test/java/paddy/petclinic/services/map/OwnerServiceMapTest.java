package paddy.petclinic.services.map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import paddy.petclinic.model.Owner;
import paddy.petclinic.services.OwnerService;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OwnerServiceMapTest {
  OwnerService ownerService;
  long id = 1L;
  String lastName = "Vijendran";

  @BeforeEach
  void setUp() {
    ownerService = new OwnerServiceMap(new PetTypeMapService(), new PetServiceMap());
    ownerService.save(Owner.builder().id(id).lastName(lastName).build());
  }

  @Test
  void findAll() {
    Set<Owner> owners = ownerService.findAll();
    assertEquals(1, owners.size());
  }

  @Test
  void findById() {
    assertEquals(id, ownerService.findById(id).getId());
  }

  @Test
  void saveExistingId() {
    long id = 2L;
    Owner newOwner = Owner.builder().id(id).build();
    Owner savedOwner = ownerService.save(newOwner);
    assertEquals(id, savedOwner.getId());
  }

  @Test
  void saveNoId() {
    Owner savedOwner = ownerService.save(Owner.builder().build());
    assertNotNull(savedOwner);
    assertNotNull(savedOwner.getId());
  }

  @Test
  void findByLastName() {
    assertEquals(lastName, ownerService.findByLastName(lastName).getLastName());
  }

  @Test
  void deleteById() {
    ownerService.deleteById(id);
    assertEquals(0, ownerService.findAll().size());
  }

  @Test
  void delete() {
    ownerService.delete(ownerService.findById(id));
    assertEquals(0, ownerService.findAll().size());
  }
}
