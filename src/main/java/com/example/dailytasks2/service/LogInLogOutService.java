package com.example.dailytasks2.service;

import com.example.dailytasks2.domain.*;
import com.example.dailytasks2.repository.Repository0;
import com.example.dailytasks2.utils.observer.Observable;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class LogInLogOutService extends Observable implements LogInLogOutService0 {

    Repository0<Integer, Records> recordsRepository0;
    UserType user = UserType.MANAGER;
    Repository0<Integer, Employee> employeeRepository0;
    Repository0<Integer, Manager> managerRepository0;

    private void testExistance(String email) throws Exception{
        boolean ok = false;
        List<Employee> employees = employeeRepository0.read();
        List<Manager> managers = managerRepository0.read();
        for(Employee employee : employees)
            if(Objects.equals(employee.getEmailAdress(), email))
            {
                ok = true;
                employeeRepository0.update(employee, new Employee(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmailAdress(), EmployeeStatus.PREZENT));
                change("employees", employee, new Employee(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmailAdress(), EmployeeStatus.PREZENT));

                recordsRepository0.create(new Records(1, LocalTime.now(), employee.getEmailAdress(), employee.getFirstName(), employee.getLastName()));
                change("records", null, new Records(1, LocalTime.now(), employee.getEmailAdress(), employee.getFirstName(), employee.getLastName()));
                user=UserType.ANGAJAT;
                break;
            }
        for(Manager manager : managers)
            if(Objects.equals(manager.getEmailAdress(), email))
            {
                ok = true;
                user=UserType.MANAGER;
                break;
            }
        if(!ok)
            throw new Exception("Email gresit!");
    }



    @Override
    public UserType LogIn(String email) {
       try {
           testExistance(email);
           return user;
       } catch (Exception e) {
           throw new RuntimeException(e);
       }

    }

    @Override
    public void LogOut(String s) {
        List<Employee> employees = employeeRepository0.read();
        for(Employee employee : employees)
            if(Objects.equals(employee.getEmailAdress(), s))
                employeeRepository0.update(employee, new Employee(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmailAdress(), EmployeeStatus.ABSENT));
    }
}
