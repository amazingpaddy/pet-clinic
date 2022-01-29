package paddy.petclinic.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import paddy.petclinic.model.Owner;
import paddy.petclinic.model.Pet;
import paddy.petclinic.model.PetType;
import paddy.petclinic.model.Specialty;
import paddy.petclinic.model.Vet;
import paddy.petclinic.model.Visit;
import paddy.petclinic.services.OwnerService;
import paddy.petclinic.services.PetTypeService;
import paddy.petclinic.services.SpecialtyService;
import paddy.petclinic.services.VetService;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {
  private final OwnerService ownerService;
  private final VetService vetService;
  private final PetTypeService petTypeService;
  private final SpecialtyService specialtyService;

  public DataLoader(
      OwnerService ownerService,
      VetService vetService,
      PetTypeService petTypeService,
      SpecialtyService specialtyService) {
    this.ownerService = ownerService;
    this.vetService = vetService;
    this.petTypeService = petTypeService;
    this.specialtyService = specialtyService;
  }

  @Override
  public void run(String... args) throws Exception {
    final int count = petTypeService.findAll().size();
    if (count == 0) {
      loadData();
    }
  }

  private void loadData() {
    System.out.println("Loading DogTypes...");
    PetType dogType = new PetType();
    dogType.setName("Dog");
    PetType savedDogType = petTypeService.save(dogType);
    System.out.println("Loading Cat Types...");
    PetType catType = new PetType();
    catType.setName("Cat");
    PetType savedCatType = petTypeService.save(catType);

    Specialty radiology = new Specialty();
    radiology.setDescription("radiology");
    Specialty surgery = new Specialty();
    surgery.setDescription("surgery");
    Specialty dentistry = new Specialty();
    dentistry.setDescription("dentistry");
    specialtyService.save(radiology);
    specialtyService.save(surgery);
    specialtyService.save(dentistry);

    System.out.println("Loading Owners...");
    Owner paddy = new Owner();
    paddy.setId(1L);
    paddy.setFirstName("Padmanabhan");
    paddy.setLastName("Vijendran");
    paddy.setAddress("Hurontario Street");
    paddy.setCity("Toronto");
    paddy.setTelephone("123456");

    Pet paddyPet = new Pet();
    paddyPet.setName("jhonny");
    paddyPet.setPetType(savedDogType);
    paddyPet.setOwner(paddy);
    paddyPet.setBirthDate(LocalDate.of(2019, 10, 5));
    paddy.getPets().add(paddyPet);
    ownerService.save(paddy);

    Owner uma = new Owner();
    uma.setId(2L);
    uma.setFirstName("Uma");
    uma.setLastName("Maheswari");
    uma.setAddress("Serangoon Street");
    uma.setCity("Singapore");
    uma.setTelephone("123456");

    Pet umaPet = new Pet();
    umaPet.setName("rowdy");
    umaPet.setPetType(savedDogType);
    umaPet.setOwner(uma);
    umaPet.setBirthDate(LocalDate.of(2018, 6, 3));
    uma.getPets().add(umaPet);

    // Adding visit before saving the uma (Owner) since owner will cascade downwards (owner -> pet
    // -> visits)
    Visit rowdyVisit = new Visit();
    rowdyVisit.setDate(LocalDate.now());
    rowdyVisit.setDescription("Regular Checkup");
    rowdyVisit.setPet(umaPet);

    umaPet
        .getVisits()
        .add(rowdyVisit); // we need to update the umaPet with visits in order to cascade to visits

    ownerService.save(uma);

    System.out.println("Loading Vets ....");
    Vet vet1 = new Vet();
    vet1.setId(1L);
    vet1.setFirstName("Rajini");
    vet1.setLastName("Kanth");
    vet1.getSpecialties().add(radiology);
    vet1.getSpecialties().add(dentistry);
    Vet vet2 = new Vet();
    vet2.setId(2L);
    vet2.setFirstName("Kamal");
    vet2.setLastName("Hassan");
    vet2.getSpecialties().add(surgery);
    vetService.save(vet1);
    vetService.save(vet2);
  }
}
