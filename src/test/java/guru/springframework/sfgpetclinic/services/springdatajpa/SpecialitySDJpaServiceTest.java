package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService specialitySDJpaService;

    @Test
    void delete() {
        specialitySDJpaService.delete(new Speciality());
    }

    @Test
    void deleteById() {
        specialitySDJpaService.deleteById(1L);
    }

    @Test
    void deleteByIdVerify() {
        specialitySDJpaService.deleteById(1L);
        specialitySDJpaService.deleteById(1L);

        // Here it is checking if the repository was called with this argument value, 1L here, and how many times it was called with that value
        verify(specialtyRepository, times(2)).deleteById(1L);

        // repository called at least 2 times with argument 1L
        verify(specialtyRepository, atLeast(2)).deleteById(1L);

        // repository called at most 2 times with argument 1L
        verify(specialtyRepository, atMost(2)).deleteById(1L);

        // check if repository called with value 1L at least once
        verify(specialtyRepository, atLeastOnce()).deleteById(1L);

        // check if repository never called with value 5L
        verify(specialtyRepository, never()).deleteById(5L);
    }
}