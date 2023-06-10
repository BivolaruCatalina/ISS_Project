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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.example.dailytasks2.utils.CollectionUtils.listFromIterable;
import static com.example.dailytasks2.utils.CollectionUtils.streamFromIterable;

public class ManagerController implements Observer0 {

    Service0<Task> taskService0;
    Service0<Employee> employeeService0;
    Service0<Records> recordsService0;
    public static final String VIEW_NAME = "/ManagerView.fxml";
    public static final String VIEW_TITLE = "ManagerView";

    private FilteredTableWrapper<Records> ftw;

    @FXML
    private AnchorPane ManagerPane;

    @FXML
    private Button sendTaskButton;

    @FXML
    private TableColumn<Records, LocalTime> checkInColumn;

    @FXML
    private Button delteEmployeeButton;

    @FXML
    private TableColumn<Task, String> emailAdressTableColumn;

    @FXML
    private TableView<Records> employeeTable;

    @FXML
    private Text employeesText;

    @FXML
    private TableColumn<Records, String> firstNameColumn;

    @FXML
    private TableColumn<Records, String> lastNameColumn;

    @FXML
    private Button logOutButton;

    @FXML
    private TextField searchField;

    @FXML
    private Text searchText;

    @FXML
    private TableColumn<Task, TaskStatus> statusTableColumn;

    @FXML
    private TableColumn<Task, String> taskTitleTableColumn;

    @FXML
    private TextField taskDescription;

    @FXML
    private Text taskDescriptionText;

    @FXML
    private TableView<Task> tasksTable;

    @FXML
    private Text tasksText;


    ObservableList<Records> modelRecords = FXCollections.observableArrayList();
    ObservableList<Task> modelTasks = FXCollections.observableArrayList();


    public void initialize() {

        ftw = new FilteredTableWrapper<>(employeeTable);

        checkInColumn.setCellValueFactory(new PropertyValueFactory<Records, LocalTime>("log_in"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Records, String>("first_name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Records, String>("last_name"));

        employeeTable.setItems(modelRecords);

        taskTitleTableColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("title"));
        emailAdressTableColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("email_adress"));
        statusTableColumn.setCellValueFactory(new PropertyValueFactory<Task, TaskStatus>("task_status"));

        tasksTable.setItems(modelTasks);

        searchField.textProperty().addListener(o -> handleFilterOthers());

        sendTaskButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Records records = employeeTable.getSelectionModel().getSelectedItem();
                String description = taskDescription.getText();
                LocalTime time = LocalTime.of(18, 0);
                taskService0.add(new Task(1, description, description, time, records.getEmailAdress(), TaskStatus.ACCEPTED));
                loadTableData();
            }
        });
        delteEmployeeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Records records = employeeTable.getSelectionModel().getSelectedItem();
                List<Employee> employees = employeeService0.getAllEntities();
                for (Employee employee : employees)
                    if (Objects.equals(employee.getEmailAdress(), records.getEmailAdress())) {
                        employeeService0.delete(employee);
                        loadTableData();
                        break;
                    }
            }
        });
        loadTableData();
    }

    private void loadTableData() {
        Iterable<Records> elements = getRecordsOfActiveEmployees();
        ftw.setTableData(listFromIterable(elements));
    }

    private void handleFilterOthers() {
        Predicate<Records> p1 = n -> n.getLastName().startsWith(searchField.getText());
        Predicate<Records> p2 = n -> n.getFirstName().startsWith(searchField.getText());
        List<Records> otherUsers = getRecordsOfActiveEmployees();
        modelRecords.setAll(otherUsers
                .stream()
                .filter(p1.or(p2))
                .collect(Collectors.toList()));

    }


    public List<Records> getRecordsOfActiveEmployees() {
        List<Records> records = new ArrayList<>();
        List<Employee> employees = employeeService0.getAllEntities();
        for (Employee employee : employees)
            if (employee.getStatus() == EmployeeStatus.PREZENT)
                getTaskByEmail(employee, records);
        return records;
    }

    public List<Records> getTaskByEmail(Employee employee, List<Records> records) {
        List<Records> allRecords = recordsService0.getAllEntities();
        for (Records record : allRecords)
            if (Objects.equals(record.getEmailAdress(), employee.getEmailAdress()))
                records.add(record);
        return records;
    }

    public void setService(Service0<Employee> employeeService0) {
        this.employeeService0 = employeeService0;
        List<Records> records = getRecordsOfActiveEmployees();
        modelRecords.setAll(records);
    }


    @Override
    public void onChange(String key, Object oldValue, Object newValue) {
        if ("records".equals(key)) {
            loadTableData();
        }
    }
}
