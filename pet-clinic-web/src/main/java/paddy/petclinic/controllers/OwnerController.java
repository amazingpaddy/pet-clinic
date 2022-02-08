package paddy.petclinic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import paddy.petclinic.services.OwnerService;

@Controller
@RequestMapping("/owners")
public class OwnerController {
  private final OwnerService ownerService;

  public OwnerController(OwnerService ownerService) {
    this.ownerService = ownerService;
  }

  @RequestMapping({"", "/", "/index", "/index.html"})
  public String listOwners(Model model) {
    model.addAttribute("owners", ownerService.findAll());
    return "owner/index";
  }

  @RequestMapping("/find")
  public String findOwners() {
    return "notimplemented";
  }

  @GetMapping("/{ownerId}")
  public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
    ModelAndView modelAndView =
        new ModelAndView(
            "owner/ownerDetails"); // owners/ownerdetails is a HTML file under resources
    modelAndView.addObject(ownerService.findById(ownerId));
    return modelAndView;
  }
}
