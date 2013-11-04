package gamearchitecture.engine;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import at.fhooe.im440.ladar.coredata.*;


public class MyCoreData implements CoreData {

    /*
     *   Properties
     */

    protected Map<String, CoreDataObject> objects = new HashMap<String, CoreDataObject>();

    /*   =======================
     *          Methods
     *   =======================
     */
    @Override
    public CoreDataObject createObject(String id) throws CoreDataException {
        if (objects.get(id) != null)
            throw new CoreDataException();

        MyCoreDataObject newObject = new MyCoreDataObject(id);
        objects.put(id, newObject);
        return newObject;
    }

    @Override
    public void destroyObject(CoreDataObject cdo) {
        objects.remove(cdo.getId());
    }

    @Override
    public void destroyAll() {
        objects.clear();
    }

    @Override
    public CoreDataObject getObject(String id) throws CoreDataException {
        CoreDataObject o = objects.get(id);
        if (o == null)
            throw new CoreDataException();
        return o;
    }

    @Override
    public boolean hasObject(String id) {
        return objects.containsKey(id);
    }

    @Override
    public int numberOfObjects() {
        return objects.size();
    }

    @Override
    public CoreDataObject getObject(int index) throws IndexOutOfBoundsException {
        CoreDataObject o = new ArrayList<>(objects.values()).get(index);
        if (o == null)
            throw new CoreDataException();
        return o;
    }

    @Override
    public CoreDataMemento saveToMemento() {
        CoreDataMemento coreDataMemento = new MyCoreDataMemento(this.objects);

        return coreDataMemento;
    }

    @Override
    public void restoreFromMemento(CoreDataMemento memento)
            throws CoreDataException {
        this.objects = ((MyCoreDataMemento) memento).getObjects();
    }

    @Override
    public Attribute createBool(boolean value) {
        return new MyAttribute(Attribute.Type.BOOL, String.valueOf(value));
    }

    @Override
    public Attribute createInt(int value) {
        return new MyAttribute(Attribute.Type.INT, String.valueOf(value));
    }

    @Override
    public Attribute createString(String value) {
        return new MyAttribute(Attribute.Type.STRING, value);
    }

    @Override
    public Attribute createScript(String value) {
        return new MyAttribute(Attribute.Type.SCRIPT, value);
    }
}
