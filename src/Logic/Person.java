package Logic;

import java.io.*;

public abstract class Person implements Serializable {
    protected String name;
    protected final long nationalId;

    public String getName() {
        return name;
    }

    public long getNationalId() {
        return nationalId;
    }

    public Person(String name, long nationalId) {
        this.name = name;
        this.nationalId = nationalId;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + '\'' + ", nationalId=" + nationalId + '}';
    }
}
