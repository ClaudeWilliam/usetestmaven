package com.qjq.guava.spi.say;

public class DefaultSay implements Say{

    @Override
    public void sayHello() {
        System.out.println("hello world");
    }
}
