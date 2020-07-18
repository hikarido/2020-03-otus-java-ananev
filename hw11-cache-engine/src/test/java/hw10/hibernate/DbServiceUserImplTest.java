package hw10.hibernate;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.core.model.*;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class DbServiceUserImplTest {

    SessionFactory sessionFactory;
    SessionManagerHibernate manager;
    UserDaoHibernate dao;
    DbServiceUserImpl service;

    @Before
    public void init() {
        sessionFactory = HibernateUtils
                .buildSessionFactory(
                        "hibernate.cfg.xml",
                        User.class,
                        PhoneDataSet.class,
                        AddressDataSet.class
                );
        manager = new SessionManagerHibernate(sessionFactory);
        dao = new UserDaoHibernate(manager);
        service = new DbServiceUserImpl(dao);
    }

    @Test
    public void createTest() {
        var service = new DbServiceUserImpl(dao);
    }

    @Test
    public void addEmptyEmptyTest() {
        var user = new User();
        user.setName("Hero");

        long id = service.saveUser(user);

        var obtainedUser = service.getUser(id);
        assertTrue(obtainedUser.isPresent());
        assertEquals(obtainedUser.get().getName(), user.getName());
    }

    @Test
    public void addSeveralEmptyUsersTest() {
        var users = new ArrayList<User>();

        {
            var first = new User();
            first.setName("first");
            users.add(first);
        }

        {
            var second = new User();
            second.setName("second");
            users.add(second);
        }

        {
            var third = new User();
            third.setName("third");
            users.add(third);
        }

        users.forEach(user -> service.saveUser(user));

        {
            int index = 0;
            Optional<User> first = service.getUser(users.get(index).getId());
            assertNotNull(first);
            assertEquals(first.get().getName(), users.get(index).getName());
        }

        {
            int index = 1;
            Optional<User> second = service.getUser(users.get(index).getId());
            assertNotNull(second);
            assertEquals(second.get().getName(), users.get(index).getName());
        }

        {
            int index = 2;
            Optional<User> third = service.getUser(users.get(index).getId());
            assertNotNull(third);
            assertEquals(third.get().getName(), users.get(index).getName());
        }

    }

    @Test
    public void addUserWithStreetTest() {
        var first = new User();
        first.setName("first");
        var firstAddress = new AddressDataSet();
        firstAddress.setStreet("1");
        first.setAddress(firstAddress);
        long id = service.saveUser(first);
        Optional<User> obtained = service.getUser(id);
        assertNotNull(obtained);
        assertEquals(obtained.get().getName(), first.getName());
        assertEquals(obtained.get().getAddress().getStreet(), first.getAddress().getStreet());
    }

    @Test
    public void addSeveralUsersWithStreetTest() {
        var users = new ArrayList<User>();

        {
            var first = new User();
            first.setName("first");
            var firstAddress = new AddressDataSet();
            firstAddress.setStreet("1");
            first.setAddress(firstAddress);
            users.add(first);
        }

        {
            var second = new User();
            second.setName("second");
            var firstAddress = new AddressDataSet();
            firstAddress.setStreet("2");
            second.setAddress(firstAddress);
            users.add(second);
        }

        {
            var third = new User();
            third.setName("third");
            var firstAddress = new AddressDataSet();
            firstAddress.setStreet("3");
            third.setAddress(firstAddress);
            users.add(third);
        }

        users.forEach(user -> service.saveUser(user));

        {
            int index = 0;
            Optional<User> first = service.getUser(users.get(index).getId());
            assertNotNull(first);
            assertEquals(first.get().getName(), users.get(index).getName());
            assertEquals(first.get().getAddress().getStreet(), users.get(index).getAddress().getStreet());
        }

        {
            int index = 1;
            Optional<User> second = service.getUser(users.get(index).getId());
            assertNotNull(second);
            assertEquals(second.get().getName(), users.get(index).getName());
            assertEquals(second.get().getAddress().getStreet(), users.get(index).getAddress().getStreet());
        }

        {
            int index = 2;
            Optional<User> third = service.getUser(users.get(index).getId());
            assertNotNull(third);
            assertEquals(third.get().getName(), users.get(index).getName());
            assertEquals(third.get().getAddress().getStreet(), users.get(index).getAddress().getStreet());
        }

    }

    @Test
    public void addUserWithStreetAndPhone() {
        var user = new User();
        user.setName("Hero");
        var address = new AddressDataSet();
        address.setStreet("Street");
        user.setAddress(address);
        var phone = new PhoneDataSet();
        phone.setNumber("8800200600");
        user.addPhone(phone);

        long id = service.saveUser(user);
        Optional<User> result = service.getUser(id);
        assertTrue(result.isPresent());
        User obtained = result.get();
        assertEquals(user.getName(), obtained.getName());
        assertEquals(user.getPhones().size(), 1);
        assertEquals(user.getPhones().get(0), phone);
    }

    @Test
    public void speedTest(){
        int testLength = 1000;
        List<User> users = new ArrayList<>(testLength);
        for(int i = 0; i < testLength; i++){
            User user = new User();
            user.setName(String.valueOf(i));
            users.add(user);
        }

        List<Long> ids = new ArrayList<>(users.size());

        long start = System.currentTimeMillis();
        for(User user: users){
            ids.add(service.saveUser(user));
        }

        for(long id: ids){
            service.getUser(id);
        }
        long end = System.currentTimeMillis();
        System.out.println("Time consumption: " + (end - start) + " ms");
    }
}
