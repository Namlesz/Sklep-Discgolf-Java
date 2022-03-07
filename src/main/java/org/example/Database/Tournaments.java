package org.example.Database;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TURNIEJE")
public class Tournaments {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Tournaments_generator")
    @SequenceGenerator(name = "Tournaments_generator", sequenceName = "TURNIEJE_ID_SEQ", allocationSize = 1)
    private int id;

    @Column(name = "NAZWA")
    private String name;

    @Column(name = "DATA")
    private Date date;

    public Tournaments() {}

    public Tournaments(int id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
