package com.jetbrains;
import bg.uni.sofia.fmi.mjt.dungeon.Direction;
import bg.uni.sofia.fmi.mjt.dungeon.GameEngine;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Hero;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Enemy;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.*;

public class Main {

    public static void main(String[] args) {
        Hero hero = new Hero("hero", 100, 100);
        hero.learn(new Spell("hero weapon", 40, 5));
        char[][] map = new char[][]{"###".toCharArray(),
                "ES.".toCharArray(),
                "#HG".toCharArray()};
        Enemy[] enemies = new Enemy[]{new Enemy("enemy", 100, 100, new Weapon("enemy weapon", 30), new Spell("spell",30, 5))};
        Treasure[] treasures = new Treasure[]{new Weapon("strong weapon", 50)};
        GameEngine gameEngine = new GameEngine(map, hero, enemies, treasures);
        String moveMessage = gameEngine.makeMove(Direction.LEFT);
	    System.out.println(moveMessage);
    }
}
