package com.qjq.guava;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public class TestMain {

    public static void main(String[] args) {
//        String str = "[12513800, 15771694, 17309985, 31245542, 35524109, 38232822, 39580272, 46778012, 51839627, 60889951, 66593260, 70940059, 75260124, 75422682, 77025966, 82582308, 83258336, 94036728, 100410370, 101863886, 102054105, 102333048]";
//        List<Long> userIdList = JSON.parseArray(str, Long.class);
//        List<List<Long>> partitionList = Lists.partition(userIdList, 10);
//        System.out.println(partitionList.size());
//        for (int i = 0; i < partitionList.size(); i++) {
//            List<Long> users = partitionList.get(i);
//            Set<Long> hasPushedUserSet = Sets.newHashSet(12513800L, 15771694L, 17309985L, 31245542L, 35524109L, 38232822L, 39580272L, 46778012L, 51839627L, 60889951L);
//            users.removeAll(hasPushedUserSet);
//            if (hasPushedUserSet.size() == 0) {
//                continue;
//            }
//            System.out.println(users.toString());
//        }
        List<List<Integer>> list = Lists.newArrayList();
        list.add(Lists.newArrayList(1,2,3));
        System.out.println(list.size());
        list.get(0).removeAll(Sets.newHashSet(1,2,3));
        System.out.println(list.size());

    }
}
