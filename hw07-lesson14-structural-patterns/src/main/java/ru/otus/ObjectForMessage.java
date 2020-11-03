package ru.otus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObjectForMessage implements Cloneable {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    protected ObjectForMessage clone() {
        ObjectForMessage copy = new ObjectForMessage();
        List<String> copyData = new ArrayList<>(data);
        copy.setData(copyData);
        return copy;
    }

    @Override
    public String toString() {
        return "ObjectForMessage{" +
                "data=" + data.toString() +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ObjectForMessage msg = (ObjectForMessage) obj;
        return Objects.deepEquals(data, msg.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

}
