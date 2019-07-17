package com.jetbrains;
import bg.uni.sofia.fmi.mjt.dungeon.Direction;
import bg.uni.sofia.fmi.mjt.dungeon.GameEngine;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Hero;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Enemy;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.*;

public class Main {

    public static void main(String[] args) {
        Hero hero = new Hero("hero", 100, 100);
        char[][] map = new char[][]{"###".toCharArray(),
                "TS.".toCharArray(),
                "#EG".toCharArray()};
        Enemy[] enemies = new Enemy[]{new Enemy("enemy", 100, 0, new Weapon("enemy weapon", 30), null)};
        Treasure[] treasures = new Treasure[]{new Weapon("strong weapon", 50)};
        GameEngine gameEngine = new GameEngine(map, hero, enemies, treasures);
        String moveMessage = gameEngine.makeMove(Direction.LEFT);

	    System.out.println("Yes");
    }
}
