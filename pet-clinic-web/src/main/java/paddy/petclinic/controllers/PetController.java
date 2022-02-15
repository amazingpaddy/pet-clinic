package paddy.petclinic.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import paddy.petclinic.model.PetType;
import paddy.petclinic.services.OwnerService;
import paddy.petclinic.services.PetService;
import paddy.petclinic.services.PetTypeService;

import java.util.Collection;

@Slf4j
@RequestMapping("/owners/{ownerId}")
@Controller
public class PetController {
  public static final String PETS_CREATE_OR_UPDATE_PET_FORM = "pets/createOrUpdatePetForm";
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

  @ModelAttribute("owner")
  public Owner findOwner(@PathVariable("ownerId") Long ownerId) {
    return ownerService.findById(ownerId);
  }

  @InitBinder("owner")
  public void initOwnerBinder(WebDataBinder webDataBinder) {
    webDataBinder.setDisallowedFields("id");
  }

  @GetMapping("/pets/new")
  public String initCreationForm(Owner owner, Model model) {
    Pet pet = new Pet();
    owner.getPets().add(pet);
    model.addAttribute("pet", pet);
    return PETS_CREATE_OR_UPDATE_PET_FORM;
  }

  @PostMapping("/pets/new")
  public String processCreationForm(
      Owner owner, @Validated Pet pet, BindingResult result, Model model) {
    if (StringUtils.isNotBlank(pet.getName())
        && pet.isNew()
        && owner.getPet(pet.getName(), true) != null) {
      result.rejectValue("name", "duplicate", "already exists");
    }
    owner.getPets().add(pet);
    if (result.hasErrors()) {
      model.addAttribute("pet", pet);
      return PETS_CREATE_OR_UPDATE_PET_FORM;
    } else {
      pet.setOwner(owner);
      ownerService.save(owner); // It will save the pet as well.
      log.info(
          "New pet with name - {} of pet type - {} is added",
          pet.getName(),
          pet.getPetType().getName());
      return "redirect:/owners/" + owner.getId();
    }
  }

  @GetMapping("/pets/{petId}/edit")
  public String initUpdateForm(@PathVariable Long petId, Model model) {
    Pet pet = petService.findById(petId);
    model.addAttribute("pet", pet);
    return PETS_CREATE_OR_UPDATE_PET_FORM;
  }

  @PostMapping("/pets/{petId}/edit")
  public String processUpdateForm(
      @Validated Pet pet, BindingResult result, Owner owner, Model model) {
    if (result.hasErrors()) {
      pet.setOwner(owner);
      model.addAttribute("pet", pet);
      return PETS_CREATE_OR_UPDATE_PET_FORM;
    } else {
      owner.getPets().add(pet);
      return "redirect:/owners/" + owner.getId();
    }
  }
}
