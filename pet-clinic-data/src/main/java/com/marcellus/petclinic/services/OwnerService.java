package com.marcellus.petclinic.services;

import com.marcellus.petclinic.model.Owner;


public interface OwnerService extends CrudService<Owner, Long>{

    Owner findByLastName(String lastName);

}
