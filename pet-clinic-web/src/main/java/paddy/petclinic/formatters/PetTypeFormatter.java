package paddy.petclinic.formatters;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Controller;
import paddy.petclinic.model.PetType;
import paddy.petclinic.services.PetTypeService;

import java.text.ParseException;
import java.util.Locale;

@SuppressWarnings("all")
@Controller
public class PetTypeFormatter implements Formatter<PetType> {
  private final PetTypeService petTypeService;

  public PetTypeFormatter(PetTypeService petTypeService) {
    this.petTypeService = petTypeService;
  }

  @Override
  public PetType parse(String name, Locale locale) throws ParseException {
    return this.petTypeService.findAll().stream()
        .filter(pT -> pT.getName().equals(name))
        .findFirst()
        .orElseThrow(() -> new ParseException("type not found: " + name, 0));
  }

  @Override
  public String print(PetType petType, Locale locale) {
    return petType.getName();
  }
}
