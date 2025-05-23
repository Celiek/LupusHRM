package com.Lupus.lupus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.Lupus.lupus.repository.uwagiPracownikowRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class uwagiPracownikowService {

    @Autowired
    private uwagiPracownikowRepository repo;

    public List<Object[]> getUwagiByPracownikId(Long id) {
        return repo.findAllByPracownikId(id);
    }

    public List<Object[]> searchByTresc(String szukanytekst){
        return repo.searchByTresc(szukanytekst);
    }

    public List<Object[]> findByDataPo(LocalDate po){
        return repo.findByDataPo(po);
    }

    public void deleteByPracownikId(Long id){
        repo.deleteByPracownikId(id);
    }

    public void updateUwagaOPcjonalne(Long idUwagi,
                                      Long idPracowenika,
                                      LocalDate dataDodania,
                                      String trescUwagi,
                                      String autoruwagi){
        repo.updateUwagaOpcjonalnie(idUwagi, idPracowenika, dataDodania, trescUwagi, autoruwagi);
    }

    public List<Object[]> getAllUwagi(){
        return repo.findAllUwagi();
    }
}
