package hw10.hibernate;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import ru.otus.core.model.PhoneDataSet;

public class PhoneDataSetTest {

    @Test
    public void createTest() {
        var obj = new PhoneDataSet();
        assertEquals(obj.getId(), 0);
        assertNull(obj.getNumber());
    }

    @Test
    public void settersTest() {
        var obj = new PhoneDataSet();
        long id = 10;
        String number = "8 800 2000 600";
        obj.setId(id);
        obj.setNumber(number);
        assertEquals(obj.getId(), id);
        assertEquals(obj.getNumber(), number);
    }

}
