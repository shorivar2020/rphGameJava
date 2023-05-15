package org.game.entity;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

public class Background extends Entity implements Serializable {
    ImageIcon image;
    public Background(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setImage(boolean isGrass, boolean isAsphalt, boolean isFloor, boolean isPlate, boolean isCushion){
        if(isGrass){
            setImageGrass(this);
        }else if(isAsphalt){
            setImageAsphalt(this);
        }else if(isFloor){
            setImageFloor(this);
        }else if(isPlate){
            setImagePlate(this);
        }else  if(isCushion){
            setImageCushion(this);
        }
    }

    public void setImageAsphalt(Background o){
        o.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/asphalt.png")));
    }

    public void setImageGrass(Background o){
        o.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/grass.png")));
    }

    public void setImageFloor(Background o){
        o.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/floor.png")));
    }

    public void setImagePlate(Background o){
        o.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/plate.png")));
    }

    public void setImageCushion(Background o){
        o.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/cushion.png")));
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
