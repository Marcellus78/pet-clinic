package com.marcellus.petclinic.services;

import com.marcellus.petclinic.model.Vet;

import java.util.Set;

public interface VetService {

    Vet fingById(Long id);

    Vet save(Vet vet);

    Set<Vet> findAll();
}
