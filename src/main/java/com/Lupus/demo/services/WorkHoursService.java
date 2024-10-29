package com.Lupus.demo.services;

import com.Lupus.demo.repository.WorkHoursInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class WorkHoursService {

    @Autowired
    public WorkHoursInterface workHoursInterface;

    public List<Object[]> getWorkHoursDaily(Long idPracownika){
        return workHoursInterface.getWorkHoursDaily(idPracownika);
    }

    public List<Object[]> getWorkHoursWeekly(){
        return workHoursInterface.getWorkHoursWeekly();
    }

    public void insertWorkHoursForAllEmployees(){
        workHoursInterface.insertWorkHoursForAllEmployees();
    }

    public List<Object[]> findWorkHoursAndPaymentsByEmployeeId(Long idPracownika){
        return workHoursInterface.findWorkHoursAndPaymentsByEmployeeId(idPracownika);
    }

    public void startWorkForAllEmployees(){
        workHoursInterface.startWorkForAllEmployees();
    }

    public void breakTimeForAllEmployees(){
        workHoursInterface.breakTimeForAllEmployees();
    }

    public void endOfBreakForAllEmplyees(){
        workHoursInterface.endOfBreakForAllEmplyees();
    }

    public void updateStartTimeForEmployee(LocalTime czas,
                                            Long idPracownika){
        workHoursInterface.updateStartTimeForEmployee(czas,idPracownika);
    }

    public void updateStartTimeFOrEmployees(LocalTime time){
        workHoursInterface.updateStartTimeFOrEmployees(time);
    }

    public List<Object[]> findWorkHoursByEmployeeId(Long idPracownika){
       return workHoursInterface.findWorkHoursByEmployeeId(idPracownika);
    }
    public List<Object[]> findWorkHoursByEmployeeIdAndRecent(Long idPracownika){
        return workHoursInterface.findWorkHoursByEmployeeIdAndRecent(idPracownika);
    }

}
