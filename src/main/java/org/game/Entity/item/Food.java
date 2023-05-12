package org.game.Entity.item;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

public class Food extends Item implements Serializable {
    int value = 1;
    int width = 25;
    int height = 25;
    ImageIcon image;
    public Food(int x, int y) {
        this.name = "Food";
        this.x = x;
        this.y = y;
        setImage(this);
    }

    public void setImage(Food t){
        t.image = new ImageIcon(getClass().getResource("/food.png"));
    }
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
    }

    public int getFoodValue(){
        return value;
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
