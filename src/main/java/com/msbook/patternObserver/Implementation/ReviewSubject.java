//package com.msbook.patternObserver.Implementation;
//
//import com.msbook.model.Review;
//import com.msbook.patternObserver.interfaces.Observer;
//import com.msbook.patternObserver.interfaces.Subject;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ReviewSubject implements Subject {
//
//    private Review review;
//
//    public ReviewSubject(Review review) {
//        this.review = review;
//    }
//
//    private List<Observer> observers = new ArrayList<>();
//
//    @Override
//    public void subscribe(Observer observer) {
//        this.observers.add(observer);
//    }
//
//    @Override
//    public void unsubscribe(Observer observer) {
//        this.observers.remove(observer);
//    }
//
//    @Override
//    public void notifyObservers() {
//        observers.forEach(observer -> observer.update(review));
//    }
//}
