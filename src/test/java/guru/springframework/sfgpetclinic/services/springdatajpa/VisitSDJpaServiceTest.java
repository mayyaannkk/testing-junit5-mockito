package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSDJpaService visitService;

    @DisplayName("Test Find All")
    @Test
    void findAll() {
        //Given
        Set<Visit> visits = new HashSet<>();
        Visit visit = new Visit();
        visits.add(visit);
        given(visitRepository.findAll()).willReturn(visits);

        //When
        Set<Visit> returnedVisits = visitService.findAll();

        //Then
        assertEquals(visits.size(), returnedVisits.size());
        then(visitRepository).should().findAll();
    }

    @DisplayName("Test Find By ID")
    @Test
    void findById() {
        //Given
        Visit visit = new Visit();
        given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));

        //When
        Visit returnedVisit = visitService.findById(1L);

        //Then
        assertEquals(visit, returnedVisit);
        then(visitRepository).should().findById(anyLong());
    }

    @DisplayName("Test Save")
    @Test
    void save() {
        //Given
        Visit visit = new Visit();
        given(visitRepository.save(any(Visit.class))).willReturn(visit);

        //When
        Visit returnedVisit = visitService.save(visit);

        //Then
        assertEquals(visit, returnedVisit);
        assertNotNull(returnedVisit);
        then(visitRepository).should().save(any(Visit.class));
    }

    @DisplayName("Test Delete")
    @Test
    void delete() {
        //When
        visitService.delete(new Visit());

        //Then
        then(visitRepository).should().delete(any(Visit.class));
    }

    @DisplayName("Test Delete By ID")
    @Test
    void deleteById() {
        //When
        visitService.deleteById(1L);

        //Then
        then(visitRepository).should().deleteById(anyLong());
    }
}