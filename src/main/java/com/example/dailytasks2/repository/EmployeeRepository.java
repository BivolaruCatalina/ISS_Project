package com.example.dailytasks2.repository;

import com.example.dailytasks2.domain.Employee;
import com.example.dailytasks2.domain.EmployeeStatus;
import com.example.dailytasks2.domain.Entity;
import com.example.dailytasks2.validators.Validator;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class EmployeeRepository implements Repository0<Integer, Employee> {

    private String url;
    private String username_post;
    private String password;
    private Validator<Employee> validator;

    public EmployeeRepository(String url, String username_post, String password, Validator<Employee> validator) {
        this.url = url;
        this.username_post = username_post;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Optional<Employee> create(Employee entity) {
        String sql = "insert into employees (first_name, last_name, email_adress, employee_status) values (?, ?, ?, ?)";
        validator.validate(entity);
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getEmailAdress());
            ps.setString(4, entity.getStatus().name());

            ps.executeQuery();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Optional.ofNullable(entity);
        }
        return Optional.empty();
    }

//    @Override
//    public Optional<Employee> update(Employee old_entity, Employee new_entity) {
//        String sql = "update employees set email_adress = ? where email_adress = ?";
//        validator.validate(new_entity);
//        try (Connection connection = DriverManager.getConnection(url, username_post, password);
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setString(1, new_entity.getEmailAdress());
//            ps.setString(2, old_entity.getEmailAdress());
//
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            //e.printStackTrace();
//            return Optional.ofNullable(new_entity);
//        }
//        return Optional.empty();
//    }

    @Override
    public Optional<Object> delete(Integer s) {
        String sql = "delete from employees where id = ?";
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
    public List<Employee> read() {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from employees");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email_adress = resultSet.getString("email_adress");
                Integer id = resultSet.getInt("id");
                EmployeeStatus stat = EmployeeStatus.valueOf(resultSet.getString("employee_status"));

                Employee employee = new Employee(id, firstName, lastName, email_adress, stat);
                employees.add(employee);
            }
            return employees;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public List<Employee> findOne(Integer s) {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * from employees where id = ?")) {

            ps.setInt(1, s);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email_adress = resultSet.getString("email_adress");
                Integer id = resultSet.getInt("id");
                EmployeeStatus stat = EmployeeStatus.valueOf(resultSet.getString("employee_status"));

                Employee employee = new Employee(id, firstName, lastName, email_adress, stat);
                employees.add(employee);
            }
            return employees;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }


//    public Optional<Employee> updateLastName(Employee old_entity, Employee new_entity) {
//        String sql = "update employees set last_name = ? where email_adress = ?";
//        validator.validate(new_entity);
//        try (Connection connection = DriverManager.getConnection(url, username_post, password);
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//
//            ps.setString(1, new_entity.getLastName());
//            ps.setString(2, old_entity.getEmailAdress());
//
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            //e.printStackTrace();
//            return Optional.ofNullable(new_entity);
//        }
//        return Optional.empty();
//    }

    @Override
    public Optional<Employee> update(Employee old_entity, Employee new_entity) {
        String sql = "update employees set first_name = ?, last_name = ?, email_adress = ?, employee_status = ? where email_adress = ?";
        validator.validate(new_entity);
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1,new_entity.getFirstName());
            ps.setString(2,new_entity.getLastName());
            ps.setString(3,new_entity.getEmailAdress());
            ps.setString(4, new_entity.getStatus().toString());
            ps.setString(5, old_entity.getEmailAdress());

            ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Optional.ofNullable(new_entity);
        }
        return Optional.empty();
//        delete(old_entity.getId());
//        create(new_entity);
//        return Optional.empty();
    }
}
