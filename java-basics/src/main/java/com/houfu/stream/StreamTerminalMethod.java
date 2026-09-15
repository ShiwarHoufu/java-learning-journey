package com.houfu.stream;

import java.util.*;
import java.util.stream.Collectors;

public class StreamTerminalMethod {
    //目标：演示stream流常用的终结方法（触发执行，之后流不可再用）
    public static void main(String[] args) {
        List<Integer> list = List.of(5, 3, 8, 1, 8, 6, 3, 9, 2);  //创建不可变集合，不能 add /remove/set 修改元素

        //1.forEach：遍历元素
        list.stream().forEach(n -> System.out.print(n + " "));
        System.out.println();

        //2.count：统计元素个数
        long count = list.stream().count();
        System.out.println("元素个数：" + count);

        //3.max / min：求最大最小值（需要比较器，返回Optional）
        Optional<Integer> max = list.stream().max(Integer::compareTo);
        // Optional<Integer> max = list.stream().max((a, b) -> a.compareTo(b));
        Optional<Integer> min = list.stream().min(Integer::compareTo);
        System.out.println("最大值：" + max.get() + "，最小值：" + min.get());

        //4.collect：收集成List
        List<Integer> filtered = list.stream().filter(n -> n > 4).collect(Collectors.toList());
        System.out.println("收集成List：" + filtered);

        //5.collect：收集成Set（自动去重）
        Set<Integer> set = list.stream().collect(Collectors.toSet());
        System.out.println("收集成Set：" + set);

        //6.collect：收集成Map（键，值）
        Map<Integer, String> map = list.stream()
                .distinct()
                .collect(Collectors.toMap(n -> n, n -> "值" + n));
        System.out.println("收集成Map：" + map);

        //7.collect：拼接字符串
        String joined = list.stream().map(String::valueOf).collect(Collectors.joining(", ", "[", "]"));
        System.out.println("拼接字符串：" + joined);

        //8.toArray：收集成数组
        Integer[] arr = list.stream().toArray(Integer[]::new);
        System.out.println("数组：" + Arrays.toString(arr));

        //9.reduce：归并计算（求和）
        int sum = list.stream().reduce(0, Integer::sum);
        System.out.println("reduce求和：" + sum);

        //10.match系列：判断元素是否匹配
        boolean anyGt8 = list.stream().anyMatch(n -> n > 8);   // 任一匹配
        boolean allGt0 = list.stream().allMatch(n -> n > 0);   // 全部匹配
        boolean noneLt0 = list.stream().noneMatch(n -> n < 0); // 都不匹配
        System.out.println("有大于8的：" + anyGt8 + "，全部大于0：" + allGt0 + "，没有负数：" + noneLt0);

        //11.findFirst / findAny：获取元素
        Optional<Integer> first = list.stream().filter(n -> n > 4).findFirst();
        System.out.println("第一个大于4的：" + first.orElse(-1));

    }
}
