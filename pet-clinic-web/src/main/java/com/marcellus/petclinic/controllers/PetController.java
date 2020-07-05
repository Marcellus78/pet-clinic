package com.marcellus.petclinic.controllers;

import com.marcellus.petclinic.model.Owner;
import com.marcellus.petclinic.model.Pet;
import com.marcellus.petclinic.model.PetType;
import com.marcellus.petclinic.services.OwnerService;
import com.marcellus.petclinic.services.PetService;
import com.marcellus.petclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private final static String VIEWS_PETS_CREATE_OR_UPDATE_FORM =
            "pets/createOrUpdatePetForm";
    private final PetService petService;
    private final OwnerService ownerService;
    private final PetTypeService petTypeService;

    public PetController(PetService petService,
                         OwnerService ownerService,
                         PetTypeService petTypeService) {
        this.petService = petService;
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return this.petTypeService.findAll();
    }
    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId ) {
        return this.ownerService.findById(ownerId);
    }
    @InitBinder
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
        dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDate.parse(text));
            }
        });
    }

    @GetMapping("/pets/new")
    public String initCreationForm(Owner owner, Model model) {
        Pet pet = new Pet();
        owner.getPets().add(pet);
        pet.setOwner(owner);
        model.addAttribute("pet", pet);
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }
    @PostMapping("pets/new")
    public String processInitForm(Owner owner,
                                  @Valid Pet pet,
                                  BindingResult result,
                                  Model model) {

        if(StringUtils.hasLength(pet.getName()) && pet.isNew() &&
                owner.getPet(pet.getName(), true) != null) {
            result.rejectValue("name", "duplicate", "already exist");
        }
        pet.setOwner(owner);
        if(result.hasErrors()){
            pet.setOwner(owner);
            model.addAttribute("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }
    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable Long petId, Model model) {
        model.addAttribute("pet",petService.findById(petId));

        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }
    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(@Valid Pet pet,
                                    Owner owner,
                                    BindingResult result,
                                    Model model,
                                    @PathVariable Long petId){
        pet.setId(petId);
        pet.setOwner(owner);
        if(result.hasErrors()) {
//            pet.setOwner(owner);
            model.addAttribute("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            petService.save(pet);
//            owner.getPets().add(pet);
            return "redirect:/owners/" + owner.getId();
        }

    }
}
