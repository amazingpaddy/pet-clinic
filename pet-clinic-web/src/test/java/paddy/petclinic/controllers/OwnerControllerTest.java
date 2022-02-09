package paddy.petclinic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import paddy.petclinic.model.Owner;
import paddy.petclinic.services.OwnerService;

import java.util.Set;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
  @Mock OwnerService ownerService;
  @InjectMocks OwnerController ownerController;
  Set<Owner> owners;
  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    owners = Set.of(Owner.builder().id(1L).build(), Owner.builder().id(2L).build());
    mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
  }

  @Test
  void findOwners() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/owners/find"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("owner/findOwners"));
    verifyNoInteractions(ownerService);
  }

  @Test
  void displayOwner() throws Exception {
    when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());
    mockMvc
        .perform(MockMvcRequestBuilders.get("/owners/123"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("owner/ownerDetails"))
        .andExpect(MockMvcResultMatchers.model().attribute("owner", hasProperty("id", is(1L))));
  }

  @Test
  void findByLastNameManyTest() throws Exception {
    when(ownerService.findOwnerByLastNameLike(anyString()))
        .thenReturn(
            Set.of(
                Owner.builder().id(1L).lastName("test").build(),
                Owner.builder().id(2L).lastName("test").build()));

    mockMvc
        .perform(MockMvcRequestBuilders.get("/owners/"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("owner/ownersList"))
        .andExpect(MockMvcResultMatchers.model().attribute("selections", hasSize(2)));
  }

  @Test
  void findByLastNameOneTest() throws Exception {
    when(ownerService.findOwnerByLastNameLike(anyString()))
        .thenReturn(Set.of(Owner.builder().id(1L).build()));

    mockMvc
        .perform(MockMvcRequestBuilders.get("/owners"))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/1"));
  }
}
