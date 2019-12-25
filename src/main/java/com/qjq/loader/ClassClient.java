package com.qjq.loader;

public class ClassClient {

    public static void main(String[] args) {
        ClassLoader loader = ClassClient.class.getClassLoader();
        ClassLoader loader1 = loader.getParent();
        ClassLoader loader2 = loader1.getParent();
        System.out.println(loader);
        System.out.println(loader1);
        System.out.println(loader2);

    }
}
