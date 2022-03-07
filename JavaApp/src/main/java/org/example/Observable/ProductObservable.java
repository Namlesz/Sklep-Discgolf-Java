package org.example.Observable;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProductObservable {
    private SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private SimpleStringProperty code = new SimpleStringProperty("");
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty chain = new SimpleStringProperty("");
    private SimpleStringProperty ean = new SimpleStringProperty("");
    private SimpleIntegerProperty vat = new SimpleIntegerProperty(0);
    private SimpleDoubleProperty rating = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty price = new SimpleDoubleProperty(0);

    public ProductObservable() {
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

    public ProductObservable(SimpleIntegerProperty id, SimpleStringProperty code, SimpleStringProperty name, SimpleStringProperty chain, SimpleStringProperty ean, SimpleIntegerProperty vat, SimpleDoubleProperty rating, SimpleDoubleProperty price) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.chain = chain;
        this.ean = ean;
        this.vat = vat;
        this.rating = rating;
        this.price = price;
    }

    public String getCode() {
        return code.get();
    }

    public SimpleStringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
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

    public String getChain() {
        return chain.get();
    }

    public SimpleStringProperty chainProperty() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain.set(chain);
    }

    public String getEan() {
        return ean.get();
    }

    public SimpleStringProperty eanProperty() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean.set(ean);
    }

    public int getVat() {
        return vat.get();
    }

    public SimpleIntegerProperty vatProperty() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat.set(vat);
    }

    public double getRating() {
        return rating.get();
    }

    public SimpleDoubleProperty ratingProperty() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating.set(rating);
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
}
