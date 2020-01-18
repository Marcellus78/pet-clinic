package com.marcellus.petclinic.services;

import com.marcellus.petclinic.model.Pet;

import java.util.Set;

public interface PetService {

    Pet fingById(Long id);

    Pet save(Pet pet);

    Set<Pet> findAll();
}
