import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.mapper.hw9.impl.JdbcMapperImpl;
import ru.otus.jdbc.mapper.hw9.impl.Account;
import ru.otus.jdbc.mapper.hw9.impl.User;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class JdbcMapperImplOnAccountTest {
    static DataSource dataSource;
    static SessionManagerJdbc sessionManager;

    @Before
    public void databaseUp() {
        dataSource = new DataSourceH2();
        sessionManager = new SessionManagerJdbc(dataSource);
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement(
                     "create table Account(no bigint(20) NOT NULL auto_increment,  type varchar(255), rest number);"
             )) {
            pst.executeUpdate();
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't initialize database" + throwables.toString());
        }
        sessionManager.beginSession();
    }

    @After
    public void DropUsers() {

        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement(
                     "drop table Account;"
             )) {
            pst.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throw new RuntimeException("Can't initialize database" + throwables.toString());
        }
    }

    @AfterClass
    public static void databaseDown() {
        sessionManager.commitSession();
        sessionManager.close();
    }

    @Test
    public void ConstructorTest() {
        var mapper = new JdbcMapperImpl<>(Account.class, sessionManager);
    }

    @Test
    public void selectOfAbsentIdTest() {
        var mapper = new JdbcMapperImpl<>(Account.class, sessionManager);
        assertEquals(null, mapper.findById(200, Account.class));
    }

    @Test
    public void selectTest() {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement(
                     "INSERT INTO Account (type, rest) VALUES ('Primary', 1234567890);"
             )) {
            pst.executeUpdate();
            connection.commit();
            var mapper = new JdbcMapperImpl<>(Account.class, sessionManager);
            Account pain = new Account(1, "Primary", 1234567890);
            Account selectedPain = mapper.findById(1, Account.class);
            assertEquals(pain, selectedPain);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("Can't initialize database");
        }

    }

    @Test
    public void insertCallTest() {
        var mapper = new JdbcMapperImpl<>(Account.class, sessionManager);

        Account account = new Account(-1, "Hero", 10);
        mapper.insert(account);
    }

    @Test
    public void insertThenSelectTest() {
        var mapper = new JdbcMapperImpl<>(Account.class, sessionManager);

        Account inserted = new Account(-1, "hero", 10);
        mapper.insert(inserted);

        Account selected = mapper.findById(1, Account.class);
        assertEquals(selected, new Account(1, "hero", 10));
    }

    @Test
    public void updateTest() {
        var mapper = new JdbcMapperImpl<>(Account.class, sessionManager);

        Account inserted = new Account(-1, "hero", 10);
        mapper.insert(inserted);

        Account master = new Account(1, "Master", 11);
        mapper.update(master);
        Account selected = mapper.findById(1, Account.class);
        assertEquals(selected, master);
    }

    @Test
    public void insertOrUpdateTest() {
        JdbcMapperImpl<Account> mapper = spy(new JdbcMapperImpl<>(Account.class, sessionManager));
        Account user = new Account(-1, "A", 10);
        verify(mapper, times(0)).insert(user);
        mapper.insertOrUpdate(user);
        verify(mapper, times(1)).insert(user);
        Account selected = mapper.findById(1, Account.class);
        mapper.insertOrUpdate(selected);
        verify(mapper, times(1)).update(selected);
    }
}
