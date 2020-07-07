package ru.otus.jdbc.mapper.hw9.impl;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return rest.equals(account.rest) &&
                no == account.no &&
                Objects.equals(type, account.type);
    }

    public Account() {
    }

    public Account(long no, String type, int rest) {
        this.no = no;
        this.type = type;
        this.rest = BigDecimal.valueOf(rest);
    }

    @Override
    public String toString() {
        return "Account{" +
                "type='" + type + '\'' +
                ", rest=" + rest +
                ", no=" + no +
                '}';
    }

    public String type;
    public BigDecimal rest;
    @Id
    public long no;
}
