package org.example.Observable;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;

public class HistoryObservable {
    private SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private SimpleStringProperty orderDate = new SimpleStringProperty("");
    private SimpleStringProperty state = new SimpleStringProperty("");

    public HistoryObservable() {
    }

    public HistoryObservable(SimpleIntegerProperty id, SimpleStringProperty orderDate, SimpleStringProperty state) {
        this.id = id;
        this.orderDate = orderDate;
        this.state = state;
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getOrderDate() {
        return orderDate.get();
    }

    public SimpleStringProperty orderDateProperty() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate.set(orderDate);
    }

    public String getState() {
        return state.get();
    }

    public SimpleStringProperty stateProperty() {
        return state;
    }

    public void setState(String state) {
        this.state.set(state);
    }
}
