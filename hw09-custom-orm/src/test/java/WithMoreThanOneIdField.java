import ru.otus.jdbc.mapper.hw9.impl.Id;

public class WithMoreThanOneIdField {
    @Id
    public long id;

    @Id
    public long id2;
}
