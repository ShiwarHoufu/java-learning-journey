package com.houfu.stream;

import java.util.*;
import java.util.stream.Stream;

public class GetStream {
    //目标：获取stream流
    public static void main(String[] args) {
        //1.获取单列集合的stream流
        Collection<String> list = new ArrayList<>();
        Stream<String> s1 = list.stream();

        //2.获取Map集合的stream流
        Map<String, Integer> map = new HashMap<>();
        //获取键值对流
        Stream<Map.Entry<String, Integer>> s2 = map.entrySet().stream();
        //获取键流
        Stream<String> s3 = map.keySet().stream();
        //获取值流
        Stream<Integer> s4 = map.values().stream();

        //3.获取数组的stream流
        Integer[] arr = {1, 2, 3, 4, 5};
        Stream<Integer> s5 = Arrays.stream(arr);
        Stream<Integer> s6 = Stream.of(arr);
    }
}
