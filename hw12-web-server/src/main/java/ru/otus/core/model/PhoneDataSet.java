package ru.otus.core.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "phonedataset")
public class PhoneDataSet {
    private long id;
    private String number;

    @ManyToOne
    private transient User owner;

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) return false;
        PhoneDataSet obj = (PhoneDataSet) other;
        return Objects.equals(id, ((PhoneDataSet) other).getId()) &&
                Objects.equals(number, obj.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }

    @ManyToOne
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
