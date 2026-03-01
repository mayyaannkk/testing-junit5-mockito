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
        Set<Visit> visits = new HashSet<>();
        Visit visit = new Visit();
        visits.add(visit);
        when(visitRepository.findAll()).thenReturn(visits);

        Set<Visit> returnedVisits = visitService.findAll();
        assertEquals(visits.size(), returnedVisits.size());
        verify(visitRepository).findAll();
    }

    @DisplayName("Test Find By ID")
    @Test
    void findById() {
        Visit visit = new Visit();
        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(visit));

        Visit returnedVisit = visitService.findById(1L);
        assertEquals(visit, returnedVisit);
        verify(visitRepository).findById(anyLong());
    }

    @DisplayName("Test Save")
    @Test
    void save() {
        Visit visit = new Visit();
        when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        Visit returnedVisit = visitService.save(visit);
        assertEquals(visit, returnedVisit);
        assertNotNull(returnedVisit);
        verify(visitRepository).save(any(Visit.class));
    }

    @DisplayName("Test Delete")
    @Test
    void delete() {
        visitService.delete(new Visit());
        verify(visitRepository).delete(any(Visit.class));
    }

    @DisplayName("Test Delete By ID")
    @Test
    void deleteById() {
        visitService.deleteById(1L);
        verify(visitRepository).deleteById(anyLong());
    }
}