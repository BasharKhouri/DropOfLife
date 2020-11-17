package com.example.dropoflife.Interface;

public interface ISubject {
    void register(IObserver o);
    void unregister(IObserver o);
    void notifyObserver();
}
