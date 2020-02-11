package com.marcellus.petclinic.services.springdatajpa;

import com.marcellus.petclinic.model.Owner;
import com.marcellus.petclinic.repositories.OwnerRepository;
import com.marcellus.petclinic.repositories.PetRepository;
import com.marcellus.petclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    private final String LAST_NAME = "Moniuszko";
    private final Long OWNER_ID = 1L;

    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService service;

    Owner returnOwner;

    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(OWNER_ID).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {
        //given
        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);

        //when
        Owner owner = service.findByLastName(LAST_NAME);

        //then
        assertEquals(OWNER_ID,owner.getId());
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {
        //given
        Set<Owner> returnedOwners = new HashSet<>();
        returnedOwners.add(Owner.builder().id(1L).build());
        returnedOwners.add(Owner.builder().id(2L).build());
        when(ownerRepository.findAll()).thenReturn(returnedOwners);

        //when
        Set<Owner> owners = service.findAll();

        //then
        assertNotNull(owners);
        assertEquals(2,owners.size());

    }

    @Test
    void findById() {
        //given
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));

        //when
        Owner owner = service.findById(OWNER_ID);

        //then
        assertNotNull(owner);
        assertEquals(OWNER_ID, owner.getId());
    }
    @Test
    void findByIdNotFound() {
        //given
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        Owner owner = service.findById(OWNER_ID);

        //then
        assertNull(owner);
    }
    @Test
    void save() {
        //given
        Owner ownerToSave = returnOwner;
        when(ownerRepository.save(any())).thenReturn(returnOwner);

        //when
        Owner savedOwner = service.save(ownerToSave);

        assertNotNull(savedOwner);
        assertEquals(OWNER_ID, savedOwner.getId());
        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        service.delete(returnOwner);

        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        service.deleteById(OWNER_ID);

        verify(ownerRepository, times(1)).deleteById(anyLong());
    }
}