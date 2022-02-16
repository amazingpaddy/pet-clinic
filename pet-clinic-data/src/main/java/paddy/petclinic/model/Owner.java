package paddy.petclinic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "owners")
public class Owner extends Person {
  @Column(name = "address")
  private String address;

  @Column(name = "city")
  private String city;

  @Column(name = "telephone")
  private String telephone;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
  private Set<Pet> pets = new HashSet<>();

  @Builder
  public Owner(
      Long id,
      String firstName,
      String lastName,
      String address,
      String city,
      String telephone,
      Set<Pet> pets) {
    super(id, firstName, lastName);
    this.address = address;
    this.city = city;
    this.telephone = telephone;
    if (pets != null) {
      this.pets = pets;
    }
  }

  public Pet getPet(String name) {
    return getPet(name, false);
  }

  public Pet getPet(String name, boolean ignoreNew) {
    for (Pet pet : pets) {
      if (!ignoreNew && !isNew()) {
        if (pet.getName().equalsIgnoreCase(name)) {
          return pet;
        }
      }
    }
    return null;
  }
  /**
   * Return the Pet with the given id, or null if none found for this Owner.
   *
   * @return a pet if pet id is already in use
   */
  public Pet getPet(Long id) {
    for (Pet pet : getPets()) {
      if (!pet.isNew()) {
        Long compId = pet.getId();
        if (compId.equals(id)) {
          return pet;
        }
      }
    }
    return null;
  }

  public void addVisit(Long petId, Visit visit) {
    Pet pet = getPet(petId);
    pet.getVisits().add(visit);
  }
}
