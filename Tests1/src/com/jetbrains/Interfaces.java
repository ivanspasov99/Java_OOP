package com.jetbrains;

public interface Interfaces {
    private void method1() {
        System.out.println("method1");
    }
    /*public void method2() {
        method1();
        System.out.println("method2");
    }*/
    private static void method3() {
        System.out.println("method3 can be used in private static or non static method");
    }
    private void method4() {
        method1();
        method3();
    }
    public static void method5() {
        System.out.println("i am public static method");
    }


}
