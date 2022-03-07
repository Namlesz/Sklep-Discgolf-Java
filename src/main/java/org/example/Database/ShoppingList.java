package org.example.Database;

import javax.persistence.*;

@Entity
@Table(name = "ZAMOWIENIA_POZYCJE")
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shoppingList_generator")
    @SequenceGenerator(name = "shoppingList_generator", sequenceName = "ZAMOWIENIA_POZYCJE_ID_SEQ", allocationSize = 1)
    private long id;

    @Column(name = "ILOSC")
    private Integer count;

    @Column(name = "WARTOSC")
    private double value;

    @Column(name = "ZAMOWIENIA_ID")
    private Integer order_id;

    @Column(name = "TOWARY_ID")
    private Integer product_id;

    public ShoppingList() {
    }

    public ShoppingList(int count, double value, int order_id, int product_id) {
        this.count = count;
        this.value = value;
        this.order_id = order_id;
        this.product_id = product_id;
    }

    public ShoppingList(long id, Integer count, double value, Integer order_id, Integer product_id) {
        this.id = id;
        this.count = count;
        this.value = value;
        this.order_id = order_id;
        this.product_id = product_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public double getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }
}
