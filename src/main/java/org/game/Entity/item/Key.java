package org.game.Entity.item;
import org.game.Player;

import java.awt.*;
import java.io.Serializable;

public class Key extends Item implements Serializable {

    int doorNumber;
    int width = 40;
    int height = 40;
    public Key(int x, int y, int doorNumber) {
        this.name = "Key";
        this.x = x;
        this.y = y;
        this.doorNumber = doorNumber;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.ORANGE);
        g2d.fillOval(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isRightDoor(int doorNumber){
        if(this.doorNumber == doorNumber){
            return true;
        }
        return false;
    }
}
