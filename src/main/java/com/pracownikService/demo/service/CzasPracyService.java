package com.pracownikService.demo.service;

import com.pracownikService.demo.entity.CzasPracy;
import com.pracownikService.demo.entity.Pracownik;
import com.pracownikService.demo.repo.CzasPracyRepository;
import com.pracownikService.demo.repo.PracownikRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CzasPracyService {
    @Autowired
    private final CzasPracyRepository czasPracyRepo;
    @Autowired
    private final PracownikRepo pracownikRepo;

    @Transactional
    public void startCzasPracyForPracownik(Long idPracownik){

        Pracownik pracownik = pracownikRepo
                .findById(idPracownik)
                .orElseThrow(() -> new RuntimeException(
                        "Nie znaleziono pracownika o ID:" +idPracownik
                ));

        LocalDate dzisiaj = LocalDate.now();

        if(czasPracyRepo.existsByPracownikIdPracownikAndDataPracy(
                idPracownik,
                dzisiaj)) {
            throw new RuntimeException(
                    "Pracownik nie ma wpisu czasu pracy na dzisiaj"
            );
        }

        CzasPracy czasPracy = new CzasPracy();
        czasPracy.setPracownik(pracownik);
        czasPracy.setDataPracy(dzisiaj);
        czasPracy.setStartPracy(LocalTime.now());

        czasPracyRepo.save(czasPracy);
    }

    @Transactional
    public void startCzasPracyForPracownicy(List<Long> idsPracownikow){

        List<Pracownik> pracownicy = pracownikRepo.findAllById(idsPracownikow);

        LocalDate dataPracy = LocalDate.now();
        LocalTime startPracy = LocalTime.now();

        List<CzasPracy> czasyPracy = pracownicy.stream()
                .map(pracownik -> {
                    CzasPracy czasPracy = new CzasPracy();
                    czasPracy.setPracownik(pracownik);
                    czasPracy.setDataPracy(dataPracy);
                    czasPracy.setStartPracy(startPracy);

                    return czasPracy;
                }).toList();
        czasPracyRepo.saveAll(czasyPracy);
    }

    @Transactional
    public void stopPracyDlaPracownika(Long idPracownik){
            CzasPracy czasPracy = czasPracyRepo
                    .findByPracownikIdPracownikAndDataPracy(
                            idPracownik,
                            LocalDate.now()
                    )
                    .orElseThrow(()->
                            new RuntimeException("pracownik o podanym id nie pracuje dzisiaj + " +idPracownik));

            if (czasPracy.getStopPracy() != null){
                throw new RuntimeException("Praca została już zakończona !");
            }

            czasPracy.setStopPracy(LocalTime.now());
    }

    @Transactional
    public void setStopPracyForPracownicy(List<Long> idPracownikow){
       LocalDate dzisiaj = LocalDate.now();
       List<CzasPracy> czasyPracy =
               czasPracyRepo
               .findAllByPracownik_IdPracownikInAndDataPracy(
                       idPracownikow,dzisiaj);

       if(czasyPracy.isEmpty()){
           throw new RuntimeException(
                   "Nie znaleziono wpisu dla podanych pracowników"
           );
       }
       LocalTime teraz = LocalTime.now();
        czasyPracy.forEach(czasPracy -> {
            if(czasPracy.getStopPracy() == null){
                czasPracy.setStopPracy(teraz);
            }
        });
    }

    @Transactional
    public void updateStartPracyForPracownik(Long id,
                                             LocalDate dataPracy,
                                             LocalTime startPracy){

        CzasPracy czasPracy = czasPracyRepo.findByPracownik_IdPracownikAndDataPracy(
                id,dataPracy
        ).orElseThrow( ()->
                new RuntimeException("Nie znaleziono czasu pracy dla pracownika o Id: "
                        + id +
                        " dla daty: "+
                        dataPracy)
        );

        czasPracy.setStartPracy(startPracy);
    }

    @Transactional
    public void updateStartPracyForPracownicy(List<Long> ids,
                                              LocalDate dataPracy,
                                              LocalTime startPracy){
        List<CzasPracy> czasyPRacy = czasPracyRepo.findAllByPracownik_IdPracownikInAndDataPracy(
                ids,
                dataPracy
        );
        czasyPRacy.forEach(czasPracy -> czasPracy.setStartPracy(startPracy));
    }
}
