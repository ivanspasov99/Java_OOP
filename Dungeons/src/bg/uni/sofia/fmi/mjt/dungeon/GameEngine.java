package bg.uni.sofia.fmi.mjt.dungeon;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Hero;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Enemy;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.*;

public class GameEngine {
    // enemy[] & treasuries[] must be equal as in map
    char[][] map;
    Hero hero;
    Enemy[] enemies;
    private int enemyCounter = 0;
    Treasure[] treasures;
    private int treasureCounter = 0;
    Position position;

    public GameEngine(char[][] map, Hero hero, Enemy[] enemies, Treasure[] treasures) {
        this.map = map.clone();
        for (int i = 0; i < map.length; i++) {
            map[i] = map[i].clone();
        }
        this.hero = hero;
        this.enemies = enemies;
        this.treasures = treasures;
        setPlayerPosition();
    }

    public char[][] getMap(){
        return map.clone(); // better?????
    }
    public Hero getHero() { return hero; }

    public String makeMove(Direction direction){
        switch (direction){
            case UP: {
                return move(-1,0);
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
            default: return  "Unknown command entered";
        }
    }

    private void setPlayerPosition(){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if(map[i][j] == 'S') {
                    position = new Position(i,j);
                    map[i][j] = 'H';
                }
            }
        }
    }
    private void changePositions(Position newPost, Position OldPosition) {
        char temp = map[newPost.x][newPost.y];
        map[newPost.x][newPost.y] =  map[OldPosition.x][OldPosition.y];
        map[OldPosition.x][OldPosition.y] = temp;
    }

    private boolean checkSpecificSymbol(char mapSymbol, char symbol) {
        return mapSymbol == symbol;
    }
    private boolean inBorders(char[][] map, Position newPos) {
        return newPos.x >= 0 && newPos.x < map.length && newPos.y >= 0 && newPos.y < map[newPos.x].length;
    }
    private boolean haveBlockade(char[][] map, Position newPos) {
        return map[newPos.x][newPos.y] == '#';
    }
    private boolean isFreeSymbol(char[][] map, Position newPos) {
        return checkSpecificSymbol(map[newPos.x][newPos.y], '.');
    }
    private boolean isTreasure(char[][] map, Position newPos) {
        return checkSpecificSymbol(map[newPos.x][newPos.y], 'T');
    }
    private boolean isEnemy(char[][] map, Position newPos) {
        return checkSpecificSymbol(map[newPos.x][newPos.y], 'E');
    }

    private String move(int X, int Y) {
        Position newPos = new Position(position.x + X, position.y + Y);
        if(!inBorders(this.map, newPos)) {
            return "You are out of the map";
        }
        if(haveBlockade(this.map, newPos)) {
            return "Wrong move. There is an obstacle and you cannot bypass it.";
        }
        if(isFreeSymbol(this.map, newPos)) {
            changePositions(newPos, this.position);
            return "You moved successfully to the next position.";
        }
        // refactor ->
        if(isTreasure(this.map, newPos)) {
            if(treasures[treasureCounter] instanceof Weapon){
                Weapon newWeapon = (Weapon)treasures[treasureCounter];
                treasureCounter++;
                this.map[newPos.x][newPos.y] = '.';
                changePositions(newPos, this.position);
                return newWeapon.collect(hero);
            }
            if(treasures[treasureCounter] instanceof Spell){
                Spell newSpell = (Spell)treasures[treasureCounter];
                treasureCounter++;
                this.map[newPos.x][newPos.y] = '.';
                changePositions(newPos, this.position);
                return newSpell.collect(hero);
            }
            if(treasures[treasureCounter] instanceof HealthPotion){
                HealthPotion newHealthPotion = (HealthPotion)treasures[treasureCounter];
                treasureCounter++;
                this.map[newPos.x][newPos.y] = '.';
                changePositions(newPos, this.position);
                // strange, it is reversed
                return newHealthPotion.collect(this.hero);
            }
            if(treasures[treasureCounter] instanceof ManaPotion){
                ManaPotion newManaPotion = (ManaPotion)treasures[treasureCounter];
                treasureCounter++;
                this.map[newPos.x][newPos.y] = '.';
                changePositions(newPos, this.position);
                return newManaPotion.collect(this.hero);
            }

        }
        if(isEnemy(this.map, newPos)) {
            // fight, could be a class
            while(hero.isAlive() && enemies[enemyCounter].isAlive()){
                enemies[enemyCounter].takeDamage(hero.attack());
                hero.takeDamage(enemies[enemyCounter].attack());
            }
            if(hero.isAlive()) {
                enemyCounter++;
                this.map[newPos.x][newPos.y] = '.';
                changePositions(newPos, this.position);
                return "Enemy Died.";
            } else {
                return "Hero is dead! Game Over!";
            }
        }
        return "You have successfully passed through the dungeon. Congrats!";
    }
}


