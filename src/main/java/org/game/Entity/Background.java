package org.game.Entity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Background extends Entity{
    ImageIcon image;
    public Background(int x, int y, int width, int height, boolean isGrass, boolean isAsphalt, boolean isFloor, boolean isPlate) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        if(isGrass){
            setImageGrass(this);
        }else if(isAsphalt){
            setImageAsphalt(this);
        }else if(isFloor){
            setImageFloor(this);
        }else if(isPlate){
            setImagePlate(this);
        }
    }

    public void setImageAsphalt(Background o){
        o.image = new ImageIcon(getClass().getResource("/asphalt.png"));
    }

    public void setImageGrass(Background o){
        o.image = new ImageIcon(getClass().getResource("/grass.png"));
    }

    public void setImageFloor(Background o){
        o.image = new ImageIcon(getClass().getResource("/floor.png"));
    }

    public void setImagePlate(Background o){
        o.image = new ImageIcon(getClass().getResource("/plate.png"));
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
