package gamearchitecture.engine;

import at.fhooe.im440.ladar.coredata.*;

import java.util.Map;

public class MyCoreDataMemento implements CoreDataMemento {

    protected Map<String, CoreDataObject> objects;

    public MyCoreDataMemento(Map<String, CoreDataObject> objects) {
        this.objects = objects;
    }

    public Map<String, CoreDataObject> getObjects() {
        return objects;
    }
}
