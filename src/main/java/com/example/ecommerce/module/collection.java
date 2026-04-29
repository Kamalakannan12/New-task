package com.example.ecommerce.module;

import java.util.*;
import java.util.stream.*;

public class collection {
    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(2);
        list.add(8);
        list.add(2); // duplicate
        list.add(10);

        System.out.println(" LIST ");

        // Stream and print
        System.out.println("Print:");
        list.stream().forEach(System.out::println);

        // Sorting
        System.out.println("Sorted:");
        list.stream().sorted().forEach(System.out::println);

        // Remove duplicates
        System.out.println("Distinct:");
        list.stream().distinct().forEach(System.out::println);

        // Filter condition (greater than 5)
        System.out.println("Filtered:");
        list.stream().filter(x -> x > 5).forEach(System.out::println);

        // Limit and Skip
        System.out.println("Limit 3:");
        list.stream().limit(3).forEach(System.out::println);

        System.out.println("Skip 2:");
        list.stream().skip(2).forEach(System.out::println);

        //set
        Set<Integer> set = new HashSet<>();
        set.add(5);
        set.add(2);
        set.add(8);
        set.add(2); // ignored (no duplicates)
        set.add(10);

        System.out.println("\n SET ");

        // Stream and print
        System.out.println("Print:");
        set.stream().forEach(System.out::println);

        // Sorting
        System.out.println("Sorted:");
        set.stream().sorted().forEach(System.out::println);

        // Distinct (already unique)
        System.out.println("Distinct:");
        set.stream().distinct().forEach(System.out::println);

        // Filter
        System.out.println("Filtered :");
        set.stream().filter(x -> x > 5).forEach(System.out::println);

        // Limit & Skip
        System.out.println("Limit 2:");
        set.stream().limit(2).forEach(System.out::println);

        System.out.println("Skip 1:");
        set.stream().skip(1).forEach(System.out::println);

//map
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Apple");
        map.put(2, "Banana");
        map.put(3, "Orange");
        map.put(4, "Apple"); // duplicate value allowed

        System.out.println("\nMAP");

        // Stream and print
        System.out.println("Print:");
        map.entrySet()
                .stream()
                .forEach(System.out::println);

        // Sorting by key
        System.out.println("Sorted by Key:");
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(System.out::println);

        // Remove duplicate values
        System.out.println("Distinct Values:");
        map.values()
                .stream()
                .distinct()
                .forEach(System.out::println);

        // Filter
        System.out.println("Filtered (key > 2):");
        map.entrySet()
                .stream()
                .filter(e -> e.getKey() > 2)
                .forEach(System.out::println);

        // Limit & Skip
        System.out.println("Limit 2:");
        map.entrySet()
                .stream()
                .limit(2)
                .forEach(System.out::println);

        System.out.println("Skip 1:");
        map.entrySet()
                .stream()
                .skip(1)
                .forEach(System.out::println);
    }
}
