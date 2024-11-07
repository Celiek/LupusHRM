package com.Lupus.demo.services;

import com.Lupus.demo.dto.UserDTO;
import com.Lupus.demo.model.Worker;
import com.Lupus.demo.repository.WorkerInterface;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkerService {
    private WorkerInterface workerInterface;

    @Transactional
    public void deletUserById(Long userId){
        workerInterface.deleteUserById(userId);
    }

    @Transactional
    public void addUser(UserDTO userDTO) {
        Worker worker = new Worker();
        worker.setImie(userDTO.getImie());
        worker.setNazwisko(userDTO.getNazwisko());
        worker.setTyp_pracownika(userDTO.getTyp_pracownika());
        worker.setZdjecie(userDTO.getZdjecie());
        worker.setData_przyjazdu(userDTO.getData_Dolaczenia());
        worker.setData_rozpoczecia_pracy(userDTO.getData_rozpoczecia_pracy());
        worker.setDrugieImie(userDTO.getDrugieImie());

        workerInterface.save(worker);
    }
    @Transactional
    public void updateUser(Long userID,
                           String imie,
                           String nazwisko,
                           String typPracownika,
                           byte[] zdjecie,
                           LocalDate dataPrzyjazdu,
                           LocalDate dataRozpoczeciaPracy,
                           String drugieImie){
        workerInterface.updateUser(userID,imie,nazwisko,typPracownika,zdjecie,dataPrzyjazdu,dataRozpoczeciaPracy,drugieImie);
    }

    public void changeEmployeeType(Long idPracownika, String typPracownika){
        workerInterface.changeEmployeeType(idPracownika,typPracownika);
    }

    public List<Worker> findUserByID(Long userId){
       return workerInterface.findUserById(userId);
    }


}
