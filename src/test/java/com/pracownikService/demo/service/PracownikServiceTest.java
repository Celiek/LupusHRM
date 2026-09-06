package com.pracownikService.demo.service;

import com.pracownikService.demo.repo.PracownikRepo;
import com.pracownikService.exception.PracownikError;
import com.pracownikService.exception.PracownikException;
import jdk.jfr.Name;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PracownikServiceTest {

    @Mock
    private PracownikRepo repo;

    @InjectMocks
    private PracownikService service;
// do poprawki
//    @Test
//    @Disabled
//    @Name("Utworzono pracownika")
//    void shouldCreatePracownik() {
//        //given
//        PracownikDTO dto = new PracownikDTO(
//            "Jan Kowalski",
//            25,
//            TypPracownika.FIZYCZNY,
//            Uprawnienia.PRACOWNIK,
//                "photo1.jpg",
//                LocalDate.of(2026,10,10),
//                LocalDate.of(2026,10,10)
//        );
//        //when
//       service.addPracownik(dto);
//       //then
//       verify(repo).addPracownik(dto);
//    }
//
//    @Test
//    void shouldThrowEmptyNameError() {
//
//        PracownikDTO dto = new PracownikDTO(
//                "",
//                25,
//                TypPracownika.FIZYCZNY,
//                Uprawnienia.PRACOWNIK,
//                "photo1.jpg",
//                LocalDate.of(2026,10,10),
//                LocalDate.of(2026,10,10)
//        );
//
//        PracownikException ex = assertThrows(
//                PracownikException.class,
//                () -> service.addPracownik(dto)
//        );
//        assertEquals(PracownikError.EMPTY_NAME, ex.getError());
//    }
//
//    @Test
//    void shouldThrowTooYoungError() {
//
//        PracownikDTO dto = new PracownikDTO(
//                "Adrian Nowak",
//                16,
//                TypPracownika.FIZYCZNY,
//                Uprawnienia.PRACOWNIK,
//                "photo1.jpg",
//                LocalDate.of(2026,10,10),
//                LocalDate.of(2026,10,10)
//        );
//
//        PracownikException ex = assertThrows(
//                PracownikException.class,
//                () -> service.addPracownik(dto)
//        );
//        assertEquals(PracownikError.TOO_YOUNG, ex.getError());
//    }

//    @Test
//    @Name("Update zdjęcia")
//    void shouldUpdateZdjecie(){
//        //given
//        Long id = 1L;
//        String zdjecie = "photo1.jpg";
//
//        when(repo.updateZdjecie(id,zdjecie)).thenReturn(1);
//
//        //when
//        service.updateZdjecie(id,zdjecie);
//
//        //then
//        verify(repo).updateZdjecie(id,zdjecie);
//    }

//    @Test
//    void shouldThrowWhenPracownikDoesNotExist() {
//        //given
//        Long id = 1L;
//        String zdjecie = "photo1.jpg";
//
//        //when
//        when(repo.updateZdjecie(id,zdjecie)).thenReturn(0);
//
//        //then
//        PracownikException exc = assertThrows(
//                PracownikException.class,
//                () -> service.updateZdjecie(id,zdjecie)
//        );
//
//        assertEquals(PracownikError.PRACOWNIK_NOT_FOUND, exc.getError());
//    }
}