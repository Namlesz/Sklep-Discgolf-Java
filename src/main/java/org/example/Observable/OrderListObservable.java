package org.example.Observable;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderListObservable {
    private SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleDoubleProperty price = new SimpleDoubleProperty(0);
    private SimpleIntegerProperty count = new SimpleIntegerProperty(1);

    public OrderListObservable() {
    }

    public OrderListObservable(SimpleIntegerProperty id, SimpleStringProperty name, SimpleDoubleProperty price, SimpleIntegerProperty count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public int getCount() {
        return count.get();
    }

    public SimpleIntegerProperty countProperty() {
        return count;
    }

    public void setCount(int count) {
        this.count.set(count);
    }
}
