import org.junit.Test;
import ru.otus.jdbc.mapper.hw9.impl.EntityClassMetaDataImpl;
import ru.otus.jdbc.mapper.hw9.impl.HaveNoIdField;
import ru.otus.jdbc.mapper.hw9.impl.HaveNoPublicConstructor;
import ru.otus.jdbc.mapper.hw9.impl.IdFieldMustBeOnlyOne;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class EntityClassMetaDataImplTest {
    @Test
    public void getClassNameOfIntegerTest() {
        assertEquals(WithOneIdField.class.getName(), new EntityClassMetaDataImpl<>(WithOneIdField.class).getName());
    }

    @Test
    public void getConstructorSuccessTest() {
        var metaData = new EntityClassMetaDataImpl<>(WithOneIdField.class);
        metaData.getConstructor();
    }

    @Test(expected = HaveNoPublicConstructor.class)
    public void getConstructorErroneousTest() {
        var metaData = new EntityClassMetaDataImpl<>(WithOneIdFieldPrivateConstructor.class);
        metaData.getConstructor();
    }

    @Test(expected = HaveNoIdField.class)
    public void getIdFieldWhereItIsNotLocated() {
        var obj = new WithoutIdField();
        new EntityClassMetaDataImpl<>(obj.getClass());
    }

    @Test(expected = IdFieldMustBeOnlyOne.class)
    public void multipleIdFieldsTest() {
        var obj = new WithMoreThanOneIdField();
        new EntityClassMetaDataImpl<>(obj.getClass());
    }

    @Test
    public void getIdFieldName() {
        String expected = "id";
        String real = new EntityClassMetaDataImpl<>(WithOneIdField.class).getIdField().getName();
        assertEquals(expected, real);
    }

    @Test
    public void getAllFields() {
        var obj = new WithOneIdField();
        List<String> expectedFieldsNames = Arrays.asList("fname", "sname", "id");
        var meta = new EntityClassMetaDataImpl<>(obj.getClass());
        List<Field> fields = meta.getAllFields();
        List<String> realFieldsNames = fields.stream().map(Field::getName).collect(Collectors.toList());
        expectedFieldsNames.sort(String::compareTo);
        realFieldsNames.sort(String::compareTo);
        assertArrayEquals(expectedFieldsNames.toArray(), realFieldsNames.toArray());
    }

    @Test
    public void getAllFieldsWithoutIdAnnotatedField() {
        var obj = new WithOneIdField();
        List<String> expectedFieldsNames = Arrays.asList("fname", "sname");
        var meta = new EntityClassMetaDataImpl<>(obj.getClass());
        List<Field> fields = meta.getFieldsWithoutId();
        List<String> realFieldsNames = fields.stream().map(Field::getName).collect(Collectors.toList());
        expectedFieldsNames.sort(String::compareTo);
        realFieldsNames.sort(String::compareTo);
        assertArrayEquals(expectedFieldsNames.toArray(), realFieldsNames.toArray());
    }
}
