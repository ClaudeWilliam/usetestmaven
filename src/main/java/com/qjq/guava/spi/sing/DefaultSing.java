package com.qjq.guava.spi.sing;


import com.google.auto.service.AutoService;

@AutoService(Sing.class)
public class DefaultSing implements Sing {
    @Override
    public void singSong() {
        System.out.println("happy birthday to you");
    }
}
