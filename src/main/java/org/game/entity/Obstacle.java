package org.game.entity;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

public class Obstacle extends Entity implements Serializable {
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
        o.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/bricks.png")));
    }

    public void setImageWater(Obstacle o){
        o.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/water.png")));
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
