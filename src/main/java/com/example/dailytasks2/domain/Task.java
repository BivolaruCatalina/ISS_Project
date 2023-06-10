package com.example.dailytasks2.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.example.dailytasks2.utils.StringUtils.matchesAny;

public class Task extends Entity<Integer>{

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private String title;
    private String description;
    private LocalTime deadline;
    private String emailAdress;
    private TaskStatus status=TaskStatus.ACCEPTED;

    public Task(Integer integer, String title, String description, LocalTime deadline, String emailAdress, TaskStatus status) {
        super(integer);
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.emailAdress = emailAdress;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalTime deadline) {
        this.deadline = deadline;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", emailAdress='" + emailAdress + '\'' +
                ", status=" + status +
                '}';
    }

    public boolean matches(String input) {
        return matchesAny(input, getId().toString(), getEmailAdress(), getStatus().toString());
    }
}
