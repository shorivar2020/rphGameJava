package org.game;

import org.game.Entity.item.Item;

public class Map {
    int[][] map;
    public Map(int[][] map) {

    }
    public int getTile(int x, int y){
        return 0;
    }

    /* Check if something in Map */
    public boolean isObstacle(int x, int y){
        return false;
    }
    public boolean isEnemy(int x, int y){
        return false;
    }
    public Item isItem(int x, int y){
        return null;
    }
}
