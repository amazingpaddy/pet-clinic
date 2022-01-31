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

import static org.hamcrest.Matchers.hasSize;
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
  void listOwners() throws Exception {
    when(ownerService.findAll()).thenReturn(owners);
    mockMvc
        .perform(MockMvcRequestBuilders.get("/owners"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("owner/index"))
        .andExpect(MockMvcResultMatchers.model().attribute("owners", hasSize(2)));
  }

  @Test
  void listOwnersByIndex() throws Exception {
    when(ownerService.findAll()).thenReturn(owners);
    mockMvc
        .perform(MockMvcRequestBuilders.get("/owners/index"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("owner/index"))
        .andExpect(MockMvcResultMatchers.model().attribute("owners", hasSize(2)));
  }

  @Test
  void findOwners() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/owners/find"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("notimplemented"));
    verifyNoInteractions(ownerService);
  }
}
