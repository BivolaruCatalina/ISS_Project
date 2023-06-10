package com.example.dailytasks2.service;

import com.example.dailytasks2.domain.Employee;
import com.example.dailytasks2.repository.EmployeeRepository;
import com.example.dailytasks2.repository.Repository0;
import com.example.dailytasks2.utils.observer.Observable;

import java.util.List;
import java.util.Objects;

public class EmployeeService extends Observable implements Service0<Employee> {

    Repository0<Integer, Employee> employeeRepository0;

    public EmployeeService(Repository0<Integer, Employee> employeeRepository0) {
        this.employeeRepository0 = employeeRepository0;
    }

    private void testEmail(Employee entity) throws Exception{
        List<Employee> employees = employeeRepository0.read();
        for (Employee employee : employees)
            if(Objects.equals(employee.getEmailAdress(), entity.getEmailAdress()))
                throw new Exception("Email-ul exista deja!");
    }

    private void testExistance(Employee entity) throws Exception{
        boolean ok = false;
        List<Employee> employees = employeeRepository0.read();
        for (Employee employee : employees)
            if(Objects.equals(employee.getEmailAdress(), entity.getEmailAdress()))
            {
                ok = true;
                break;
            }
        if(!ok)
            throw new Exception("Angajatul nu exista!");
    }

    @Override
    public void add(Employee entity) {
        try {
            testEmail(entity);
            employeeRepository0.create(entity);
            change("employees", null, entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Employee entity) {
        try {
            testExistance(entity);
            employeeRepository0.delete(entity.getId());
            change("employees", entity, entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Employee old_entity, Employee new_entity) {
        try {
            testExistance(old_entity);
            employeeRepository0.update(old_entity, new_entity);
            change("employees", old_entity, new_entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee getEntityById(Integer s) {
        try {
            testExistance(employeeRepository0.findOne(s).get(0));
            return employeeRepository0.findOne(s).get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> getAllEntities() {
        return employeeRepository0.read();
    }

    public boolean testEmail(String email){
        boolean ok = true;
        List<Employee> employees = employeeRepository0.read();
        for(Employee employee : employees)
            if(Objects.equals(employee.getEmailAdress(), email))
            {
                ok = true;
                break;
            }
        return ok;
    }
}
