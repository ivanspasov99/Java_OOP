package com.jetbrains;

import bg.sofia.uni.fmi.mjt.git.Commit;
import bg.sofia.uni.fmi.mjt.git.Repository;
import bg.sofia.uni.fmi.mjt.git.Result;

public class Main {

    public static void main(String[] args) {
        //Commit commit = new Commit("First Commit");
        //System.out.println(commit.getDate());
        Repository rep = new Repository();
        Result result = rep.add("ivan","asd");
        Result result2 = rep.add("iv2an","asasdasd", "asd");

        Result result3 = rep.remove("ads");
        Result result4 = rep.remove("asd");

        //System.out.println(result.getMessage());
        //System.out.println(result2.getMessage());

        //System.out.println(result3.getMessage());
        //System.out.println(result4.getMessage());

        Result commitRes = rep.commit("First Commit");
        System.out.println(commitRes.getMessage());

        rep.add("1", "2", "3");
        rep.commit("Second Commit");
        rep.remove("1");
        rep.commit("Second remove commit");
        System.out.println("zdr");
    }
}
