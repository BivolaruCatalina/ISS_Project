package com.example.dailytasks2.service;

import com.example.dailytasks2.domain.Employee;
import com.example.dailytasks2.domain.Manager;
import com.example.dailytasks2.domain.Manager;
import com.example.dailytasks2.repository.Repository0;
import com.example.dailytasks2.utils.observer.Observable;

import java.util.List;
import java.util.Objects;

public class ManagersService extends Observable implements Service0<Manager>{
    
    Repository0<Integer,Manager> managerRepository0;

    public ManagersService(Repository0<Integer, Manager> managerRepository0) {
        this.managerRepository0 = managerRepository0;
    }

    private void testEmail(Manager entity) throws Exception{
        List<Manager> managers = managerRepository0.read();
        for (Manager manager : managers)
            if(Objects.equals(manager.getEmailAdress(), entity.getEmailAdress()))
                throw new Exception("Email-ul exista deja!");
    }

    private void testExistance(Manager entity) throws Exception{
        boolean ok = false;
        List<Manager> managers = managerRepository0.read();
        for (Manager manager : managers)
            if(Objects.equals(manager.getEmailAdress(), entity.getEmailAdress()))
            {
                ok = true;
                break;
            }
        if(!ok)
            throw new Exception("Managerul nu exista!");
    }
    
    @Override
    public void add(Manager entity) {
        try {
            testEmail(entity);
            managerRepository0.create(entity);
            change("managers", null, entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Manager entity) {
        try {
            testExistance(entity);
            managerRepository0.delete(entity.getId());
            change("managers", entity, entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Manager old_entity, Manager new_entity) {
        try {
            testExistance(old_entity);
            managerRepository0.update(old_entity, new_entity);
            change("managers", old_entity, new_entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Manager getEntityById(Integer s) {
        try {
            testExistance(managerRepository0.findOne(s).get(0));
            return managerRepository0.findOne(s).get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Manager> getAllEntities() {
        return managerRepository0.read();
    }

    public boolean testEmail(String email){
        boolean ok = true;
        List<Manager> managers = managerRepository0.read();
        for(Manager manager : managers)
            if(Objects.equals(manager.getEmailAdress(), email))
            {
                ok = true;
                break;
            }
        return ok;
    }
}
