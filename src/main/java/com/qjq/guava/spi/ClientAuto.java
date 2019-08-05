package com.qjq.guava.spi;

import com.qjq.guava.spi.sing.Sing;
import org.junit.Test;

import java.util.ServiceLoader;

public class ClientAuto {

    /**
     * google的自动注入spi
     */
    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        ServiceLoader<Sing> loader = ServiceLoader.load(Sing.class, classLoader);

        for (Sing sing : loader) {
            sing.singSong();
        }
    }

}
