package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository repository;

    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void delete() {
        //Given
        Speciality speciality = new Speciality();

        //When
        service.delete(speciality);

        //Then
        then(repository).should().delete(any(Speciality.class));
    }

    @Test
    void deleteById() {
        //Given
        long id = 1L;

        //When
        service.deleteById(id);
        service.deleteById(id);

        //Then
        then(repository).should(times(2)).deleteById(id);
    }

    @Test
    void deleteByIDAtLeast() {
        //Given

        //When
        service.deleteById(1L);
        service.deleteById(1L);

        //Then
        then(repository).should(atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteByIDAtMost() {
        //When
        service.deleteById(1L);
        service.deleteById(1L);

        //Then
        then(repository).should(atMost(5)).deleteById(1L);
    }

    @Test
    void deleteByIDNever() {
        //When
        service.deleteById(1L);
        service.deleteById(1L);

        //Then
        then(repository).should(never()).deleteById(5L);
    }

    @Test
    void deleteByIdVerify() {
        //When
        service.deleteById(1L);
        service.deleteById(1L);

        //Then
        // Here it is checking if the repository was called with this argument value, 1L here, and how many times it was called with that value
        then(repository).should(times(2)).deleteById(anyLong());

        // repository called at least 2 times with argument 1L
        then(repository).should(atLeast(2)).deleteById(1L);

        // repository called at most 2 times with argument 1L
        then(repository).should(atMost(2)).deleteById(1L);

        // check if repository called with value 1L at least once
        then(repository).should(atLeastOnce()).deleteById(1L);

        // check if repository never called with value 5L
        then(repository).should(never()).deleteById(5L);
    }


    @Test
    void findByIDTest() {
        //Given
        Speciality speciality = new Speciality();
        given(repository.findById(1L)).willReturn(Optional.of(speciality));

        //When
        Speciality foundSpeciality = service.findById(1L);

        //Then
        assertNotNull(foundSpeciality);
        then(repository).should().findById(anyLong());
        then(repository).should(times(1)).findById(anyLong());
        then(repository).shouldHaveNoMoreInteractions();
    }
}