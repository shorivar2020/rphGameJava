package org.game.Entity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Obstacle extends Entity{
    ImageIcon image;
    public Obstacle(int x, int y, int width, int height, boolean isBuilding, boolean isWater) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        if(isBuilding){
            setImageBuilding(this);
        }else if(isWater){
            setImageWater(this);
        }
    }

    public void setImageBuilding(Obstacle o){
        o.image = new ImageIcon(getClass().getResource("/brics.png"));
    }

    public void setImageWater(Obstacle o){
        o.image = new ImageIcon(getClass().getResource("/water.png"));
    }

    public void draw(Graphics2D g2d) {
//        g2d.setColor(Color.lightGray);
//        g2d.fillRect(x, y, width, height);
        g2d.drawImage(image.getImage(), x, y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
