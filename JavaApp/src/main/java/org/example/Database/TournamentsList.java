package org.example.Database;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TURNIEJE_LISTA")
public class TournamentsList {
    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "KLIENCI_ID")
    private int user_id;

    @Column(name = "TURNIEJE_ID")
    private int tour_id;

    @Column(name = "NAZWA")
    private String tour_name;

    @Column(name = "DATA")
    private Date tour_date;

    public TournamentsList() {
    }

    public TournamentsList(int id, int user_id, int tour_id, String tour_name, Date tour_date) {
        this.id = id;
        this.user_id = user_id;
        this.tour_id = tour_id;
        this.tour_name = tour_name;
        this.tour_date = tour_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTour_id() {
        return tour_id;
    }

    public void setTour_id(int tour_id) {
        this.tour_id = tour_id;
    }

    public String getTour_name() {
        return tour_name;
    }

    public void setTour_name(String tour_name) {
        this.tour_name = tour_name;
    }

    public Date getTour_date() {
        return tour_date;
    }

    public void setTour_date(Date tour_date) {
        this.tour_date = tour_date;
    }
}

