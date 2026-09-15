package com.houfu.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamMethod {
    //目标：演示stream流常用的中间方法（返回新stream，可链式调用）
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(List.of(5, 3, 8, 1, 8, 6, 3, 9, 2));

        //1.filter：过滤，保留满足条件的元素
        list.stream().filter(n -> n > 4).forEach(n -> System.out.print(n + " "));
        System.out.println();

        //2.map：映射，把元素转换成另一种形式
        list.stream().map(n -> "编号-" + n).limit(3).forEach(s -> System.out.print(s + " "));
        System.out.println();

        //3.distinct：去重（依赖equals）
        list.stream().distinct().forEach(n -> System.out.print(n + " "));
        System.out.println();

        //4.sorted：排序（自然排序）
        list.stream().sorted().forEach(n -> System.out.print(n + " "));
        System.out.println();
        //sorted：自定义比较器（降序）
        list.stream().sorted((a, b) -> b - a).forEach(n -> System.out.print(n + " "));
        System.out.println();

        //5.limit：取前N个
        list.stream().limit(3).forEach(n -> System.out.print(n + " "));
        System.out.println();

        //6.skip：跳过前N个
        list.stream().skip(6).forEach(n -> System.out.print(n + " "));
        System.out.println();

        //7.limit + skip：分页效果（第2页，每页3条）
        list.stream().skip(3).limit(3).forEach(n -> System.out.print(n + " "));
        System.out.println();

        //8.flatMap：把每个元素打平成流，常用于合并嵌套结构
        Stream<List<Integer>> nested = Stream.of(List.of(1, 2), List.of(3, 4), List.of(5));
        nested.flatMap(List::stream).forEach(n -> System.out.print(n + " "));
        System.out.println();

        //9.mapToInt / mapToDouble：映射为基本类型流，方便统计
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        System.out.println("总和：" + sum);
        double avg = list.stream().mapToInt(Integer::intValue).average().orElse(0);
        System.out.println("平均值：" + avg);

        //10.concat：合并两个流（静态方法）
        Stream<String> a = Stream.of("A", "B");
        Stream<String> b = Stream.of("C", "D");
        Stream.concat(a, b).forEach(s -> System.out.print(s + " "));
        System.out.println();

        //11.链式调用综合演示：过滤 -> 去重 -> 排序 -> 跳过 -> 收集
        List<Integer> result = list.stream()
                .filter(n -> n > 2)
                .distinct()
                .sorted()
                .skip(1)
                .limit(5)
                .collect(Collectors.toList());
        System.out.println("链式调用结果：" + result);
    }
}
