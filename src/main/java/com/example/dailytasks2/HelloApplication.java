package com.example.dailytasks2;

import com.example.dailytasks2.controller.LogInController;
import com.example.dailytasks2.domain.*;
import com.example.dailytasks2.repository.*;
import com.example.dailytasks2.service.*;
import com.example.dailytasks2.validators.EmployeeValidator;
import com.example.dailytasks2.validators.ManagerValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("Reading data from file");
        String username="postgres";
        String password="C.01000011";
        String url="jdbc:postgresql://localhost:5432/daily_tasks";

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LogInView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LogInController logInController = fxmlLoader.getController();
        Repository0<Integer, Employee> employeeRepository =
                new EmployeeRepository(url,username, password,  new EmployeeValidator());
        Repository0<Integer, Records> recordsRepository =
                new RecordsRepository(url,username,password);
        Repository0<Integer, Manager> managerRepository0 =
                new ManagerRepository(url,username,password, new ManagerValidator());
        Repository0<Integer, Task> taskRepository0 =
                new TaskRepository(url,username,password);
        Repository0<Integer,User> userRepository0 =
                new UserRepository(url, username, password);
        Service0<Employee> employeeService0 = new EmployeeService(employeeRepository);
        Service0<Manager> managerService0 = new ManagersService(managerRepository0);
        Service0<Records> recordsService0 = new RecordsService(recordsRepository);
        Service0<Task> taskService0 = new TaskService(taskRepository0);
        Service0<User> userService0 = new UserService(userRepository0);
        logInController.setService(employeeService0);
        stage.setTitle("DailyTasks!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}