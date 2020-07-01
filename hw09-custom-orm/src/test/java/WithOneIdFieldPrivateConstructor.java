import ru.otus.jdbc.mapper.hw9.impl.Id;

public class WithOneIdFieldPrivateConstructor {
    @Id
    public long id;
    public String fname;
    public String sname;

    private WithOneIdFieldPrivateConstructor() {

    }
}
