package com.marcellus.petclinic.services;

import com.marcellus.petclinic.model.Owner;

import java.util.List;


public interface OwnerService extends CrudService<Owner, Long>{

    Owner findByLastName(String lastName);
    List<Owner> findAllByLastNameLike(String lastName);
}
