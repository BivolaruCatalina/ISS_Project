package com.example.dailytasks2.repository;

import com.example.dailytasks2.domain.Task;
import com.example.dailytasks2.domain.TaskStatus;
import com.example.dailytasks2.domain.Entity;
import com.example.dailytasks2.domain.Task;
import com.example.dailytasks2.repository.Repository0;
import com.example.dailytasks2.validators.Validator;

import java.sql.*;
import java.sql.Date;
import java.time.LocalTime;
import java.util.*;

public class TaskRepository implements Repository0<Integer, Task> {
    private String url;
    private String username_post;
    private String password;
    //private Validator<Task> validator;

    public TaskRepository(String url, String username_post, String password) {
        this.url = url;
        this.username_post = username_post;
        this.password = password;
        //this.validator = validator;
    }

    @Override
    public Optional<Task> create(Task entity) {
        String sql = "insert into tasks (title, description, deadline, email_adress, task_status) values (?, ?, ?, ?, ?)";
        //validator.validate(entity);
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getDescription());
            ps.setTime(3, Time.valueOf(entity.getDeadline()));
            ps.setString(4, entity.getEmailAdress());
            ps.setString(5, entity.getStatus().toString());

            ps.executeQuery();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Optional.ofNullable(entity);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Object> delete(Integer s) {
        String sql = "delete from tasks where id = ?";
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
    public List<Task> read() {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from tasks");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                LocalTime deadline = resultSet.getTime("deadline").toLocalTime();
                String email_adress = resultSet.getString("email_adress");
                Integer id = resultSet.getInt("id");
                TaskStatus status = TaskStatus.valueOf(resultSet.getString("task_status"));

                Task task = new Task(id, title, description, deadline, email_adress, status);
                tasks.add(task);
            }
            return tasks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public List<Task> findOne(Integer s) {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * from tasks where id = ?")) {

            ps.setInt(1, s);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                LocalTime deadline = resultSet.getTime("deadline").toLocalTime();
                String email_adress = resultSet.getString("email_adress");
                Integer id = resultSet.getInt("id");
                TaskStatus status = TaskStatus.valueOf(resultSet.getString("task_status"));

                Task task = new Task(id, title, description, deadline, email_adress, status);
                tasks.add(task);
            }
            return tasks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public Optional<Task> update(Task old_entity, Task new_entity) {
        String sql = "update tasks set title = ?, description = ?, deadline = ?, email_adress = ?, task_status = ? where id = ?";
        //validator.validate(new_entity);
        try (Connection connection = DriverManager.getConnection(url, username_post, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, new_entity.getTitle());
            ps.setString(2, new_entity.getDescription());
            ps.setTime(3, Time.valueOf(new_entity.getDeadline()));
            ps.setString(4,new_entity.getEmailAdress());
            ps.setString(5,new_entity.getStatus().toString());
            ps.setInt(6,old_entity.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Optional.ofNullable(new_entity);
        }
        return Optional.empty();
    }
}
