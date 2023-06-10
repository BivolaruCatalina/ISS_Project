package com.example.dailytasks2.controller;

import com.example.dailytasks2.domain.*;
import com.example.dailytasks2.service.Service0;
import com.example.dailytasks2.utils.FilteredTableWrapper;
import com.example.dailytasks2.utils.observer.Observer0;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.example.dailytasks2.utils.CollectionUtils.listFromIterable;

public class EmployeeController implements Observer0 {

    Employee employee;

    Service0<Task> taskService0;
    Service0<Employee> employeeService0;
    Service0<Records> recordsService0;
    public static final String VIEW_NAME = "/EmployeeView.fxml";
    public static final String VIEW_TITLE = "EmployeeView";

    private FilteredTableWrapper<Task> ftw;

    public EmployeeController(com.example.dailytasks2.domain.Employee employee) {
        this.employee = employee;
    }

    @FXML
    private AnchorPane Employee;

    @FXML
    private Button absentButton;

    @FXML
    private TableColumn<Task, LocalTime> deadlineColumn;

    @FXML
    private Button logOutButton;

    @FXML
    private Button presentButton;

    @FXML
    private TableColumn<Task, TaskStatus> statusColumn;

    @FXML
    private TableColumn<Task, String> taskDescriptionColumn;

    @FXML
    private TableColumn<Task, String> taskNameColumn;

    @FXML
    private Button taskStatusButton;

    @FXML
    private TableView<Task> taskTable;

    ObservableList<Task> modelTask = FXCollections.observableArrayList();

    public void initialize() {

        ftw = new FilteredTableWrapper<>(taskTable);

        taskNameColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));
        taskDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Task, TaskStatus>("task_status"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<Task, LocalTime>("deadline"));

        taskTable.setItems(modelTask);

        presentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                employeeService0.update(employee, new Employee(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmailAdress(), EmployeeStatus.PREZENT));
                loadTableData();
            }
        });

        absentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                employeeService0.update(employee, new Employee(employee.getId(), employee.getFirstName(), employee.getLastName(), employee.getEmailAdress(), EmployeeStatus.ABSENT));
                loadTableData();
            }
        });

        taskStatusButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                    Task task = taskTable.getSelectionModel().getSelectedItem();
                    taskService0.update(task, new Task(task.getId(), task.getTitle(), task.getDescription(), task.getDeadline(), task.getEmailAdress(), TaskStatus.DONE));

            }
        });

//        logOutButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//            }
//        });
    }

    private List<Task> getTasksByEmail(String email) {
        List<Task> tasks = new ArrayList<>();
        List<Task> allTasks = taskService0.getAllEntities();
            for(Task task : allTasks)
                if(task.getEmailAdress() == email)
                    tasks.add(task);
            return tasks;
    }
    private void loadTableData() {
        Iterable<Task> elements = getTasksByEmail(employee.getEmailAdress());
        ftw.setTableData(listFromIterable(elements));
    }

    public void setService(Service0<Employee> employeeService0) {
        this.employeeService0 = employeeService0;
        List<Task> tasks = getTasksByEmail(employee.getEmailAdress());
        modelTask.setAll(tasks);
    }


    @Override
    public void onChange(String key, Object oldValue, Object newValue) {
        if ("tasks".equals(key)) {
            loadTableData();
        }
    }
}
