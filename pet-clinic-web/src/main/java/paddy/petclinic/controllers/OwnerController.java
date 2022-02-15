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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import paddy.petclinic.model.Owner;
import paddy.petclinic.services.OwnerService;

import java.util.Collection;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/owners")
public class OwnerController {

  public static final String OWNER_CREATE_OR_UPDATE_OWNER_FORM = "owner/createOrUpdateOwnerForm";
  private final OwnerService ownerService;

  public OwnerController(OwnerService ownerService) {
    this.ownerService = ownerService;
  }

  @InitBinder
  public void setDisAllowedFields(WebDataBinder webDataBinder) {
    webDataBinder.setDisallowedFields("id");
  }

  @RequestMapping("/find")
  public String findOwners(Model model) {
    model.addAttribute("owner", Owner.builder().build());
    return "owner/findOwners";
  }

  @GetMapping
  public String processFindForm(Owner owner, BindingResult result, Model model) {
    log.info("Owner inside - process forum - {}", owner.getId());
    if (Objects.isNull(owner.getLastName())) {
      owner.setLastName(StringUtils.EMPTY);
    }

    Collection<Owner> owners =
        ownerService.findOwnerByLastNameLike("%" + owner.getLastName() + "%");
    log.info("--->  Owner size - {}", owners.size());
    if (owners.isEmpty()) {
      result.rejectValue("lastName", "notFound", "not Found");
      return "owner/findOwners";
    } else if (owners.size() == 1) {
      owner = owners.iterator().next();
      return "redirect:/owners/" + owner.getId();
    } else {
      model.addAttribute("selections", owners);
      return "owner/ownersList";
    }
  }

  @GetMapping("/{ownerId}")
  public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
    ModelAndView modelAndView =
        new ModelAndView(
            "owner/ownerDetails"); // owners/ownerDetails is an HTML file under resources
    modelAndView.addObject(ownerService.findById(ownerId));
    return modelAndView;
  }

  /**
   * This method is getting called when we hit create button - mapped to /owners/new and returns the
   * form to add the new owner details.
   *
   * @param model - We are setting new Owner as attribute so that we can add all attributes
   * @return - Forum to add attributes of the new owner like firstName, lastName etc.
   */
  @GetMapping("/new")
  public String initCreationForm(Model model) {
    model.addAttribute("owner", Owner.builder().build());
    return OWNER_CREATE_OR_UPDATE_OWNER_FORM;
  }

  /**
   * This method calls once the user submitted the new Owner forum as we need to save the new Owner
   * to our DB
   *
   * @param owner - New owner with added attributes.
   * @param result - Its checks for validations if any.
   * @return - we save the new owner and redirect to the new Owner page.
   */
  @PostMapping("/new")
  public String processCreationForm(@Validated Owner owner, BindingResult result) {
    if (result.hasErrors()) {
      return OWNER_CREATE_OR_UPDATE_OWNER_FORM;
    }
    Owner savedOwner = ownerService.save(owner);
    log.info("New owner created with the id - {}", savedOwner.getId());
    return "redirect:/owners/" + savedOwner.getId();
  }

  @GetMapping("/{ownerId}/edit")
  public String initUpdateOwnerForm(@PathVariable Long ownerId, Model model) {
    model.addAttribute("owner", ownerService.findById(ownerId));
    return OWNER_CREATE_OR_UPDATE_OWNER_FORM;
  }

  @PostMapping("/{ownerId}/edit")
  public String processUpdateForm(
      @Validated Owner owner, BindingResult result, @PathVariable Long ownerId) {
    if (result.hasErrors()) {
      return OWNER_CREATE_OR_UPDATE_OWNER_FORM;
    }
    log.info("OwnerId returned by the forum - {}", owner.getId());
    log.info("Path Variable owner Id - {}", ownerId);
    owner.setId(ownerId);
    Owner savedOwner = ownerService.save(owner);
    return "redirect:/owners/" + savedOwner.getId();
  }
}
