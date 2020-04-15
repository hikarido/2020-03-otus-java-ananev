package ru.otus.hw2;

import static org.junit.Assert.*;
import org.junit.Test;
import ru.otus.hw02.DIYArray;

import java.util.ArrayList;

public class DIYArrayTest {

    @Test
    public void defaultConstructorTestTest(){
        DIYArray<Integer> vals = new DIYArray<>();
    }

    @Test
    public void constructorWithSizeTest(){
        DIYArray<Integer> vals = new DIYArray<>(100);
    }

    //TODO of some collection constructor test

    @Test
    public void sizeAndCapacityOfDefaultConstructor(){
        DIYArray<Integer> vals = new DIYArray<>();
        assertEquals(vals.size(), 0);
        assertNotEquals(vals.getCapacity(), 0);
    }

    @Test
    public void sizeAndCapacityWithCapacityConstructor(){
        DIYArray<String> vals = new DIYArray<>(100);
        assertEquals(vals.getCapacity(), 100);
        assertEquals(vals.size(), 0);
    }

    @Test
    public void isEmptyOnDefaultConstruct(){
        DIYArray<Integer> vals = new DIYArray<>();
        assertTrue(vals.isEmpty());
    }

    @Test
    public void isEmptyOnCapacityConstruct(){
        DIYArray<Integer> vals = new DIYArray<>(1000);
        assertTrue(vals.isEmpty());
    }

    @Test
    public void toStringWithDeafultConstructorTest(){
        DIYArray<String> vals = new DIYArray<>();
        assertEquals("[  ]", vals.toString());
    }

    @Test
    public void emptyToStringWithCapacityConstructorTest(){
        DIYArray<String> vals = new DIYArray<>(5);
        assertEquals("[  ]", vals.toString());
    }

    @Test
    public void addOneTest(){
        DIYArray<String> vals = new DIYArray<>();
        vals.add("Hello");
        assertEquals( 1, vals.size());
        assertEquals("[ Hello ]", vals.toString());
    }

    @Test
    public void add10Test(){
        DIYArray<String> vals = new DIYArray<>();
        String expected = "[ ";
        for(int i = 0; i < 10; i++){
            expected += Integer.toString(i) + " ";
            vals.add(Integer.toString(i));
        }
        expected += "]";
        assertEquals(expected, vals.toString());
        assertEquals(10, vals.size());
        assertEquals(vals.size(), vals.getCapacity());

    }

    @Test
    public void add100Test(){
        DIYArray<String> vals = new DIYArray<>();
        String expected = "[ ";
        int len = 100;
        for(int i = 0; i < len; i++){
            expected += Integer.toString(i) + " ";
            vals.add(Integer.toString(i));
        }
        expected += "]";
        assertEquals(expected, vals.toString());
        assertEquals(len, vals.size());
        int initialCapacity = 10;
        int resultCapacity = (((initialCapacity * 2) * 2) * 2) * 2;

        assertEquals(resultCapacity, vals.getCapacity());

    }
}
