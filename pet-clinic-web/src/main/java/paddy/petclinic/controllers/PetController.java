package paddy.petclinic.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import paddy.petclinic.model.Owner;
import paddy.petclinic.model.PetType;
import paddy.petclinic.services.OwnerService;
import paddy.petclinic.services.PetService;
import paddy.petclinic.services.PetTypeService;

import java.util.Collection;

@Slf4j
@RequestMapping("/owners/{ownerId}")
@Controller
public class PetController {
  private final OwnerService ownerService;
  private final PetService petService;
  private final PetTypeService petTypeService;

  public PetController(
      OwnerService ownerService, PetService petService, PetTypeService petTypeService) {
    this.ownerService = ownerService;
    this.petService = petService;
    this.petTypeService = petTypeService;
  }

  @ModelAttribute("types")
  public Collection<PetType> populatePetTypes() {
    return petTypeService.findAll();
  }

  public Owner findOwner(@PathVariable("ownerId") Long ownerId) {
    return ownerService.findById(ownerId);
  }

  @InitBinder("owner")
  public void initOwnerBinder(WebDataBinder webDataBinder) {
    webDataBinder.setDisallowedFields("id");
  }
}
