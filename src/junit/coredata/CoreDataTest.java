package junit.coredata;

import java.util.HashSet;
import java.util.Set;

import at.fhooe.im440.ladar.coredata.*;
import at.fhooe.im440.ladar.util.*;
import org.junit.*;

import static junit.framework.Assert.*;

public class CoreDataTest {

    private CoreData data;

    @Before
    public void setUp() throws Exception {
        data = Loader.lookup(CoreDataFactory.class).create();
    }

    @After
    public void tearDown() throws Exception {
        data = null;
    }

    @Test
    public void coreDataIsAvailable() {
        assertNotNull(data);
    }

    @Test
    public void hasObjectReturnsFalseOnNonExistingObject() {
        assertFalse(data.hasObject("foo"));
    }

    @Test
    public void hasObjectReturnsTrueOnExistingObject() {
        data.createObject("foo");
        assertTrue(data.hasObject("foo"));
    }

    @Test(expected = CoreDataException.class)
    public void createObjectThrowsExceptionOnExistingObject() {
        data.createObject("foo");
        data.createObject("foo");
    }

    @Test(expected = CoreDataException.class)
    public void getObjectThrowsExceptionOnNonExistingObject() {
        data.createObject("foo");
        data.getObject("bar");
    }

    @Test
    public void getObjectReturnsCorrectObject() {
        CoreDataObject foo = data.createObject("foo");
        CoreDataObject bar = data.createObject("bar");

        assertSame(foo, data.getObject("foo"));
        assertSame(bar, data.getObject("bar"));
    }

    @Test
    public void destroyObjectDoesNotThrowExceptionOnNonExistingObject() {
        CoreDataObject foo = data.createObject("foo");
        data.createObject("bar");

        data.destroyObject(foo);
        data.destroyObject(foo);
    }

    @Test
    public void hasObjectReturnsFalseOnDestroyedObject() {
        CoreDataObject foo = data.createObject("foo");
        data.createObject("bar");

        data.destroyObject(foo);
        assertFalse(data.hasObject("foo"));
    }

    @Test(expected = CoreDataException.class)
    public void getObjectThrowsExceptionOnDestroyedObject() {
        CoreDataObject foo = data.createObject("foo");
        data.createObject("bar");

        data.destroyObject(foo);
        data.getObject("foo");
    }

    @Test
    public void createObjectWorksOnDestroyedObject() {
        CoreDataObject foo = data.createObject("foo");
        data.createObject("bar");

        data.destroyObject(foo);
        data.createObject("foo");
    }

    @Test
    public void createObjectReturnsNewObjectOnDestroyedObject() {
        CoreDataObject foo1 = data.createObject("foo");
        data.createObject("bar");

        data.destroyObject(foo1);
        CoreDataObject foo2 = data.createObject("foo");

        assertNotSame(foo1, foo2);
    }

    @Test
    public void iteratingObjectsReturnsAllObjects() {
        CoreDataObject foo = data.createObject("foo");
        CoreDataObject bar = data.createObject("bar");
        CoreDataObject baz = data.createObject("baz");

        Set<CoreDataObject> s = new HashSet<>();

        s.add(foo);
        s.add(bar);
        s.add(baz);

        for (int i = 0; i < data.numberOfObjects(); ++i) {
            s.remove(data.getObject(i));
        }
        assertTrue(s.isEmpty());
    }

    @Test
    public void iteratingObjectsDoesNotReturnDestroyedObjects() {
        data.createObject("foo");
        CoreDataObject deleteMe = data.createObject("deleteMe");
        data.createObject("bar");
        data.createObject("baz");

        data.destroyObject(deleteMe);

        for (int i = 0; i < data.numberOfObjects(); ++i) {
            assertNotSame(deleteMe, data.getObject(i));
        }
    }

    @Test
    public void hasObjectReturnsFalseAfterDestroyAll() {
        data.createObject("foo");
        data.createObject("bar");
        data.createObject("baz");

        data.destroyAll();
        assertFalse(data.hasObject("foo"));
        assertFalse(data.hasObject("bar"));
        assertFalse(data.hasObject("baz"));
    }

