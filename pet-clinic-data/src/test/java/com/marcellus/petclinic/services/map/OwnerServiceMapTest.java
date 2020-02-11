package com.marcellus.petclinic.services.map;

import com.marcellus.petclinic.model.Owner;
import com.marcellus.petclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    OwnerService ownerService;

    final Long ownerId = 1L;
    final String ownerName = "Moniuszko";

    @BeforeEach
    void setUp() {
        ownerService = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());

        ownerService.save(Owner.builder().id(ownerId).lastName(ownerName).build());
    }

    @Test
    void findByLastName() {

        Owner owner = ownerService.findByLastName(ownerName);

        assertNotNull(owner);
        assertEquals(ownerName, owner.getLastName());

    }
    @Test
    void findByLastNameNotFound() {

        Owner owner = ownerService.findByLastName("foo");

        assertNull(owner);

    }

    @Test
    void findAll() {

        Set<Owner> owners = ownerService.findAll();
        assertEquals(1, owners.size());
    }

    @Test
    void findById() {

        Owner owner = ownerService.findById(ownerId);
        assertEquals(ownerId, owner.getId());
    }

    @Test
    void deleteById() {
        //given
        ownerService.deleteById(ownerId);


        //when
        Set<Owner> owners = ownerService.findAll();

        //then
        assertEquals(0,owners.size());
    }

    @Test
    void delete() {

        //given
        Owner owner = ownerService.findById(ownerId);

        //when
        ownerService.delete(owner);
        Set<Owner> owners = ownerService.findAll();

        //then
        assertEquals(0, owners.size());
    }

    @Test
    void save() {
        //given
        long id = 2L;
        String name = "Kowalski";
        Owner owner2 = Owner.builder().id(id).lastName(name).build();
        ownerService.save(owner2);

        //when
        Set<Owner> owners = ownerService.findAll();

        //then
        assertEquals(2,owners.size());
    }
    @Test
    void savedNoId(){
        //given
        Owner savedOwner = ownerService.save(Owner.builder().build());

        //when
        //then
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }
}