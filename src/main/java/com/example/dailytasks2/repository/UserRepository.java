package com.example.dailytasks2.repository;

import com.example.dailytasks2.domain.Entity;
import com.example.dailytasks2.domain.Manager;
import com.example.dailytasks2.domain.User;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class UserRepository implements Repository0<Integer, User>{

    private String url;
    private String username_post;
    private String password;

    public UserRepository(String url, String username_post, String password) {
        this.url = url;
        this.username_post = username_post;
        this.password = password;
    }


    @Override
    public Optional<User> create(User entity) {
        String sql = "insert into users (email_user, password) values (?, ?)";
 //       validator.validate(entity);
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getEmailAdress());
            ps.setString(2, entity.getPassword());

            ps.executeQuery();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Optional.ofNullable(entity);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User old_entity, User new_entity) {
        String sql = "update users set email_user = ?, password = ? where email_user = ?";
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, new_entity.getEmailAdress());
            ps.setString(2, new_entity.getPassword());
            ps.setString(3, old_entity.getEmailAdress());

            ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Optional.ofNullable(new_entity);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Object> delete(Integer s) {
        String sql = "delete from users where id = ?";
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
    public List<User> read() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String emailAdress = resultSet.getString("email_user");
                String password = resultSet.getString("password");
                Integer id = resultSet.getInt("id");

                User user = new User(id, emailAdress, password);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> findOne(Integer s) {
        List<User> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * from users where id = ?")) {

            ps.setInt(1, s);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String emailAdress = resultSet.getString("email_user");
                String password = resultSet.getString("password");
                Integer id = resultSet.getInt("id");

                User user = new User(id, emailAdress, password);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
