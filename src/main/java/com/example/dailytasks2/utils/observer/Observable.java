package com.example.dailytasks2.utils.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class Observable {
    final List<Observer0> observers = new ArrayList<>();

    public void addObserver(Observer0 observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer0 observer) {
        observers.remove(observer);
    }

    protected void change(String key, Object oldValue, Object newValue) {
        observers.forEach(observer -> observer.onChange(key, oldValue, newValue));
    }
}
