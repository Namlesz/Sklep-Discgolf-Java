package org.example.Observable;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TournamentListObservable {
    SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    SimpleStringProperty date = new SimpleStringProperty("");
    SimpleStringProperty name = new SimpleStringProperty("");

    public TournamentListObservable() {
    }

    public TournamentListObservable(SimpleIntegerProperty id, SimpleStringProperty date, SimpleStringProperty name) {
        this.id = id;
        this.date = date;
        this.name = name;
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

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
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
}
