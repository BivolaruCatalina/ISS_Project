package com.example.dailytasks2.repository;

import com.example.dailytasks2.domain.Employee;
import com.example.dailytasks2.domain.Manager;
import com.example.dailytasks2.domain.Entity;

import java.sql.*;
import java.util.*;

import com.example.dailytasks2.validators.Validator;

public class ManagerRepository implements Repository0<Integer, Manager>{

    private String url;
    private String username_post;
    private String password;
    private Validator<Manager> validator;

    public ManagerRepository(String url, String username_post, String password, Validator<Manager> validator) {
        this.url = url;
        this.username_post = username_post;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Manager> create(Manager entity) {
        String sql = "insert into managers (first_name, last_name, email_adress) values (?, ?, ?)";
        validator.validate(entity);
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getEmailAdress());

            ps.executeQuery();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Optional.ofNullable(entity);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Manager> update(Manager old_entity, Manager new_entity) {
        String sql = "update managers set first_name = ?, last_name = ?, email_adress = ? where email_adress = ?";
        validator.validate(new_entity);
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, new_entity.getFirstName());
            ps.setString(2, new_entity.getLastName());
            ps.setString(3, new_entity.getEmailAdress());
            ps.setString(4, old_entity.getEmailAdress());

            ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Optional.ofNullable(new_entity);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Object> delete(Integer s) {
        String sql = "delete from managers where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, s);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Manager> read() {
        List<Manager> managers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from managers");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email_adress = resultSet.getString("email_adress");
                Integer id = resultSet.getInt("id");

                Manager manager = new Manager(id, firstName, lastName, email_adress);
                managers.add(manager);
            }
            return managers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managers;
    }

    @Override
    public List<Manager> findOne(Integer s) {
        List<Manager> managers = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * from managers where id = ?")) {

            ps.setInt(1, s);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email_adress = resultSet.getString("email_adress");
                Integer id = resultSet.getInt("id");

                Manager manager = new Manager(id, firstName, lastName, email_adress);
                managers.add(manager);
            }
            return managers;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return managers;
    }
}
