package org.game.Entity.item;
import org.game.Entity.TrashCan;
import org.game.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

public class Key extends Item implements Serializable {

    int doorNumber;
    int width = 40;
    int height = 40;
    ImageIcon image;
    public Key(int x, int y, int doorNumber) {
        this.name = "Key";
        this.x = x;
        this.y = y;
        this.doorNumber = doorNumber;
        setImage(this);
    }

    public void setImage(Key t){
        t.image = new ImageIcon(getClass().getResource("/key.png"));
    }


    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
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
