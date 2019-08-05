package com.qjq.guava.spi;

import com.qjq.guava.spi.say.Say;
import com.qjq.guava.spi.sing.Sing;
import org.junit.Test;

import java.util.ServiceLoader;

public class client {

    /**
     * java 默认的spi
     */
    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ServiceLoader<Say> loader = ServiceLoader.load(Say.class, classLoader);

        for (Say say : loader) {
            say.sayHello();
        }
    }



}
