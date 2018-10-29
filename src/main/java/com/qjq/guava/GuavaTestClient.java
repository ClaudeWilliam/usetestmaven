package com.qjq.guava;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.List;

public class GuavaTestClient {
    public static void main(String[] args) {
        List<String> names= Lists.newArrayList();
        names.add("11");
        names.add("22");
        names.add(null);
        names.add("33");
        names.add("44");
        names.add(null);
        names.add("55");
        String delimiter = ",";
        Joiner joiner = Joiner.on(delimiter);
        String excludeNullString = joiner.skipNulls().join(names);
        String NullString = joiner.useForNull("null").join(names);
        System.out.println(excludeNullString);
        System.out.println(NullString);
        List<String> integers=Lists.newArrayList(Splitter.on(delimiter).trimResults().split(excludeNullString));
        System.out.println(integers);

    }
}
