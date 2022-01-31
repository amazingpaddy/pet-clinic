package paddy.petclinic.services.springdatajpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import paddy.petclinic.model.Owner;
import paddy.petclinic.repositories.OwnerRepository;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJPAServiceTest {
  private final String lastName = "Smith";

  @Mock OwnerRepository ownerRepository;
  @InjectMocks OwnerSDJPAService ownerSDJPAService;

  private Owner returnOwner;

  @BeforeEach
  void setUp() {
    returnOwner = Owner.builder().id(1L).lastName(lastName).build();
  }

  @Test
  void findAll() {
    Set<Owner> owners = Set.of(createOwner(1L), createOwner(2L));
    when(ownerRepository.findAll()).thenReturn(owners);
    assertEquals(2, ownerSDJPAService.findAll().size());
  }

  @Test
  void findById() {
    when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));
    assertEquals(1L, ownerSDJPAService.findById(1L).getId());
  }

  @Test
  void save() {
    when(ownerRepository.save(any())).thenReturn(returnOwner);
    assertEquals(1L, ownerSDJPAService.save(createOwner(1L)).getId());
  }

  @Test
  void delete() {
    ownerSDJPAService.delete(returnOwner);
    verify(ownerRepository, times(1)).delete(any());
  }

  @Test
  void deleteById() {
    ownerSDJPAService.deleteById(1L);
    verify(ownerRepository, times(1)).deleteById(anyLong());
  }

  @Test
  void findByLastName() {
    when(ownerRepository.findOwnerByLastName(anyString())).thenReturn(returnOwner);
    assertEquals(lastName, ownerSDJPAService.findByLastName(lastName).getLastName());
  }

  private Owner createOwner(long id) {
    return Owner.builder().id(id).build();
  }
}
