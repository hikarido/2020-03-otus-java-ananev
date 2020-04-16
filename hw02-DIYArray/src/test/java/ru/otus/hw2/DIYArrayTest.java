package ru.otus.hw2;

import static org.junit.Assert.*;
import org.junit.Test;
import ru.otus.hw02.DIYArray;

import java.util.ArrayList;

public class DIYArrayTest {

    @Test
    public void createByDefaultConstructorTest(){
        DIYArray<Integer> vals = new DIYArray<>();
    }

    @Test
    public void createByCapacityConstructorTest(){
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
    public void add1Test(){
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

    @Test
    public void add1000Test(){
        DIYArray<String> vals = new DIYArray<>();
        String expected = "[ ";
        int len = 1000;
        for(int i = 0; i < len; i++){
            expected += Integer.toString(i) + " ";
            vals.add(Integer.toString(i));
        }
        expected += "]";
        assertEquals(expected, vals.toString());
        assertEquals(len, vals.size());
        int initialCapacity = 10;
        int resultCapacity = (((((((initialCapacity * 2) * 2) * 2) * 2) * 2) * 2) * 2);

        assertEquals(resultCapacity, vals.getCapacity());

    }

    @Test
    public void removeOneFromEmptyWithDefaultConstructorTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        assertFalse(vals.remove(Integer.valueOf(10)));
    }

    @Test
    public void removeOneFromEmptyWithCapacityConstructorTest(){
        DIYArray<Integer> vals = new DIYArray<>(100);
        assertFalse(vals.remove(Integer.valueOf(10)));
    }

    @Test
    public void removeValueWhichDoesNotExists(){
        DIYArray<String> vals = new DIYArray<>();
        vals.add("a");
        vals.add("aa");
        vals.add("aaa");

        assertFalse(vals.remove("b"));
        assertEquals("[ a aa aaa ]", vals.toString());
    }

    @Test
    public void removeLastOfLength3(){
        DIYArray<String> vals = new DIYArray<>();
        vals.add("a");
        vals.add("aa");
        vals.add("aaa");

        assertTrue(vals.remove("aaa"));
        assertEquals("[ a aa ]", vals.toString());
    }

    @Test
    public void removeMiddleOfLenght3(){
        DIYArray<String> vals = new DIYArray<>();
        vals.add("a");
        vals.add("aa");
        vals.add("aaa");

        assertTrue(vals.remove("aa"));
        assertEquals("[ a aaa ]", vals.toString());
    }

    @Test
    public void removeFirstOfLenght3(){
        DIYArray<String> vals = new DIYArray<>();
        vals.add("a");
        vals.add("aa");
        vals.add("aaa");

        assertTrue(vals.remove("a"));
        assertEquals("[ aa aaa ]", vals.toString());
    }

    @Test
    public void removeLastOfLenght2(){
        DIYArray<String> vals = new DIYArray<>();
        vals.add("a");
        vals.add("aa");

        assertTrue(vals.remove("aa"));
        assertEquals("[ a ]", vals.toString());
    }

    @Test
    public void removeFirstOfLenght2(){
        DIYArray<String> vals = new DIYArray<>();
        vals.add("a");
        vals.add("aa");

        assertTrue(vals.remove("a"));
        assertEquals("[ aa ]", vals.toString());
    }

    @Test
    public void goThroughLength0ForEachByIteratorTest(){
        DIYArray<String> vals = new DIYArray<>();
        String res = "";
        String expected = "";
        for(String val: vals){
            res += val;
        }

        assertEquals(expected, res);
    }


    @Test
    public void goThroughLength1ForEachByIteratorTest(){
        DIYArray<String> vals = new DIYArray<>();
        vals.add("a");
        String res = "";
        String expected = "a";
        for(String val: vals){
            res += val;
        }

        assertEquals(expected, res);
    }

    @Test
    public void goThroughLength2ForEachByIteratorTest(){
        DIYArray<String> vals = new DIYArray<>();
        vals.add("a");
        vals.add("bb");
        String res = "";
        String expected = "abb";
        for(String val: vals){
            res += val;
        }

        assertEquals(expected, res);
    }

    @Test
    public void goThroughLength3ForEachByIteratorTest(){
        DIYArray<String> vals = new DIYArray<>();
        vals.add("a");
        vals.add("bb");
        vals.add("ccc");
        String res = "";
        String expected = "abbccc";
        for(String val: vals){
            res += val;
        }

        assertEquals(expected, res);
    }

    @Test
    public void goThroughLength1000ForEachByIteratorTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        StringBuilder expected = new StringBuilder(3000);
        for(int i = 0; i < 1000; i++){
            vals.add(i);
            expected.append(i);
        }

        String exp = expected.toString();
        StringBuilder reality = new StringBuilder();
        for(Integer val: vals){
            reality.append(val);
        }

        String real = reality.toString();
        assertEquals(exp, real);

    }

}
