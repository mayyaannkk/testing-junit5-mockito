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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository repository;

    @InjectMocks
    SpecialitySDJpaService service;

    @Test
    void delete() {
        Speciality speciality = new Speciality();
        service.delete(speciality);

        verify(repository).delete(any(Speciality.class));
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
    }

    @Test
    void deleteByIdVerify() {
        service.deleteById(1L);
        service.deleteById(1L);

        // Here it is checking if the repository was called with this argument value, 1L here, and how many times it was called with that value
        verify(repository, times(2)).deleteById(anyLong());

        // repository called at least 2 times with argument 1L
        verify(repository, atLeast(2)).deleteById(1L);

        // repository called at most 2 times with argument 1L
        verify(repository, atMost(2)).deleteById(1L);

        // check if repository called with value 1L at least once
        verify(repository, atLeastOnce()).deleteById(1L);

        // check if repository never called with value 5L
        verify(repository, never()).deleteById(5L);
    }

    @Test
    void findByIDTest() {
        Speciality speciality = new Speciality();
        when(repository.findById(1L)).thenReturn(Optional.of(speciality));

        Speciality foundSpeciality = service.findById(1L);
        assertNotNull(foundSpeciality);
        verify(repository).findById(1L);
    }

    @Test
    void findByIDBDDTest() {
        Speciality speciality = new Speciality();

        given(repository.findById(1L)).willReturn(Optional.of(speciality));

        Speciality foundSpeciality = service.findById(1L);

        assertNotNull(foundSpeciality);
        verify(repository).findById(1L);
    }
}