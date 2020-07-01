import org.junit.Test;
import ru.otus.jdbc.mapper.hw9.impl.EntityClassMetaDataImpl;
import ru.otus.jdbc.mapper.hw9.impl.HaveNoPublicConstructor;

import static org.junit.Assert.*;

public class EntityClassMetaDataImplTest {
    @Test
    public void getClassNameOfIntegerTest(){
        assertEquals(Integer.class.getName(), new EntityClassMetaDataImpl(Integer.class).getName());
    }

    @Test
    public void getConstructorSuccessTest(){
        var metaData = new EntityClassMetaDataImpl(String.class);
        metaData.getConstructor();
    }

    @Test(expected = HaveNoPublicConstructor.class)
    public void getConstructorErroneousTest(){
        var metaData = new EntityClassMetaDataImpl(Integer.class);
        metaData.getConstructor();
    }
}
