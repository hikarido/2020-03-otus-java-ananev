package hw10.hibernate;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import ru.otus.core.model.AddressDataSet;

public class AddressDataSetTest {
    @Test
    public void createEmptyTest() {
        var obj = new AddressDataSet();
        assertEquals(obj.getId(), 0);
        assertNull(obj.getStreet());
    }

    @Test
    public void settersTest() {
        var obj = new AddressDataSet();
        long id = 2;
        String street = "Street";
        obj.setId(id);
        obj.setStreet(street);
        assertEquals(obj.getId(), id);
        assertEquals(obj.getStreet(), street);
    }
}
