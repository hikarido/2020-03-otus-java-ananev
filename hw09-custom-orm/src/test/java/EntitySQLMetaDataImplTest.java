import org.junit.Test;
import ru.otus.jdbc.mapper.hw9.impl.EntitySQLMetaDataImpl;

import static org.junit.Assert.*;

public class EntitySQLMetaDataImplTest {
    @Test
    public void createTest(){
        WithOneIdField obj = new WithOneIdField();
        new EntitySQLMetaDataImpl(obj.getClass());
    }

    @Test
    public void getSelectAllSql(){
        WithOneIdField obj = new WithOneIdField();
        var entity = new EntitySQLMetaDataImpl(obj.getClass());
        String expected = "SELECT * FROM WithOneIdField;";
        assertEquals(expected, entity.getSelectAllSql());
    }

    @Test
    public void getSelectByIdSql(){
        WithOneIdField obj = new WithOneIdField();
        var entity = new EntitySQLMetaDataImpl(obj.getClass());
        String expected = "SELECT * FROM WithOneIdField WHERE id = ?;";
        assertEquals(expected, entity.getSelectByIdSql());
    }

    @Test
    public void getInsertSql(){
        WithOneIdField obj = new WithOneIdField();
        var entity = new EntitySQLMetaDataImpl(obj.getClass());
        String expected = "INSERT INTO WithOneIdField (fname, sname) VALUES (?, ?);";
        assertEquals(expected, entity.getInsertSql());
    }

    @Test
    public void getUpdateSql(){
        WithOneIdField obj = new WithOneIdField();
        var entity = new EntitySQLMetaDataImpl(obj.getClass());
        String expected = "UPDATE WithOneIdField SET (fname = ?, sname = ?) WHERE ? = ?;";
        assertEquals(expected, entity.getUpdateSql());
    }
}
