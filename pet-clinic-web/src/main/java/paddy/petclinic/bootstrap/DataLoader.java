package paddy.petclinic.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import paddy.petclinic.model.Owner;
import paddy.petclinic.model.Vet;
import paddy.petclinic.services.OwnerService;
import paddy.petclinic.services.VetService;
import paddy.petclinic.services.map.OwnerServiceMap;
import paddy.petclinic.services.map.VetServiceMap;

@Component
public class DataLoader implements CommandLineRunner {
    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader() {
        ownerService = new OwnerServiceMap();
        vetService = new VetServiceMap();
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Loadiing Owners...");
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
