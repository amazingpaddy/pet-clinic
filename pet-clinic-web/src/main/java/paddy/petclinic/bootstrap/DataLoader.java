package paddy.petclinic.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import paddy.petclinic.model.Owner;
import paddy.petclinic.model.PetType;
import paddy.petclinic.model.Vet;
import paddy.petclinic.services.OwnerService;
import paddy.petclinic.services.PetTypeService;
import paddy.petclinic.services.VetService;

@Component
public class DataLoader implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Loading DogTypes...");
        PetType dogType = new PetType();
        dogType.setName("Dog");
        PetType savedDogType = petTypeService.save(dogType);
        System.out.println("Loading Cat Types...");
        PetType catType = new PetType();
        catType.setName("Cat");
        PetType savedCatType = petTypeService.save(catType);

        System.out.println("Loading Owners...");
        Owner paddy = new Owner();
        paddy.setId(1L);
        paddy.setFirstName("Padmanabhan");
        paddy.setLastName("Vijendran");
        ownerService.save(paddy);
        Owner uma = new Owner();
        uma.setId(2L);
        uma.setFirstName("Uma");
        uma.setLastName("Mahe");
        ownerService.save(uma);
        System.out.println("Loading Vets ....");
        Vet vet1 = new Vet();
        vet1.setId(1L);
        vet1.setFirstName("Rajini");
        vet1.setLastName("Kanth");
        Vet vet2 = new Vet();
        vet2.setId(2L);
        vet2.setFirstName("Kamal");
        vet2.setLastName("Hassan");
        vetService.save(vet1);
        vetService.save(vet2);
    }
}
