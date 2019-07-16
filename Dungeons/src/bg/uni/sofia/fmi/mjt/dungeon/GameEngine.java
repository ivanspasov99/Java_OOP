package bg.uni.sofia.fmi.mjt.dungeon;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Hero;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Enemy;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.*;
import bg.uni.sofia.fmi.mjt.dungeon.Position;

public class GameEngine {
    // enemy[] & treasuries[] must be equal as in map
    char[][] map;
    Hero hero;
    Enemy[] enemies;
    Treasure[] treasures;
    Position position;

    public GameEngine(char[][] map, Hero hero, Enemy[] enemies, Treasure[] treasures) {
        this.map = map.clone(); // !!!
        this.hero = hero;
        this.enemies = enemies;
        this.treasures = treasures;
        setPlayerPosition();
    }

    char[][] getMap(){
        return map; // or map.clone()?
    }
    Hero getHero() { return hero; }

    String makeMove(Direction direction){
        switch (direction){
            // maybe separate class for movement
            case UP: {
                // maybe first par. to be oldPosition(object), slower
                if(checkBorders(position.x-1, position.y)) {
                    if(!haveBlockade(position.x-1, position.y)) {
                        if(!haveSpecificSymbol(position.x-1, position.y)){
                            changePosition(position.x-1, position.y, this.position);
                            return "You moved successfully to the next position.";
                        }
                        else {
                            if(doAction()) {
                                changePosition(position.x-1, position.y, this.position);
                                return "Enemy Dead";
                            }
                            else return "Hero Died! Game Over!";
                        }
                    }
                    else return "Wrong move. There is a obstacle and you cannot bypass it.";
                }
                else return "Out of Map.";
                //break; maybe not reachable
            }
            // other directions
            default: return "Default";
        }
    }

    private void setPlayerPosition(){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if(map[i][j] == 'S') {
                    position.x = i;
                    position.y = j;
                }
            }
        }
    }
    private boolean checkBorders(int newCoordX, int newCoordY) {
        if(newCoordX > 0 && newCoordX < map.length && newCoordY > 0 && newCoordY < map[newCoordX].length)
            return true;
        else
            return false;
    }
    private boolean haveBlockade(int newCoordX, int newCoordY) {
        if(map[newCoordX][newCoordY] == '#') return true;
        else return false;
    }
    private boolean haveSpecificSymbol(int newCoordX, int newCoordY) {
        if(map[newCoordX][newCoordY] != '.') return true;
        else return false;
    }
    private void changePosition(int newCoordX, int newCoordY, Position playerOldPosition) {
        char temp = map[newCoordX][newCoordY];
        map[newCoordX][newCoordY] =  map[playerOldPosition.x][playerOldPosition.y];
        map[playerOldPosition.x][playerOldPosition.y] = temp;
    }

    // not done
    private boolean doAction() {
        // algorithm
        return true;
    }
}
