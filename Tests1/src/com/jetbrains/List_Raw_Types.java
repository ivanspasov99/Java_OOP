package com.jetbrains;

import java.util.ArrayList;
import java.util.List;

public class List_Raw_Types {

    public static void main(String[] args) {
        List x1 = new ArrayList();
        List x2 = new ArrayList<>();
        List<? extends Object> objects = new ArrayList<>();
        List<Integer> x3 = x1;
        objects = x3;
        x2 = x3;
        x1.add(1);
        x1.add(3);
        x3.remove(1);
        System.out.println(x3.size());
        List<Number> x4 = x1;


        Integer i = 5;
        Number n = i;
    }
}
