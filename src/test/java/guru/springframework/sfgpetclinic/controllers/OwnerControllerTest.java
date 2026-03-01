package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.fauxspring.ModelMapImpl;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    OwnerController ownerController;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    void processFindFormWildcardString() {
        //Given
        Owner owner = new Owner(1L, "Joe", "Buck");
        List<Owner> ownerList = new ArrayList<>();
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(ownerList);

        //When
        String viewName = ownerController.processFindForm(owner, bindingResult, null);

        //Then
        assertEquals("%Buck%", captor.getValue());

    }

    @Test
    void processFindFormWildcardStringAnnotation() {
        //Given
        Owner owner = new Owner(1L, "Joe", "Buck");
        List<Owner> ownerList = new ArrayList<>();

        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(ownerList);

        //When
        String viewName = ownerController.processFindForm(owner, bindingResult, null);

        //Then
        assertEquals("%Buck%", stringArgumentCaptor.getValue());

    }

    @Test
    void processCreationFormWithoutError() {
        //Given
        Owner owner = new Owner(5L, "Joe", "Buck");
        given(ownerService.save(owner)).willReturn(owner);
        given(bindingResult.hasErrors()).willReturn(false);

        //When
        String result = ownerController.processCreationForm(owner, bindingResult);

        //Then
        assertEquals("redirect:/owners/5", result);
    }

    @Test
    void processCreationFormWithError() {
        //Given
        Owner owner = new Owner(5L, "Joe", "Buck");
        given(bindingResult.hasErrors()).willReturn(true);

        //When
        String result = ownerController.processCreationForm(owner, bindingResult);

        //Then
        assertEquals("owners/createOrUpdateOwnerForm", result);
    }
}