    @Test
    public void numberOfObjectsReturnsZeroAfterDestroyAll() {
        data.createObject("foo");
        data.createObject("bar");
        data.createObject("baz");
        data.destroyAll();

        assertEquals(0, data.numberOfObjects());
    }

    @Test
    public void getParentReturnsNullOnNewObject() {
        CoreDataObject foo = data.createObject("foo");
        assertNull(foo.getParent());
    }

    @Test
    public void getIdReturnsCorrectValue() {
        CoreDataObject foo = data.createObject("foo");
        CoreDataObject bar = data.createObject("bar");

        assertEquals("foo", foo.getId());
        assertEquals("bar", bar.getId());
    }

    @Test
    public void getParentReturnsCorrectValueAfterSetParent() {
        CoreDataObject foo = data.createObject("foo");
        CoreDataObject bar = data.createObject("bar");

        bar.setParent(foo);
        assertSame(foo, bar.getParent());
    }

    @Test
    public void iteratingChildrenReturnsCorrectValues() {
        CoreDataObject foo = data.createObject("foo");
        CoreDataObject bar = data.createObject("bar");
        CoreDataObject baz = data.createObject("baz");

        bar.setParent(foo);
        baz.setParent(foo);

        Set<CoreDataObject> s = new HashSet<>();

        s.add(bar);
        s.add(baz);


        for (int i = 0; i < foo.numOfChildren(); ++i) {
            s.remove(foo.getChild(i));
        }
        assertTrue(s.isEmpty());
    }

    @Test
    public void getParentReturnsCorrectValuesAfterResettingParent() {
        CoreDataObject foo = data.createObject("foo");
        CoreDataObject bar = data.createObject("bar");
        CoreDataObject baz = data.createObject("baz");

        bar.setParent(foo);
        baz.setParent(foo);

        baz.setParent(bar);
        assertSame(bar, baz.getParent());
        assertSame(foo, bar.getParent());
    }

    @Test
    public void iteratingChildrenReturnsCorrectValuesAfterResettingParent() {
        CoreDataObject foo = data.createObject("foo");
        CoreDataObject bar = data.createObject("bar");
        CoreDataObject baz = data.createObject("baz");
        CoreDataObject qux = data.createObject("qux");


        bar.setParent(foo);
        baz.setParent(foo);
        qux.setParent(foo);
        baz.setParent(bar);

        Set<CoreDataObject> s = new HashSet<>();

        s.add(bar);
        s.add(qux);

        for (int i = 0; i < foo.numOfChildren(); ++i) {
            assertTrue(s.contains(foo.getChild(i)));
            s.remove(foo.getChild(i));
        }
        assertTrue(s.isEmpty());
    }

    @Test
    public void iteratingChildrenDoesNotReturnDestroyedChildren() {
        CoreDataObject foo = data.createObject("foo");
        CoreDataObject bar = data.createObject("bar");
        CoreDataObject baz = data.createObject("baz");
        CoreDataObject qux = data.createObject("qux");

        bar.setParent(foo);
        baz.setParent(foo);
        qux.setParent(foo);
        data.destroyObject(baz);

        Set<CoreDataObject> s = new HashSet<>();

        s.add(bar);
        s.add(qux);
        for (int i = 0; i < foo.numOfChildren(); ++i) {
            assertTrue(s.contains(foo.getChild(i)));
            s.remove(foo.getChild(i));
        }
        assertTrue(s.isEmpty());
    }

    @Test
    public void setParentWorksWithNullValue() {
        CoreDataObject foo = data.createObject("foo");
        CoreDataObject bar = data.createObject("bar");

        bar.setParent(null);
        foo.setParent(null);

        assertNull(bar.getParent());
    }


    @Test
    public void hasAttributeReturnsFalseOnNoneExistingAttribute() {
        CoreDataObject foo = data.createObject("foo");

        assertFalse(foo.hasAttribute("bar"));
    }

