package paddy.petclinic.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
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
}
