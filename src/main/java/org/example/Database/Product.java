package org.example.Database;

import javax.persistence.*;

@Entity
@Table(name = "TOWARY")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
    @SequenceGenerator(name = "product_generator", sequenceName = "TOWARY_ID_SEQ", allocationSize = 1)
    private long id;

    @Column(name = "KOD_TOWARU")
    private String code;

    @Column(name = "NAZWA_TOWARU")
    private String name;

    @Column(name = "JEDNOSTKA_MIARY")
    private String chain;

    @Column(name = "EAN")
    private String ean;

    @Column(name = "VAT")
    private int vat;

    @Column(name = "OCENA")
    private double rating;

    @Column(name = "CENA")
    private double price;

    public Product() {
    }

    public Product(long id, String code, String name, String chain, String ean, int vat, double rating, double price) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.chain = chain;
        this.ean = ean;
        this.vat = vat;
        this.rating = rating;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", chain='" + chain + '\'' +
                ", ean='" + ean + '\'' +
                ", vat=" + vat +
                ", rating=" + rating +
                ", price=" + price +
                '}';
    }
}
