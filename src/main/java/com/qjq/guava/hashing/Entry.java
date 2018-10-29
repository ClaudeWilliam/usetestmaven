package com.qjq.guava.hashing;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * created by junqiang.qiu 2018/9/25 16:36
 * 用于存放Cahe中的对象,这里面
 */
@Setter @Getter @ToString
public class Entry {
    private String key;

    public Entry(String key) {
        this.key = key;
    }
}
