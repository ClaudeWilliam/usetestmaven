package com.qjq.guava;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Client {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int count = in.nextInt();
        List<child> list = Lists.newArrayList();
        for (int i = 1; i <= n; i++) {
            child c = new child(i, i);
            list.add(c);
        }
        while (list.size() >1) {
            List<child> data = list.stream().map(Client::addC).collect(Collectors.toList());
            System.out.println("data:"+data);
            System.out.println(data.get(0).getCount()%count);
            list = data.stream().filter((child) -> child.getCount()%count!=0).collect(Collectors.toList());
            System.out.println("list:"+list.size());
        }
        System.out.println("res:"+list);
        System.out.println(1%2);


    }

    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    public static class child {
        Integer name;
        Integer count;
    }
    public static child addC(child c){
        c.count=c.count+1;
        return c;
    }

}
