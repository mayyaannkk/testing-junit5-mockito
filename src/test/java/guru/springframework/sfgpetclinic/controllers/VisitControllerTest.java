package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    PetService petService;

    @Mock
    Model model;

    @InjectMocks
    VisitController visitController;

    @Test
    void loadPetWithVisit() {
        //Given
        Pet pet = new Pet(1L);
        Visit visit = new Visit();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        given(petService.findById(1L)).willReturn(pet);

        //When
        Visit returnedVisit = visitController.loadPetWithVisit(1L, mock(Map.class));

        //Then
        then(petService).should().findById(anyLong());
        assertEquals((Long)1L, returnedVisit.getPet().getId());
    }
}