package com.example.dailytasks2.service;

import com.example.dailytasks2.domain.Employee;
import com.example.dailytasks2.domain.User;
import com.example.dailytasks2.domain.User;
import com.example.dailytasks2.repository.Repository0;
import com.example.dailytasks2.utils.observer.Observable;

import java.util.List;
import java.util.Objects;

public class UserService extends Observable implements Service0<User> {
    
    Repository0<Integer,User> userRepository0;

    public UserService(Repository0<Integer, User> userRepository0) {
        this.userRepository0 = userRepository0;
    }

    private void testEmail(User entity) throws Exception{
        List<User> users = userRepository0.read();
        for (User user : users)
            if(Objects.equals(user.getEmailAdress(), entity.getEmailAdress()))
                throw new Exception("Email-ul exista deja!");
    }

    private void testExistance(User entity) throws Exception{
        boolean ok = false;
        List<User> users = userRepository0.read();
        for (User user : users)
            if(Objects.equals(user.getEmailAdress(), entity.getEmailAdress()) && Objects.equals(user.getPassword(), entity.getPassword()))
            {
                ok = true;
                break;
            }
        if(!ok)
            throw new Exception("Email sau parola gresite!");
    }
    
    @Override
    public void add(User entity) {
        try {
            testEmail(entity);
            userRepository0.create(entity);
            change("users", null, entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User entity) {
        try {
            testExistance(entity);
            userRepository0.delete(entity.getId());
            change("users", entity, entity);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User old_entity, User new_entity) {
        try {
            testExistance(old_entity);
            userRepository0.update(old_entity, new_entity);
            change("users", old_entity, new_entity);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getEntityById(Integer s) {
        try {
            testExistance(userRepository0.findOne(s).get(0));
            return userRepository0.findOne(s).get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllEntities() {
        return userRepository0.read();
    }

    public boolean testEmail(String email) {
        boolean ok = false;
        User user1= new User(0,"", "");
        List<User> users = userRepository0.read();
        for (User user : users)
            if (Objects.equals(user.getEmailAdress(), email)) {
                ok = true;
                user1 = user;
                break;
            }
        return testPassword(user1.getPassword()) && ok;
    }

    public boolean testPassword(String password){
        List<User> users = userRepository0.read();
        for (User user : users)
            if (Objects.equals(user.getPassword(), password)) {
                return true;
            }
        return false;
    }
}
