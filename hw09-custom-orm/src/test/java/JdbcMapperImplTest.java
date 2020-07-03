import org.junit.Test;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.mapper.hw9.impl.JdbcMapperImpl;
import ru.otus.jdbc.mapper.hw9.impl.User;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import static org.junit.Assert.*;

public class JdbcMapperImplTest {

    @Test
    public void create() {
        var dataSource = new DataSourceH2();
        var sessionManager = new SessionManagerJdbc(dataSource);
        var mapper = new JdbcMapperImpl<>(User.class, sessionManager);
    }
}
