package com.pracownikService.demo.service;

import com.pracownikService.demo.entity.CzasPracy;
import com.pracownikService.demo.entity.Pracownik;
import com.pracownikService.demo.repo.CzasPracyRepository;
import com.pracownikService.demo.repo.PracownikRepo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CzasPracyServiceTest {

    @InjectMocks
    private CzasPracyService czasPracyService;

    @Mock
    private CzasPracyRepository czasPracyRepo;

    @Mock
    private PracownikRepo pracownikRepo;

//    @Test
//    @Disabled
//    void shouldStartWorkForPracownik(){
//        //given
//        Long id = 1L;
//
//        Pracownik pracownik = new Pracownik();
//        pracownik.setIdPracownik(id);
//
//        when(pracownikRepo.findById(id))
//                .thenReturn(Optional.of(pracownik));
//
//        //when
//        czasPracyService.startCzasPracyForPracownik(id);
//
//        //then
//        verify(czasPracyRepo).save(any(CzasPracy.class));
//    }

    @Test
    void itShouldStartWorkForPracownicy(){
        //given
        //przygotowanie danych
        List<Long> pracownicy = new ArrayList<>();
        pracownicy.add(1L);
        pracownicy.add(2L);
        pracownicy.add(3L);
        pracownikRepo.findAllById(pracownicy);

        Pracownik pracownik1 = new Pracownik();
        pracownik1.setIdPracownik(1L);
        Pracownik pracownik2 = new Pracownik();
        pracownik2.setIdPracownik(2L);
        Pracownik pracownik3 = new Pracownik();
        pracownik3.setIdPracownik(3L);

        List<Pracownik> listaPracownikow =
                List.of(pracownik1, pracownik2, pracownik3);
        // mockito mockuje(udaje) repozytorium i zwraca wynik
        // w postaci listy pracowników
        when(pracownikRepo.findAllById(pracownicy))
                .thenReturn(listaPracownikow);
        //when
        // uruchamia testowaną metodę i testuje logikę
        czasPracyService.startCzasPracyForPracownicy(pracownicy);

        //then

        ArgumentCaptor<List<CzasPracy>> captor =
                ArgumentCaptor.forClass(List.class);
        // sprawdza wynik działania metody w serwisie
        verify(czasPracyRepo)
                .saveAll(captor.capture());

        List<CzasPracy> zapisaneCzasy =
                captor.getValue();

        assertEquals(pracownik1,
                zapisaneCzasy.get(0).getPracownik());

        assertEquals(pracownik2,
                zapisaneCzasy.get(1).getPracownik());

        assertEquals(pracownik3,
                zapisaneCzasy.get(2).getPracownik());
    }

    @Test
    void itShloudStopPracyForPracownik(){

        //given
        Long idPracownika = 1L;

        CzasPracy czasPracy = new CzasPracy();
        czasPracy.setStopPracy(null);

        when(czasPracyRepo.findByPracownikIdPracownikAndDataPracy(
                eq(idPracownika),
                any(LocalDate.class))
        ).thenReturn(Optional.of(czasPracy));
        //when
        czasPracyService.stopPracyDlaPracownika(idPracownika);

        //then
        assertNotNull(czasPracy.getStopPracy());
    }

    @Test
    void itShouldStopPracyForPracownicy(){

        //given
        List<Long> idPracownikow = new ArrayList<>();
        idPracownikow.add(1L);
        idPracownikow.add(2L);
        idPracownikow.add(3L);

        CzasPracy cp1 = new CzasPracy();
        cp1.setStopPracy(null);
        CzasPracy cp2 = new CzasPracy();
        cp1.setStopPracy(null);
        CzasPracy cp3 = new CzasPracy();
        cp1.setStopPracy(null);


        when(czasPracyRepo.findAllByPracownik_IdPracownikInAndDataPracy(
                eq(idPracownikow),
                any(LocalDate.class)))
                .thenReturn(List.of(cp1,cp2,cp3));

        //when
        czasPracyService.setStopPracyForPracownicy(idPracownikow);

        //then
        assertNotNull(cp1.getStopPracy());
        assertNotNull(cp2.getStopPracy());
        assertNotNull(cp3.getStopPracy());
    }

    @Test
    void itShouldUpdateStartPracyForPracownik(){
        //given
        Long id = 1L;
        LocalDate data = LocalDate.now();
        LocalTime stopPracy = LocalTime.of(8,0);

        CzasPracy czasPracy = new CzasPracy();
        czasPracy.setStartPracy(null);

        when(czasPracyRepo.findByPracownik_IdPracownikAndDataPracy(
                id,data
        )).thenReturn(Optional.of(czasPracy));

        //when
        czasPracyService.updateStartPracyForPracownik(id,data,stopPracy);

        //then
        assertEquals(stopPracy,czasPracy.getStartPracy());

        verify(czasPracyRepo)
                .findByPracownik_IdPracownikAndDataPracy(id,data);
    }

    @Test
    void itShouldUpdateStartPracyForPracownicy(){
        //given
        LocalDate dataPracy = LocalDate.now();
        List<Long> idPracownikow = List.of(1L,2L,3L);
        LocalTime startPracy = LocalTime.of(8,0);

        CzasPracy cp1 = new CzasPracy();
        CzasPracy cp2 = new CzasPracy();
        CzasPracy cp3 = new CzasPracy();

        List<CzasPracy> czasyPracy =
                List.of(cp1,cp2,cp3);

        cp1.setStartPracy(null);
        cp2.setStartPracy(null);
        cp3.setStartPracy(null);

        when(czasPracyRepo.findAllByPracownik_IdPracownikInAndDataPracy(
                idPracownikow,
                dataPracy
        )).thenReturn(czasyPracy);

        //when
        czasPracyService.updateStartPracyForPracownicy(idPracownikow
                ,dataPracy
                ,startPracy);
        //then

        verify(czasPracyRepo)
                .findAllByPracownik_IdPracownikInAndDataPracy(
                        idPracownikow,
                        dataPracy
                );

        assertEquals(startPracy,cp1.getStartPracy());
        assertEquals(startPracy,cp2.getStartPracy());
        assertEquals(startPracy,cp3.getStartPracy());
    }
    @Test
    void shouldUpdateStopPracyForPracownicy(){
        //given
        LocalDate dataPracy = LocalDate.now();
        List<Long> idPracownikow = List.of(1L,2L,3L);
        LocalTime stopPracy = LocalTime.of(16,30);

        CzasPracy cp1 = new CzasPracy();
        cp1.setStopPracy(null);
        CzasPracy cp2 = new CzasPracy();
        cp2.setStopPracy(null);
        CzasPracy cp3 = new CzasPracy();
        cp3.setStopPracy(null);

        List<CzasPracy> czasyPracy =
                List.of(cp1,cp2,cp3);

        when(czasPracyRepo.findAllByPracownik_IdPracownikInAndDataPracy(
                idPracownikow,
                dataPracy))
                .thenReturn(czasyPracy);

        //when

        czasPracyService.updateStopPracyForPracownicy(
                idPracownikow,
                dataPracy,
                stopPracy);

        //then
        assertTrue(
                czasyPracy.stream()
                        .allMatch(cp -> stopPracy.equals(cp.getStopPracy()))
        );

        verify(czasPracyRepo)
                .findAllByPracownik_IdPracownikInAndDataPracy(
                        idPracownikow,
                        dataPracy
                );
    }
}