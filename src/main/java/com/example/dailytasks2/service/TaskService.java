package com.example.dailytasks2.service;

import com.example.dailytasks2.domain.Employee;
import com.example.dailytasks2.domain.Task;
import com.example.dailytasks2.domain.Task;
import com.example.dailytasks2.repository.Repository0;
import com.example.dailytasks2.utils.observer.Observable;

import java.util.List;
import java.util.Objects;

public class TaskService extends Observable implements Service0<Task> {
    
    Repository0<Integer, Task> taskRepository0;

    public TaskService(Repository0<Integer, Task> taskRepository0) {
        this.taskRepository0 = taskRepository0;
    }

    private void testExistance(Task entity) throws Exception{
        boolean ok = false;
        List<Task> tasks = taskRepository0.read();
        for (Task task : tasks)
            if(Objects.equals(task.getId(), entity.getId()))
            {
                ok = true;
                break;
            }
        if(!ok)
            throw new Exception("Task-ul nu exista!");
    }
    
    @Override
    public void add(Task entity) {
        taskRepository0.create(entity);
        change("tasks", null, entity);

    }

    @Override
    public void delete(Task entity) {
        taskRepository0.delete(entity.getId());
        change("tasks", entity, entity);

    }

    @Override
    public void update(Task old_entity, Task new_entity) {
        try {
            testExistance(old_entity);
            taskRepository0.update(old_entity, new_entity);
            change("tasks", old_entity, new_entity);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Task getEntityById(Integer s) {
        return taskRepository0.findOne(s).get(0);
    }

    @Override
    public List<Task> getAllEntities() {
        return taskRepository0.read();
    }

    public boolean testEmail(String email){
        return false;
    }

}
