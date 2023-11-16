package org.maoqifan.observer;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Subject {
    private final static List<Observer> observers = new ArrayList<>();
    private int state;

    public void setState(int state){
        this.state = state;
        notifyObservers();
    }

    // 添加观察者
    public void attach(Observer observer){
        observers.add(observer);
    }

    // 移除观察者
    public void detach(Observer observer){
        observers.remove(observer);
    }


    // 通知所有观察者更新状态
    public void notifyObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
