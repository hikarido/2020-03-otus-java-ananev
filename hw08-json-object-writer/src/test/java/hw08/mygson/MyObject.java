package hw08.mygson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class MyObject{
    private int INT = 3;
    private int[] INTS = {1,2,3,4,5,6};

    private String STR = "str";
    private String[] STRS = {"str0", "str1"};

    private SubObject subObject = new SubObject();
    private SubObject[] arrayOfSubObjects = new SubObject[]{new SubObject(), new SubObject()};

    private int defaultInt;
    private String defaultString;
    private Object defaultReference;

    private Collection collection = new ArrayList(Arrays.asList(1,2,3,4,5,6));
    private int[][] nestedArray = new int[][]{{1,2,3}, {1,2,3}};

    private char aChar = 'a';
    private byte aByte = 0x1;
    private long aLong = 123L;
    private short aShort = 10000;
    private boolean aBoolean = false;
    private float aFloat = 1.24F;
    private double aDouble = 1.23;
}