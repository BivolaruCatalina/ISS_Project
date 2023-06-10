package com.example.dailytasks2.service;

import com.example.dailytasks2.domain.Records;
import com.example.dailytasks2.repository.Repository0;

import java.util.List;

public class RecordsService implements Service0<Records>{

    Repository0<Integer, Records> recordsRepository0;

    public RecordsService(Repository0<Integer, Records> recordsRepository0) {
        this.recordsRepository0 = recordsRepository0;
    }

    @Override
    public void add(Records entity) {
        recordsRepository0.create(entity);
    }

    @Override
    public void delete(Records entity) {

    }

    @Override
    public void update(Records old_entity, Records new_entity) {

    }

    @Override
    public Records getEntityById(Integer s) {
        return recordsRepository0.findOne(s).get(0);
    }

    @Override
    public List<Records> getAllEntities() {
        return recordsRepository0.read();
    }

    @Override
    public boolean testEmail(String email) {
        return false;
    }
}
