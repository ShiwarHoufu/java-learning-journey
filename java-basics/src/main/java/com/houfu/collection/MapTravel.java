package com.houfu.collection;

import java.util.HashMap;
import java.util.Map;

public class MapTravel {
    //三种方式遍历

    public static void main(String[] args) {
        //创建一个集合
        Map<String, Integer> map = new HashMap<>();
        map.put("张三", 18);
        map.put("李四", 18);
        map.put("王五", 20);
        System.out.println(map);

        //1.遍历keySet集合
        for (String key : map.keySet()) {
            Integer value = map.get(key);
            System.out.println(key + "=" + value);
        }

        //2.遍历entrySet集合
        //  Map底层不是直接存 key 和 value，而是把一对 key+value 封装成 Map.Entry 对象，
        //  entrySet() 就是取出所有 Entry 对象放到 Set 集合。
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + "=" + value);
        }

        //3.lambda表达式遍历
        map.forEach((key, value) -> System.out.println(key + "=" + value));

    }
}
