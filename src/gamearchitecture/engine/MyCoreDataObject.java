package gamearchitecture.engine;

import java.io.Serializable;
import java.util.*;

import at.fhooe.im440.ladar.coredata.*;

public class MyCoreDataObject implements CoreDataObject, Serializable {

    /*   =======================
     *         Properties
     *   =======================
     */
    protected String myId;
    protected CoreDataObject parent;
    protected Map<String, CoreDataObject> children;
    protected Map<String, Attribute> attributes;

    /*   =======================
     *    Standard Constructors
     *   =======================
     */

    public MyCoreDataObject(String id) {
        this(id, null);
    }

    public MyCoreDataObject(String id, CoreDataObject parent) {
        this.init();
        this.myId = id;
        this.parent = parent;
    }

    /**
     * Initialize Collections
     */
    protected void init() {
        children = new HashMap<String, CoreDataObject>();
        attributes = new HashMap<String, Attribute>();
    }



    /*   =======================
     *       Implementations
     *   =======================
     */

    @Override
    public String getId() {
        return this.myId;
    }

     /*   =====================================
     *       Implementations - Parents / Child
     *    =====================================
     */

    @Override
    public CoreDataObject getParent() {
        return this.parent;
    }

    @Override
    public void setParent(CoreDataObject parent) {
        // removes from its old parents child list "this" object
        if (this.parent != null)
            ((MyCoreDataObject) this.parent).children.remove(this.myId);

        this.parent = parent;

        // adds "this" to the parents child list
        if (parent != null)
            ((MyCoreDataObject) this.parent).children.put(this.myId, this);
    }

    @Override
    public int numOfChildren() {
        return this.children.size();
    }

    @Override
    public CoreDataObject getChild(int index) throws IndexOutOfBoundsException {
        List<CoreDataObject> l = new ArrayList<CoreDataObject>(children.values());
        return l.get(index);
    }


    /*   =====================================
    *       Implementations - Attributes
    *    =====================================
    */

    @Override
    public Attribute getAttribute(String id) throws CoreDataException {
        if (attributes.get(id) == null)
            throw new CoreDataException();

        return attributes.get(id);
    }

    @Override
    public CoreDataObject setAttribute(String id, Attribute attr) {
        attributes.put(id, attr);
        return this;
    }

    @Override
    public String getAttribute(int index) throws IndexOutOfBoundsException {

        List<Attribute> l = new ArrayList<>(attributes.values());
        Attribute a = l.get(index);

        Set<String> keys = attributes.keySet();
        for (String key : keys)
            if (attributes.get(key).equals(a))
                return key;

        throw new CoreDataException();
    }

    @Override
    public boolean hasAttribute(String id) {
        return attributes.containsKey(id);
    }

    @Override
    public void removeAttribute(String id) {
        attributes.remove(id);
    }

    @Override
    public int numOfAttributes() {
        return this.attributes.size();
    }

    @Override
    public String toString() {
        return "Object-id: " + myId;
    }
}
