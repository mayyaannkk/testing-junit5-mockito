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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Spy
    PetService petService;

    @InjectMocks
    VisitController visitController;

    @Test
    void loadPetWithVisit() {
        //Given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(1L);
        Pet pet3 = new Pet(3L);

        petService.save(pet);
        petService.save(pet3);

        doReturn(pet).when(petService).findById(1L);

        //When
        Visit returnedVisit = visitController.loadPetWithVisit(1L, model);

        //Then
        assertEquals((Long)1L, returnedVisit.getPet().getId());
        assertNotNull(returnedVisit);
        assertNotNull(returnedVisit.getPet());
        then(petService).should(times(1)).findById(anyLong());
    }
}