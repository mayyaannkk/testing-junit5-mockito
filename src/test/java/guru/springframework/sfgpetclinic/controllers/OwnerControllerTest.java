package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.fauxspring.ModelMapImpl;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @Mock
    BindingResult bindingResult;

    @Mock
    Model model;

    @InjectMocks
    OwnerController ownerController;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willAnswer(invocation -> {
            String name = invocation.getArgument(0);
            List<Owner> owners = new ArrayList<>();

            if (name.equals("%Buck%")) {
                owners.add(new Owner(1L, "Joe", "Buck"));
                return owners;
            } else if(name.equals("%DontFindMe%")) {
                return owners;
                //returning empty owners
            } else if(name.equals("%MultipleOwners%")) {
                owners.addAll(0, List.of(new Owner(1L, "Joe", "MultipleOwners"), new Owner(2L, "Buck", "MultipleOwners")));
                return owners;
            }
            throw new RuntimeException("Invalid Argument");
        });
    }

    @Test
    void processFindFormWildcardString() {
        //Given
        Owner owner = new Owner(1L, "Joe", "Buck");
        List<Owner> ownerList = new ArrayList<>();
        final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        //When
        String viewName = ownerController.processFindForm(owner, bindingResult, null);

        //Then
        assertEquals("%Buck%", stringArgumentCaptor.getValue());
        verifyZeroInteractions(model);

    }

    @Test
    void processFindFormWildcardStringAnnotation() {
        //Given
        Owner owner = new Owner(1L, "Joe", "Buck");

        //When
        String viewName = ownerController.processFindForm(owner, bindingResult, null);

        //Then
        assertEquals("%Buck%", stringArgumentCaptor.getValue());
        assertEquals("redirect:/owners/1", viewName);
        verifyZeroInteractions(model);
    }

    @Test
    void processFindFormDontFind() {
        //Given
        Owner owner = new Owner(1L, "Joe", "DontFindMe");

        //When
        String viewName = ownerController.processFindForm(owner, bindingResult, null);

        verifyNoMoreInteractions(ownerService);

        //Then
        assertEquals("%DontFindMe%", stringArgumentCaptor.getValue());
        assertEquals("owners/findOwners", viewName);
        verifyZeroInteractions(model);
    }

    @Test
    void processFindFormMultipleOwners() {
        //Given
        Owner owner = new Owner(1L, "Joe", "MultipleOwners");
        InOrder inOrder = Mockito.inOrder(ownerService, model);

        //When
        String viewName = ownerController.processFindForm(owner, bindingResult, model);

        //Then
        assertEquals("%MultipleOwners%", stringArgumentCaptor.getValue());
        assertEquals("owners/ownersList", viewName);

        //inorder asserts -- order matters here
        inOrder.verify(ownerService).findAllByLastNameLike(anyString());
        inOrder.verify(model, times(1)).addAttribute(anyString(), anyList());

        verifyNoMoreInteractions(model);
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