    @Test
    public void hasAttributeReturnsTrueOnExistingAttribute() {
        CoreDataObject foo = data.createObject("foo");
        foo.setAttribute("bar", data.createBool(true));
        assertTrue(foo.hasAttribute("bar"));
    }

    @Test
    public void getAttributeReturnsCorrectValue() {
        CoreDataObject foo = data.createObject("foo");
        foo.setAttribute("bar", data.createInt(42));
        foo.setAttribute("baz", data.createInt(43));

        assertEquals(42, foo.getAttribute("bar").toInt());
    }

    @Test
    public void setAttributeOverwritesPriveousAttribute() {
        CoreDataObject foo = data.createObject("foo");
        foo.setAttribute("bar", data.createInt(42));
        foo.setAttribute("bar", data.createInt(43));

        assertEquals(43, foo.getAttribute("bar").toInt());
    }

    @Test
    public void hasAttributeReturnsFalseAfterRemove() {
        CoreDataObject foo = data.createObject("foo");
        foo.setAttribute("bar", data.createInt(42));
        foo.setAttribute("baz", data.createInt(43));
        foo.removeAttribute("bar");

        assertFalse(foo.hasAttribute("bar"));
    }

    @Test
    public void removeAttributeWorksOnNonExistingAttribute() {
        CoreDataObject foo = data.createObject("foo");
        foo.removeAttribute("bar");
        foo.setAttribute("bar", data.createInt(42));
        foo.removeAttribute("bar");
        foo.removeAttribute("bar");
    }

    @Test(expected = CoreDataException.class)
    public void getAttributeThrowsExceptionOnNonExistingAttribute() {
        CoreDataObject foo = data.createObject("foo");
        foo.getAttribute("bar");
    }

    @Test(expected = CoreDataException.class)
    public void getAttributeThrowsExceptionOnRemovedAttribute() {
        CoreDataObject foo = data.createObject("foo");
        foo.setAttribute("bar", data.createInt(42));
        foo.removeAttribute("bar");
        foo.getAttribute("bar");
    }

    @Test
    public void iteratingAttributesReturnsAllAttributes() {
        CoreDataObject foo = data.createObject("foo");

        foo.setAttribute("bar", data.createInt(42));
        // we add the same attribute a couple of times in order to test
        // if the internal implementation of the iteration does not add
        // items to the list used for iteration more than once
        foo.setAttribute("bar", data.createInt(42));
        foo.setAttribute("bar", data.createInt(42));
        foo.setAttribute("bar", data.createInt(42));

        foo.setAttribute("baz", data.createInt(43));
        foo.setAttribute("qux", data.createInt(44));
        foo.setAttribute("quux", data.createInt(45));

        Set<String> s = new HashSet<>();
        s.add("bar");
        s.add("baz");
        s.add("qux");
        s.add("quux");
        for (int i = 0; i < foo.numOfAttributes(); ++i) {
            s.remove(foo.getAttribute(i));
        }
        assertTrue(s.isEmpty());
    }

    @Test
    public void iteratingAttributesDoesNotReturnRemovedAttributes() {
        CoreDataObject foo = data.createObject("foo");

        foo.setAttribute("bar", data.createInt(42));
        // we add the same attribute a couple of times in order to test
        // if the internal implementation of the iteration does not add
        // items to the list used for iteration more than once
        foo.setAttribute("bar", data.createInt(42));
        foo.setAttribute("bar", data.createInt(42));
        foo.setAttribute("bar", data.createInt(42));

        foo.setAttribute("baz", data.createInt(43));
        foo.setAttribute("qux", data.createInt(44));
        foo.setAttribute("quux", data.createInt(45));

        foo.removeAttribute("qux");
        Set<String> s = new HashSet<>();
        s.add("bar");
        s.add("baz");
        s.add("quux");

        for (int i = 0; i < foo.numOfAttributes(); ++i) {
            assertTrue(s.contains(foo.getAttribute(i)));
            s.remove(foo.getAttribute(i));
        }
        assertTrue(s.isEmpty());
    }

}