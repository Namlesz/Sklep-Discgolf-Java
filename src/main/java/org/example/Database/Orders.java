package org.example.Database;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ZAMOWIENIA")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_generator")
    @SequenceGenerator(name = "orders_generator", sequenceName = "ZAMOWIENIA_ID_SEQ", allocationSize = 1)
    private long id;

    @Column(name = "DATA_ZAMOWIENA")
    private Date date;

    @Column(name = "KLIENCI_ID")
    private int user_id;

    @Column(name = "STATUS")
    private String state;

    public Orders() {
    }

    public Orders(Date date, int user_id, String state) {
        this.date = date;
        this.user_id = user_id;
        this.state = state;
    }

    public Orders(long id, Date date, int user_id, String state) {
        this.id = id;
        this.date = date;
        this.user_id = user_id;
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
