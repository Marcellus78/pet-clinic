package com.marcellus.petclinic.bootstrap;

import com.marcellus.petclinic.model.*;
import com.marcellus.petclinic.services.OwnerService;
import com.marcellus.petclinic.services.PetTypeService;
import com.marcellus.petclinic.services.SpecialityService;
import com.marcellus.petclinic.services.VetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;

    @Autowired
    public DataLoader(OwnerService ownerService, VetService vetService,
                      PetTypeService petTypeService, SpecialityService specialityServiceService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityServiceService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();
        if(count == 0 ) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        Speciality savedRadiology = specialityService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");
        Speciality savedSurgery = specialityService.save(surgery);

        Speciality dentistry = new Speciality();
        dentistry.setDescription("Dentistry");
        Speciality savedDentistry = specialityService.save(dentistry);

        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        owner1.setAddress("123 Brickeler");
        owner1.setCity("Miami");
        owner1.setTelephone("12345678");
        Pet mikePet = new Pet();
        mikePet.setPetType(savedDogPetType);
        mikePet.setOwner(owner1);
        mikePet.setBirthDate(LocalDate.now());
        mikePet.setName("Rosco");
        owner1.getPets().add(mikePet);
        ownerService.save(owner1);


        Owner owner2 = new Owner();
        owner2.setFirstName("Maciek");
        owner2.setLastName("Moniuszko");
        owner2.setAddress("Lubelska 31/23");
        owner2.setCity("Puławy");
        owner2.setTelephone("698151230");
        Pet marcelPet = new Pet();
        marcelPet.setPetType(savedCatPetType);
        marcelPet.setOwner(owner2);
        marcelPet.setBirthDate(LocalDate.now());
        marcelPet.setName("Hrun");
        owner2.getPets().add(marcelPet);
        ownerService.save(owner2);

        System.out.println("Loaded Owners... ");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("West");
        vet1.getSpecialities().add(savedRadiology);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("John");
        vet2.setLastName("Doe");
        vet2.getSpecialities().add(savedSurgery);

        vetService.save(vet2);

        System.out.println("Loaded Vets...");
    }
}
