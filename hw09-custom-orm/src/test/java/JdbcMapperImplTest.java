import org.junit.*;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.mapper.hw9.impl.JdbcMapperImpl;
import ru.otus.jdbc.mapper.hw9.impl.User;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JdbcMapperImplTest {

    static DataSource dataSource;
    static SessionManagerJdbc sessionManager;

    @Before
    public void databaseUp() {
        dataSource = new DataSourceH2();
        sessionManager = new SessionManagerJdbc(dataSource);
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement(
                     "create table User(id long auto_increment, name varchar(50), age int(3));"
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
                     "drop table User;"
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
        var mapper = new JdbcMapperImpl<>(User.class, sessionManager);
    }

    @Test
    public void selectOfAbsentIdTest() {
        var mapper = new JdbcMapperImpl<>(User.class, sessionManager);
        assertEquals(null, mapper.findById(200, User.class));
    }

    @Test
    public void selectTest() {
        try (var connection = dataSource.getConnection();
             var pst = connection.prepareStatement(
                     "INSERT INTO User (name, age) VALUES ('Putin', 2036);"
             )) {
            pst.executeUpdate();
            connection.commit();
            var mapper = new JdbcMapperImpl<>(User.class, sessionManager);
            User pain = new User(1, "Putin", 2036);
            User selectedPain = mapper.findById(1, User.class);
            assertEquals(pain, selectedPain);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("Can't initialize database");
        }

    }

    @Test
    public void insertCallTest() {
        var mapper = new JdbcMapperImpl<>(User.class, sessionManager);

        User user = new User(-1, "Hero", 10);
        mapper.insert(user);
    }

    @Test
    public void insertThenSelectTest() {
        var mapper = new JdbcMapperImpl<>(User.class, sessionManager);

        User inserted = new User(-1, "hero", 10);
        mapper.insert(inserted);

        User selected = mapper.findById(1, User.class);
        assertEquals(selected, new User(1, "hero", 10));
    }

    @Test
    public void updateTest() {
        var mapper = new JdbcMapperImpl<>(User.class, sessionManager);

        User inserted = new User(-1, "hero", 10);
        mapper.insert(inserted);

        User master = new User(1, "Master", 11);
        mapper.update(master);
        User selected = mapper.findById(1, User.class);
        assertEquals(selected, master);
    }

    @Test
    public void insertOrUpdateTest() {
        JdbcMapperImpl<User> mapper = spy(new JdbcMapperImpl<>(User.class, sessionManager));
        User user = new User(-1, "A", 10);
        verify(mapper, times(0)).insert(user);
        mapper.insertOrUpdate(user);
        verify(mapper, times(1)).insert(user);
        User selected = mapper.findById(1, User.class);
        mapper.insertOrUpdate(selected);
        verify(mapper, times(1)).update(selected);
    }
}
