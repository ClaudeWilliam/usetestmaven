package com.qjq.guava;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Method;
import java.util.Set;

public class RFMain {
    public static void main(String[] args) {
        ConfigurationBuilder config = new ConfigurationBuilder();
        config.filterInputsBy(new FilterBuilder().includePackage("com.qjq.guava").includePackage("com.qjq.guava"));
        config.addUrls(ClasspathHelper.forPackage("com.qjq.guava"));
        config.setScanners(new TypeAnnotationsScanner(),new MethodAnnotationsScanner());
        Reflections reflections = new Reflections(config);
        Set<Method> resources = reflections.getMethodsAnnotatedWith(org.junit.Test.class);

        System.out.println(resources.size());
    }
}
