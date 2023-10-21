package com.msbook.patternObserver.interfaces;

public interface Subject{

    public void subscribe(Observer observer);

    public void unsubscribe(Observer observer);

    public void notifyObservers(Long id);
}
