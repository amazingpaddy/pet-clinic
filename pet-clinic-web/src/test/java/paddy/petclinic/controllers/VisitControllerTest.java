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
import paddy.petclinic.model.Pet;
import paddy.petclinic.model.PetType;
import paddy.petclinic.services.OwnerService;
import paddy.petclinic.services.PetService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
  public static final String VISIT_CREATE_OR_UPDATE_OWNER_FORM = "pets/createOrUpdateVisitForm";
  @Mock OwnerService ownerService;
  @Mock PetService petService;
  @InjectMocks VisitController visitController;
  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
  }

  @Test
  void initCreationForm() throws Exception {
    when(visitController.findOwner(anyLong())).thenReturn(Owner.builder().id(1L).build());
    when(visitController.findPet(anyLong()))
        .thenReturn(Pet.builder().id(1L).petType(new PetType(1L, "Dog")).build());
    mockMvc
        .perform(MockMvcRequestBuilders.get("/owners/1/pets/1/visits/new"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name(VISIT_CREATE_OR_UPDATE_OWNER_FORM))
        .andExpect(MockMvcResultMatchers.model().attributeExists("visit"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
        .andExpect(MockMvcResultMatchers.model().attributeExists("pet"));
  }
}
