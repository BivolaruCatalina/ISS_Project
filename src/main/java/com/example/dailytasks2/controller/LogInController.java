package com.example.dailytasks2.controller;


import com.example.dailytasks2.domain.Employee;
import com.example.dailytasks2.domain.Task;
import com.example.dailytasks2.domain.User;
import com.example.dailytasks2.service.LogInLogOutService;
import com.example.dailytasks2.service.Service0;
import com.example.dailytasks2.service.UserType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogInController {

    LogInLogOutService logInLogOutService;
    Service0<User> userService0;

    Service0<Employee> employeeService0;

    @FXML
    private TextField emailField;

    @FXML
    private Text emailText;

    @FXML
    private void logInButton(ActionEvent event){
        if(userService0.testEmail(emailField.getText()))
        {
            if(logInLogOutService.LogIn(emailField.getText()) == UserType.MANAGER){

            ManagerController managerController = new ManagerController();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(ManagerController.VIEW_NAME));
            loader.setController(managerController);

            Scene scene = null;
            Stage stage = new Stage();
            try {
                scene = new Scene(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            stage.setScene(scene);
            stage.setTitle(ManagerController.VIEW_TITLE);
            stage.show();
        }
        if(logInLogOutService.LogIn(emailField.getText()) == UserType.ANGAJAT){

            EmployeeController employeeController = new EmployeeController(getEmployeeByEmail(emailField.getText()));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(ManagerController.VIEW_NAME));
            loader.setController(employeeController);

            Scene scene = null;
            Stage stage = new Stage();
            try {
                scene = new Scene(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            stage.setScene(scene);
            stage.setTitle(EmployeeController.VIEW_TITLE);
            stage.show();
        }
        }
    }

    @FXML
    private AnchorPane logInPane;

    @FXML
    private TextField passwdField;

    @FXML
    private Text passwdText;



    private Employee getEmployeeByEmail(String email){
        List<Employee> employees = employeeService0.getAllEntities();
        for(Employee employee : employees)
            if(Objects.equals(employee.getEmailAdress(), email))
                return employee;
        return null;
    }

    public void setService(Service0<Employee> employeeService0) {
        this.employeeService0 = employeeService0;
    }

};
