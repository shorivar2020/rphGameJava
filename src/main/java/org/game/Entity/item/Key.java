package org.game.Entity.item;
import java.awt.*;

public class Key extends Item {
    int doorNumber;
    int width = 100;
    int height = 100;
    public Key(int x, int y, int doorNumber) {
        this.x = x;
        this.y = y;
        this.doorNumber = doorNumber;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.ORANGE);
        g2d.fillRect(x, y, width, height);
    }

    public boolean isRightDoor(int doorNumber){
        return false;
    }
}
