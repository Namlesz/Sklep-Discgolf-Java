package org.example.Database;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "KLIENCI")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "KLIENCI_ID_SEQ", allocationSize = 1)
    private long id;

    @Column(name = "IMIE")
    private String name;

    @Column(name = "NAZWISKO")
    private String surname;

    @Column(name = "DATA_URODZENIA")
    private Date birthday;

    private String email;

    @Column(name = "MIASTO")
    private String town;

    @Column(name = "ULICA")
    private String street;

    @Column(name = "NR_DOMU")
    private String house_num;

    @Column(name = "NR_MIESZKANIA")
    private int apartment_num;

    private String jwt_token;

    public User() {
    }

    public User(long id, String name, String surname, Date birthday, String email,
                String town, String street, String house_num, int apartment_num, String jwt_token) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.email = email;
        this.town = town;
        this.street = street;
        this.house_num = house_num;
        this.apartment_num = apartment_num;
        this.jwt_token = jwt_token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse_num() {
        return house_num;
    }

    public void setHouse_num(String house_num) {
        this.house_num = house_num;
    }

    public int getApartment_num() {
        return apartment_num;
    }

    public void setApartment_num(int apartment_num) {
        this.apartment_num = apartment_num;
    }

    public String getJwt_token() {
        return jwt_token;
    }

    public void setJwt_token(String jwt_token) {
        this.jwt_token = jwt_token;
    }
}
