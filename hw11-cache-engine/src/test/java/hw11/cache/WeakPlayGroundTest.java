package hw11.cache;

import org.junit.Test;

import static org.junit.Assert.*;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.WeakHashMap;

public class WeakPlayGroundTest {
    @Test
    public void weakRefOnObjTest() {
        WeakReference<ArrayList<Integer>> ref;
        var list = new ArrayList<Integer>(10);

        ref = new WeakReference<>(list);
        System.gc();
        assertNotNull(ref.get());
        list = null;
        System.gc();
        assertNull(ref.get());
    }

    @Test
    public void weakHashMapWithOuterKeyReferenceTest() {
        var key = new Key();
        var map = new WeakHashMap<Key, String>();
        map.put(key, "key linked");

        map.put(new Key(), "without out side links");
        assertEquals(map.size(), 2);

        System.out.println(map);
        System.gc();

        System.out.println(map);
        assertEquals(map.size(), 1);

        key = null;
        System.gc();
        System.out.println(map);
        assertTrue(map.isEmpty());
    }
}


class Key {
}
