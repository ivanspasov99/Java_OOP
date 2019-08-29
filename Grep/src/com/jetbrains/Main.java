package com.jetbrains;

import bg.sofia.uni.fmi.mjt.grep.StringSearcher;

public class Main {

    public static void main(String[] args) {
        StringSearcher stringSearcher = new StringSearcher();
        try {
            System.out.println(stringSearcher.stringSearch(System.in));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
