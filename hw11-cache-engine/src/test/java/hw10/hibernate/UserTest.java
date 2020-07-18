package hw10.hibernate;

import org.junit.Test;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.model.User;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void createDefaultTest() {
        var user = new User();
        assertEquals(user.getId(), 0);
        assertNull(user.getAddress());
        assertNull(user.getName());
        assertNotNull(user.getPhones());
    }

    @Test
    public void createParametrizedTest() {
        long id = 10;
        String name = "name";
        var user = new User(id, name);
        assertEquals(user.getId(), id);
        assertNull(user.getAddress());
        assertEquals(user.getName(), name);
        assertNotNull(user.getPhones());
    }

    @Test
    public void settersTest() {
        var user = new User();
        long id = 100;
        var address = new AddressDataSet();
        var phone = new PhoneDataSet();
        var name = "name";

        user.setAddress(address);
        user.setId(id);
        user.setName(name);
        user.addPhone(phone);

        assertEquals(user.getId(), id);
        assertEquals(user.getAddress(), address);
        assertEquals(user.getName(), name);
        assertEquals(user.getPhones().get(0), phone);
    }

    @Test
    public void removePhoneTest() {
        var obj = new User();
        var phone = new PhoneDataSet();
        obj.addPhone(phone);
        assertEquals(obj.getPhones().get(0), phone);

        obj.removePhone(phone);
        assertEquals(obj.getPhones().size(), 0);
    }
}
