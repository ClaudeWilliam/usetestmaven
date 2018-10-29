package com.qjq.guava;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LamadaTestClient {

    public String outFunction(Integer x) {
        System.out.println("this Function Integer:" + x);
        return "this Function Integer:" + x;
    }

    public void outConsumer(String s) {
        System.out.println("this Conumer String:" + s);
    }

    public Boolean outPredicate(Integer i) {
        System.out.println("i:" + i + (i > 0));
        return i > 0;
    }

    public Integer outSuppiler() {
        System.out.println("this need return value:" + 1000);
        return 1000;
    }

    public void outBiConsumer(Integer x, String str) {
        System.out.println("param:" + x + "string" + str);
    }

    public void outOK(Integer i) {
        System.out.println("say OK" + i);
    }

    //接收一个参数返回一个的 函数对象
    @Test
    public void testFunction() {
        Function<Integer, String> function = (x) -> outFunction(x);
        String string = function.apply(1000);
    }

    @Test
    public void testConsumer() {
        Consumer<String> consumer = (s) -> outConsumer(s);
        consumer.accept("what is that");
    }

    @Test
    public void testPredicate() {
        Predicate<Integer> predicate = (i) -> outPredicate(i);
        predicate.test(10);
    }

    @Test
    public void testSupplier() {
        Supplier<Integer> supplier = () -> outSuppiler();
        supplier.get();
    }

    @Test
    public void testBiConsumer() {
        BiConsumer<Integer, String> consumer = (x, str) -> outBiConsumer(x, str);
        consumer.accept(10, "wulili");

    }

    @Test
    public void testOK() {
        Consumer<Integer> consumer = LamadaTestClient.this::outOK;
        consumer.accept(1000);
    }

    @Test
    public void testMaxAndMin() {
        List<Integer> list = Lists.newArrayList(3, 5, 2, 9, 1);
        int maxInt = list.stream().max(Integer::compareTo).get();
        System.out.println(maxInt);
        int minInt = list.stream().min(Integer::compareTo).get();
        System.out.println(minInt);
    }

    @Test
    public void testReduce() {
        int sum = Stream.of(1, 2, 3, 4, 5, 6, 7).reduce(0, (a, b) -> a + b);
        System.out.println("reduce 求和" + sum);
        int sum1 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).reduce(1, (a, b) -> a * b);
        System.out.println("reduce 乘积" + sum1);
        int pSum = Stream.of(1, 2, 3, 4, 5, 6, 7).parallel().reduce(0, (a, b) -> a + b);
        System.out.println("reduce 并行求和" + pSum);
        int pSum1 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).parallel().reduce(1, (a, b) -> a * b);
        System.out.println("reduce 并行求和" + pSum1);
        int sumSize = Stream.of("Apple", "Banana", "Orange", "Pear").parallel().map(s -> s.length()).reduce(Integer::sum).get();
        System.out.println("mapReduce求和" + sumSize);
    }

    @Test
    public void testSort() {
        //从小到大排序
        List<Integer> list = Lists.newArrayList(3, 5, 1, 10, 8);
        List<Integer> sortedList = list.stream().sorted(Integer::compareTo).collect(Collectors.toList());
        System.out.println(sortedList);
        //从大到小排序
        List<Integer> sortedList1 = list.stream().sorted(Comparator.comparing(Integer::intValue).reversed()).collect(Collectors.toList());
        System.out.println(sortedList1);
        //从大到小排序,使用并行操作实现排序
        List<Integer> sortedListP = list.stream().parallel().sorted(Comparator.comparing(Integer::intValue).reversed()).collect(Collectors.toList());
        System.out.println(sortedListP);

    }

    @Test
    public void testStreamDistinct() {
        List<String> list = Lists.newArrayList("a", "b", "c", "d", "a", "d");
        List dis = list.stream().parallel().distinct().collect(Collectors.toList());
        System.out.println(dis);
    }

    @Test
    public void testStreamFilter() {
        List<Integer> list = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        List filters = list.stream().parallel().filter((x) -> x % 2 == 0).collect(Collectors.toList());
        System.out.println(filters);
    }

    @Test
    public void testStreamMap() {
        List<String> list = Lists.newArrayList("d", "a", "e", "a", "b", "c");
        List maps = list.stream().parallel().distinct().sorted(String::compareTo).map((x) -> x.hashCode()).collect(Collectors.toList());
        System.out.println(maps);
    }

    @Test
    public void testStreamFlatmap() {
        String poetry = "Where, before me, are the ages that have gone?\n" +
                "And where, behind me, are the coming generations?\n" +
                "I think of heaven and earth, without limit, without end,\n" +
                "And I am all alone and my tears fall down.";
        Stream<String> lines = Arrays.stream(poetry.split("\n"));
        List<String> arr = Arrays.stream(poetry.split("\n")).collect(Collectors.toList());
        System.out.println(arr);
        Stream<String> words = lines.flatMap(line -> Arrays.stream(line.split(" ")));
        List<String> arrw = Arrays.stream(poetry.split("\n")).flatMap(line -> Arrays.stream(line.split(" "))).collect(Collectors.toList());
        System.out.println(arrw);
        List<String> flatList = words.map(w -> {
            if (w.endsWith(",") || w.endsWith(".") || w.endsWith("?"))
                return w.substring(0, w.length() - 1).trim().toLowerCase();
            else
                return w.trim().toLowerCase();
        }).distinct().sorted().collect(Collectors.toList());
        System.out.println(flatList);

    }

    @Test
    public void testSteamLimit() {
        List<Integer> list = IntStream.range(0, 100).filter((x) -> x % 2 == 0).boxed().limit(20).collect(Collectors.toList());
        System.out.println(list);
    }

    @Test
    public void testSteamPeek() {
        IntStream.range(0, 100).filter((x) -> x % 2 == 1).boxed().limit(20).peek((x) -> System.out.println("hello world num:" + x)).collect(Collectors.toList());
    }

    @Test
    public void testSteamSkip() {
        List<Integer> list = IntStream.range(0, 100).skip(50).filter((x) -> x % 2 == 0).boxed().peek((x -> System.out.println("nice num:" + x))).sorted(Comparator.comparing(Integer::intValue).reversed()).collect(Collectors.toList());
        System.out.println(list);
    }

    @Test
    public void testSteamMatch(){
        System.out.println(Stream.of(1,2,3,4,5,6).allMatch((x)->x>0));//所有的匹配
        System.out.println(Stream.of(-1,2,3,4,5,6).allMatch((x)->x>0));
        System.out.println(Stream.of(-1,-2,-3,-4,-5).anyMatch((x)->x>0));//只要有一个匹配
        System.out.println(Stream.of(-1,-2,-3,-4,5).anyMatch((x)->x>0));
        System.out.println(Stream.of(-1,-2,-3,-4,5).noneMatch((x)->x>0));//不要任何匹配
        System.out.println(Stream.of(-1,-2,-3,-4,-5).noneMatch((x)->x>0));
    }
    @Test
    public void testStreamCount(){
        System.out.println( IntStream.range(0,100).filter((x)->x%2==0).boxed().count());
    }

    @Test
    public void testOptional() {
        System.out.println(Optional.empty());
    }

}
