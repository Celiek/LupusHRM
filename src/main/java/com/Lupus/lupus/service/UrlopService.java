package com.Lupus.lupus.service;

import com.Lupus.lupus.repository.UrlopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrlopService {
    @Autowired
    private UrlopyRepository repo;

    //zwraca listę wszystkich urlopów pracowników
    public List<Object[]> findAllUrlopy(){
        return repo.findAllUrlopy();
    }

    public List<Object[]> findUrlopyFor(Long idPracownika){
        return repo.findUrlopyFor(idPracownika);
    }
}
