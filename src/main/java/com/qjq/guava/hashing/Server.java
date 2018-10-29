package com.qjq.guava.hashing;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
@Getter @Setter
public class Server {

    private String name;

    private Map<Entry,Entry> entries;

    public Server(String name) {
        this.name = name;
        entries=new HashMap<>();
    }

    public void put(Entry e) {
        entries.put(e, e);
    }

    public Entry get(Entry e) {
        return entries.get(e);
    }
}
