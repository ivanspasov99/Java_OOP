package bg.uni.sofia.fmi.mjt.dungeon;

import bg.uni.sofia.fmi.mjt.dungeon.actor.Hero;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Enemy;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.*;

public class GameEngine {
    // enemy[] & treasuries[] must be equal as in map
    char[][] map;
    Hero hero;
    public Enemy[] enemies;
    private int enemyCounter = 0;
    Treasure[] treasures;
    private int treasureCounter = 0;
    Position position;

    public GameEngine(char[][] map, Hero hero, Enemy[] enemies, Treasure[] treasures) {
        this.map = map.clone();
        this.map = new char[map.length][];
        for (int i = 0; i < map.length; i++) {
            this.map[i] = new char[map[i].length];
            for (int j = 0; j < map[i].length; j++) {
                this.map[i][j] = map[i][j];
            }
        }
        this.hero = new Hero(hero); // if we use copy const (game engine does not uses the submitted hero) not showing
        //this.enemies = enemies.clone();
        setLocalArrayOfEnemies(enemies);
        this.treasures = treasures.clone(); // same as above
        setPlayerPosition();
    }

    public char[][] getMap() {
        return map;
    }

    public Hero getHero() {
        return hero;
    }

    public String makeMove(Direction direction) {
        switch (direction) {
            case UP: {
                return move(-1, 0);
            }
            case RIGHT: {
                return move(0, 1);
            }
            case LEFT: {
                return move(0, -1);
            }
            case DOWN: {
                return move(+1, 0);
            }
            default:
                return "Unknown command entered";
        }
    }

    private void setPlayerPosition() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] == 'S') {
                    position = new Position(i, j);
                    map[i][j] = 'H';
                }
            }
        }
    }

    private void changePositions(Position newPost, Position OldPosition) {
        char temp = map[newPost.x][newPost.y];
        map[newPost.x][newPost.y] = map[OldPosition.x][OldPosition.y];
        map[OldPosition.x][OldPosition.y] = temp;
        position = new Position(newPost.x, newPost.y);
    }

    private boolean inBorders(char[][] map, Position newPos) {
        return newPos.x >= 0 && newPos.x < map.length && newPos.y >= 0 && newPos.y < map[newPos.x].length;
    }

    private boolean haveBlockade(char[][] map, Position newPos) {
        return map[newPos.x][newPos.y] == '#';
    }

    private boolean isFreeSymbol(char[][] map, Position newPos) {
        return map[newPos.x][newPos.y] == '.';
    }

    private boolean isTreasure(char[][] map, Position newPos) {
        return map[newPos.x][newPos.y] == 'T';
    }

    private boolean isEnemy(char[][] map, Position newPos) {
        return map[newPos.x][newPos.y] == 'E';
    }

    private String collect(Treasure treasure) {
        treasureCounter++;
        return treasure.collect(hero);
    }

    private String move(int X, int Y) {
        Position newPos = new Position(position.x + X, position.y + Y);
        if (!inBorders(this.map, newPos)) {
            return "You are out of the map.";
        }
        if (haveBlockade(this.map, newPos)) {
            return "Wrong move. There is an obstacle and you cannot bypass it.";
        }
        if (isFreeSymbol(this.map, newPos)) {
            changePositions(newPos, this.position);
            return "You moved successfully to the next position.";
        }
        // refactor ->
        if (isTreasure(this.map, newPos)) {
            Treasure treasure = null;
            if (treasures[treasureCounter] instanceof Weapon) {
                treasure = (Weapon) treasures[treasureCounter];
            }
            if (treasures[treasureCounter] instanceof Spell) {
                treasure = (Spell) treasures[treasureCounter];
            }
            if (treasures[treasureCounter] instanceof HealthPotion) {
                treasure = (HealthPotion) treasures[treasureCounter];
            }
            if (treasures[treasureCounter] instanceof ManaPotion) {
                treasure = (ManaPotion) treasures[treasureCounter];
            }
            if (treasure != null) {
                this.map[newPos.x][newPos.y] = '.';
                changePositions(newPos, this.position);
                return collect(treasure);
            }

        }
        if (isEnemy(this.map, newPos)) {
            // fight, could be a class
            while (hero.isAlive() && enemies[enemyCounter].isAlive()) {
                enemies[enemyCounter].takeDamage(hero.attack());
                if (!enemies[enemyCounter].isAlive()) break;
                hero.takeDamage(enemies[enemyCounter].attack());
            }
            if (hero.isAlive()) {
                enemyCounter++;
                this.map[newPos.x][newPos.y] = '.';
                changePositions(newPos, this.position);
                return "Enemy died.";
            } else {
                return "Hero is dead! Game over!";
            }
        }
        this.map[newPos.x][newPos.y] = '.';
        changePositions(newPos, this.position);
        return "You have successfully passed through the dungeon. Congrats!";
    }

    private void setLocalArrayOfEnemies(Enemy[] enemies) {
        this.enemies = new Enemy[enemies.length];
        for (int i = 0; i < enemies.length; i++) {
            this.enemies[i] = new Enemy(enemies[i]);
        }
    }
}


