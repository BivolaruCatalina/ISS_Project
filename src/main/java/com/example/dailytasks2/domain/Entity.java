package com.example.dailytasks2.domain;
public abstract class Entity <ID>{

    private ID id;

    public Entity(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public abstract boolean matches(String input);
}
