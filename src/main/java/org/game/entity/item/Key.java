package org.game.entity.item;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

public class Key extends Item implements Serializable {

    int doorNumber;
    ImageIcon image;
    public Key(int x, int y, int doorNumber) {
        this.x = x;
        this.y = y;
        this.doorNumber = doorNumber;
        width = 40;
        height = 40;
        setImage(this);
    }

    public void setImage(Key t){
        t.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/key.png")));
    }


    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isRightDoor(int doorNumber){
        return this.doorNumber == doorNumber;
    }
}
