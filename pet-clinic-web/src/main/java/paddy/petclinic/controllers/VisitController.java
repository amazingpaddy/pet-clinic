package paddy.petclinic.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import paddy.petclinic.model.Owner;
import paddy.petclinic.model.Pet;
import paddy.petclinic.model.Visit;
import paddy.petclinic.services.OwnerService;
import paddy.petclinic.services.PetService;

@Slf4j
@RequestMapping("/owners/{ownerId}/pets/{petId}")
@Controller
public class VisitController {
  private final PetService petService;
  private final OwnerService ownerService;

  public VisitController(PetService petService, OwnerService ownerService) {
    this.petService = petService;
    this.ownerService = ownerService;
  }

  @InitBinder
  public void setDisAllowedFields(WebDataBinder webDataBinder) {
    webDataBinder.setDisallowedFields("id");
  }

  @ModelAttribute("owner")
  public Owner findOwner(@PathVariable Long ownerId) {
    log.info("****** Calling Attribute method for owner ********");
    return ownerService.findById(ownerId);
  }

  @ModelAttribute("pet")
  public Pet findPet(@PathVariable Long petId) {
    log.info("******** Calling Attribute method for pet **********");
    return petService.findById(petId);
  }

  @ModelAttribute("visit")
  public Visit loadPetWithVisit(@ModelAttribute Owner owner, @ModelAttribute Pet pet) {
    Visit visit = new Visit();
    pet.getVisits().add(visit);
    visit.setPet(pet);
    return visit;
  }

  // Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is
  // called
  @GetMapping("/visits/new")
  public String initNewVisitForm() {
    return "pets/createOrUpdateVisitForm";
  }

  // Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is
  // called
  @PostMapping("/visits/new")
  public String processNewVisitForm(
      @ModelAttribute Owner owner,
      @PathVariable Long petId,
      @Validated Visit visit,
      BindingResult result) {
    if (result.hasErrors()) {
      return "pets/createOrUpdateVisitForm";
    } else {
      owner.addVisit(petId, visit);
      ownerService.save(owner);
      return "redirect:/owners/{ownerId}";
    }
  }
}
