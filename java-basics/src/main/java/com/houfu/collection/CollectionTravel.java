package com.houfu.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

public class CollectionTravel {

    public static void main(String[] args) {
        //创建一个集合
        Collection<String> names = new ArrayList<>();
        names.add("张三");
        names.add("李四");
        names.add("王五");

        //遍历方式一：迭代器遍历
        Iterator<String> iterator = names.iterator();
        while (iterator.hasNext()) {     //判断当前位置是否有元素
            System.out.println(iterator.next());
        }

        //遍历方式二：增强for循环遍历
        for (String name : names) {
            System.out.println(name);
        }

        //遍历方式三：lambda表达式遍历
        names.forEach(name -> System.out.println(name));
        /*
        names.forEach(new Consumer<String>() {
            @Override
            public void accept(String name) {
                System.out.println(name);
            }
        });
        */
    }
}
