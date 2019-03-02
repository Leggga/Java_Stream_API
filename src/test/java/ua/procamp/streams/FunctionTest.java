package ua.procamp.streams;

import org.junit.Before;
import org.junit.Test;
import ua.procamp.streams.stream.AsIntStream;
import ua.procamp.streams.stream.IntStream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;


public class FunctionTest {

    private IntStream intStream;
    private int[] array;

    @Before
    public void init(){
        array = new int[20];

        for (int i = 0; i < array.length; i++) {
            array[i] = boundRandom(-10 , 20);
        }
        intStream = AsIntStream.of(array);
//        System.out.println(Arrays.toString(array));
    }

    @Test
    public void testFunctionMax(){
        System.out.println("testFunctionMax");
        int expResult = Arrays.stream(array).max().getAsInt();
        int result = intStream.max();
        assertEquals(expResult,result);
    }

    @Test
    public void testFunctionMin(){
        System.out.println("testFunctionMin");
        int expResult = Arrays.stream(array).min().getAsInt();
        int result = intStream.min();
        assertEquals(expResult,result);
    }

    @Test
    public void testFunctionAverage(){
        System.out.println("testFunctionAverage");
        double expResult = Arrays.stream(array).average().getAsDouble();
        double result = intStream.average();
        assertEquals(expResult,result,0.000000000000001);
    }

    @Test
    public void testFunctionCount(){
        System.out.println("testFunctionCount");
        long expResult = Arrays.stream(array).count();
        long result = intStream.count();
        assertEquals(expResult,result);
    }

    @Test
    public void testFunctionSum(){
        System.out.println("testFunctionCount");
        int expResult = Arrays.stream(array).sum();
        int result = intStream.sum();
        assertEquals(expResult,result);
    }

    @Test
    public void testFunctionToArray(){
        System.out.println("testFunctionToArray");
        int[] expResult = array;
        int[] result = intStream.toArray();
        assertArrayEquals(expResult,result);
    }

    @Test
    public void testFunctionFilter(){
        System.out.println("testFunctionFilter");
        int[] expResult = Arrays.stream(array).filter(x -> x % 2 == 0).toArray();
        int[] result = intStream.filter(x -> x % 2 == 0).toArray();
        assertArrayEquals(expResult,result);
    }

    @Test
    public void testFunctionForEach(){
        System.out.println("testFunctionForEach");
        StringBuilder sbExp = new StringBuilder();
        StringBuilder sbRes = new StringBuilder();

        Arrays.stream(array).forEach(sbExp::append);
        intStream.forEach(sbRes::append);
        assertEquals(sbExp.toString(),sbRes.toString());
    }

    @Test
    public void testFunctionFlatMap(){
        System.out.println("testFunctionFlatMap");
        List<Integer> listInt = Arrays.stream(array).boxed().collect(Collectors.toList());
        Integer[] exp = listInt.stream().flatMap(x -> Stream.of(x - 1, x + 1)).toArray(Integer[]::new);

        int[] intermArr = intStream.flatMap(x -> AsIntStream.of(x - 1, x + 1)).toArray();
        Integer[] resExp = Arrays.stream(intermArr).boxed().toArray(Integer[]::new);
        assertArrayEquals(exp,resExp);
    }

    @Test
    public void testFunctionReduce(){
        System.out.println("testFunctionReduce");
        int exp = Arrays.stream(array).filter(x -> x > 2).reduce(0, (x , y) -> x + y );
        int res = intStream.filter(x -> x > 2).reduce(0 , (x, y) -> x + y);
        assertEquals(exp,res);
    }

    private static int boundRandom(int min, int max){
        if (min > max)throw new IllegalArgumentException("min > max");

        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
}
