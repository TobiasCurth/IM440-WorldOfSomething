package gamearchitecture.engine;

import at.fhooe.im440.ladar.coredata.*;

import java.io.Serializable;

public class MyAttribute implements Attribute, Serializable {

    private Type type;
    private String value;


    public MyAttribute(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public boolean toBool() throws CoreDataException {
        if (this.type != Type.BOOL)
            throw new CoreDataException();
        return Boolean.parseBoolean(value);
    }

    @Override
    public int toInt() throws CoreDataException {
        if (this.type != Type.INT)
            throw new CoreDataException();
        return Integer.parseInt(value);
    }

    @Override
    public String toScript() throws CoreDataException {
        if (this.type != Type.SCRIPT)
            throw new CoreDataException();
        return value;
    }

    @Override
    public String toString() {
       return value;
    }
}
