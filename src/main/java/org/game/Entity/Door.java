package org.game.Entity;

import org.game.Entity.item.Key;
import java.awt.*;

public class Door extends Entity{
    int doorNumber;
    boolean locked;
    int width = 100;
    int height = 25;

    public Door(int x, int y, int doorNumber) {
        this.x = x;
        this.y = y;
        this.doorNumber = doorNumber;
    }
    void unlock(Key key){
        if(key.isRightDoor(doorNumber)){
            locked = false;
        }else{
            locked = true;
        }
    }
    void open(){}
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.orange);
        g2d.fillRect(x, y, width, height);
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
