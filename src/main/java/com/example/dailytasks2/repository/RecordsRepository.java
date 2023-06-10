package com.example.dailytasks2.repository;

import com.example.dailytasks2.domain.Entity;
import com.example.dailytasks2.domain.Records;
import com.example.dailytasks2.domain.User;

import java.sql.*;
import java.sql.Date;
import java.time.LocalTime;
import java.util.*;

public class RecordsRepository implements Repository0<Integer, Records> {

    private String url;
    private String username_post;
    private String password;

    public RecordsRepository(String url, String username_post, String password) {
        this.url = url;
        this.username_post = username_post;
        this.password = password;
    }

    @Override
    public Optional<Records> create(Records entity) {
        String sql = "insert into records (log_in, email_adress, first_name, last_name) values (?, ?, ?, ?)";
        //       validator.validate(entity);
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setTime(1, Time.valueOf(entity.getLogIn()));
            ps.setString(2, entity.getEmailAdress());
            ps.setString(3, entity.getFirstName());
            ps.setString(4, entity.getLastName());

            ps.executeQuery();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Optional.ofNullable(entity);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Records> update(Records old_entity, Records new_entity) {
        String sql = "update records set email_adress = ?, log_in = ?, first_name = ?, last_name = ? where id = ?";
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, new_entity.getEmailAdress());
            ps.setTime(2, Time.valueOf(new_entity.getLogIn()));
            ps.setString(3, new_entity.getFirstName());
            ps.setString(4, new_entity.getLastName());
            ps.setInt(3, old_entity.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Optional.ofNullable(new_entity);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Object> delete(Integer s) {
        String sql = "delete from records where id = ?";
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
    public List<Records> read() {
        List<Records> records = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from records");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String emailAdress = resultSet.getString("email_adress");
                LocalTime logIn = resultSet.getTime("log_in").toLocalTime();
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                Records records1 = new Records(id, logIn, emailAdress, firstName, lastName);
                records.add(records1);
            }
            return records;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    @Override
    public List<Records> findOne(Integer s) {
        List<Records> records = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * from records where id = ?")) {

            ps.setInt(1, s);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String emailAdress = resultSet.getString("email_adress");
                LocalTime logIn = resultSet.getTime("log_in").toLocalTime();
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                Records records1 = new Records(id, logIn, emailAdress, firstName, lastName);
                records.add(records1);
            }
            return records;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}
