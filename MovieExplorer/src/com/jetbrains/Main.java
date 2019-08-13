package com.jetbrains;

import bg.sofia.uni.fmi.mjt.movies.MoviesExplorer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) {
        try (InputStream movieList = new FileInputStream("resources/movies-mpaa.txt")) {
            MoviesExplorer moviesExplorer = new MoviesExplorer(movieList);
        } catch (IOException fnf) {
            // auto-closable problem??
            fnf.printStackTrace(); // maybe not enough
        }
    }
}
