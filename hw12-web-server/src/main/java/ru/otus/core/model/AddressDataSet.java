package ru.otus.core.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "addressdataset")
public class AddressDataSet {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private long id;

    @Column(name = "street")
    private String street;

    public AddressDataSet() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass()) return false;
        AddressDataSet o = (AddressDataSet) other;
        return Objects.equals(this.id, o.getId())
                && Objects.equals(this.street, o.getStreet());
    }
}
