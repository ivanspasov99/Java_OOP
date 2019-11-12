package com.jetbrains;

public class InheritStaticMethod implements Comparable {
    private final int a;

    public InheritStaticMethod(int a) {
        this.a = a;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
