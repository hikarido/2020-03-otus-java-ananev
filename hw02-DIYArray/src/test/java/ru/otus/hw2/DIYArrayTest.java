package ru.otus.hw2;

import static org.junit.Assert.*;
import org.junit.Test;
import ru.otus.hw02.DIYArray;

import java.util.Collections;

public class DIYArrayTest {

    @Test
    public void createByDefaultConstructorTest(){
        DIYArray<Integer> vals = new DIYArray<>();
    }

    @Test
    public void createByCapacityConstructorTest(){
        DIYArray<Integer> vals = new DIYArray<>(100);
    }

    @Test
    public void sizeAndCapacityOfDefaultConstructorTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        assertEquals(vals.size(), 0);
        assertNotEquals(vals.getCapacity(), 0);
    }

    @Test
    public void sizeAndCapacityWithCapacityConstructorTest(){
        DIYArray<String> vals = new DIYArray<>(100);
        assertEquals(vals.getCapacity(), 100);
        assertEquals(vals.size(), 0);
    }

    @Test
    public void isEmptyOnDefaultConstructTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        assertTrue(vals.isEmpty());
    }

    @Test
    public void isEmptyOnCapacityConstructTest(){
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
    public void add_1_Test(){
        DIYArray<String> vals = new DIYArray<>();
        vals.add("Hello");
        assertEquals( 1, vals.size());
        assertEquals("[ Hello ]", vals.toString());
    }

    @Test
    public void add_10_Test(){
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
    public void add_100_Test(){
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
    public void add_1000_Test(){
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
    public void removeValueWhichDoesNotExistsTest(){
        DIYArray<String> vals = new DIYArray<>();
        vals.add("a");
        vals.add("aa");
        vals.add("aaa");

        assertFalse(vals.remove("b"));
        assertEquals("[ a aa aaa ]", vals.toString());
    }

    @Test
    public void removeLastOfLength_3_Test(){
        DIYArray<String> vals = new DIYArray<>();
        vals.add("a");
        vals.add("aa");
        vals.add("aaa");

        assertTrue(vals.remove("aaa"));
        assertEquals("[ a aa ]", vals.toString());
    }

    @Test
    public void removeMiddleOfLenght_3_Test(){
        DIYArray<String> vals = new DIYArray<>();
        vals.add("a");
        vals.add("aa");
        vals.add("aaa");

        assertTrue(vals.remove("aa"));
        assertEquals("[ a aaa ]", vals.toString());
    }

    @Test
    public void removeFirstOfLenght_3_Test(){
        DIYArray<String> vals = new DIYArray<>();
        vals.add("a");
        vals.add("aa");
        vals.add("aaa");

        assertTrue(vals.remove("a"));
        assertEquals("[ aa aaa ]", vals.toString());
    }

    @Test
    public void removeLastOfLenght_2_Test(){
        DIYArray<String> vals = new DIYArray<>();
        vals.add("a");
        vals.add("aa");

        assertTrue(vals.remove("aa"));
        assertEquals("[ a ]", vals.toString());
    }

    @Test
    public void removeFirstOfLenght_2_Test(){
        DIYArray<String> vals = new DIYArray<>();
        vals.add("a");
        vals.add("aa");

        assertTrue(vals.remove("a"));
        assertEquals("[ aa ]", vals.toString());
    }

    @Test
    public void goThroughLength_0_ForEachByIteratorTest(){
        DIYArray<String> vals = new DIYArray<>();
        String res = "";
        String expected = "";
        for(String val: vals){
            res += val;
        }

        assertEquals(expected, res);
    }

    @Test
    public void goThroughLength_1_ForEachByIteratorTest(){
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
    public void goThroughLength_2_ForEachByIteratorTest(){
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
    public void goThroughLength_3_ForEachByIteratorTest(){
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
    public void goThroughLength_1000_ForEachByIteratorTest(){
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

    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void getFrom_0_OfEmptyTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        vals.get(0);
    }

    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void getFrom_Minus1_OfEmptyTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        vals.get(-1);
    }

    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void getFrom_1_OfEmptyTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        vals.get(1);
    }

    @Test
    public void getFromZeroPositionFromNoEmptyTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        vals.add(42);
        vals.add(43);
        vals.add(44);
        assertEquals(Integer.valueOf(42), vals.get(0));
    }

    @Test
    public void getFromFirstPositionFromNoEmptyTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        vals.add(42);
        vals.add(43);
        vals.add(44);
        assertEquals(Integer.valueOf(43), vals.get(1));
    }

    @Test
    public void getFromSecondPositionFromNoEmptyTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        vals.add(42);
        vals.add(43);
        vals.add(44);
        assertEquals(Integer.valueOf(44), vals.get(2));
    }

    @Test
    public void getFrom_1000_PositionFromNoEmptyTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        for(int i = 1; i <= 1000; i++){
            vals.add(i);
        }
        assertEquals(Integer.valueOf(1000), vals.get(vals.size() - 1));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void setTo_0_OfEmptyTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        vals.set(0, 10);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void setTo_1_ofEmptyTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        vals.set(1, 10);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void setTo_2_ofEmptyTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        vals.set(2, 10);
    }

    @Test
    public void setTo_0_first_ofNonEmptyTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        vals.add(10);
        vals.add(20);
        vals.add(30);
        vals.add(40);
        int pos = 0;
        vals.set(pos, 100);
        assertEquals(Integer.valueOf(100), vals.get(pos));
    }

    @Test
    public void setTo_1_middle_ofNonEmptyTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        vals.add(10);
        vals.add(20);
        vals.add(30);
        vals.add(40);

        int pos = 1;
        vals.set(pos, 100);
        assertEquals(Integer.valueOf(100), vals.get(pos));
    }

    @Test
    public void setTo_2_middle_ofNonEmptyTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        vals.add(10);
        vals.add(20);
        vals.add(30);
        vals.add(40);

        int pos = 2;
        vals.set(pos, 100);
        assertEquals(Integer.valueOf(100), vals.get(pos));
    }

    @Test
    public void setTo_3_last_ofNonEmptyTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        vals.add(10);
        vals.add(20);
        vals.add(30);
        vals.add(40);

        int pos = 3;
        vals.set(pos, 100);
        assertEquals(Integer.valueOf(100), vals.get(pos));
    }

    @Test
    public void collectionsAddAllTest(){
        DIYArray<Integer> vals = new DIYArray<>();
        Integer[] filler = new Integer[100];
        for(int i = 0; i < filler.length; i++){
            filler[i] = i;
        }

        Collections.addAll(vals, filler);
        assertArrayEquals(filler, vals.toArray());
    }

    @Test
    public void collectionsCopyTest(){
        DIYArray<Integer> a = new DIYArray<>();
        DIYArray<Integer> b = new DIYArray<>();
        int[] array = new int[100];
        for(int i = 0; i < array.length; i++){
            a.add(i);
            b.add(0);
        }
        Collections.copy(a, b);
        assertArrayEquals(a.toArray(), b.toArray());
    }

    @Test
    public void collectionsSortTest(){
        DIYArray<Integer> strs = new DIYArray<>();
        DIYArray<Integer> sortedStrs = new DIYArray<>();

        for(int i = 0; i < 1000; i++){
            strs.add(Integer.valueOf(i));
            sortedStrs.add(Integer.valueOf(i));
        }

        Collections.shuffle(strs);
        Collections.sort(strs);
        assertArrayEquals(sortedStrs.toArray(), strs.toArray());
    }

}
