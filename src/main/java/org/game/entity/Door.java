package org.game.entity;

import lombok.Getter;
import lombok.Setter;
import org.game.entity.item.Key;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class Door extends Entity implements Serializable {
    int doorNumber;
    private boolean locked;
    ImageIcon image;

    public Door(int x, int y, int doorNumber, boolean isVertical) {
        this.x = x;
        this.y = y;

        this.doorNumber = doorNumber;
        this.locked = true;
        if(isVertical){
            width = 25;
            height = 90;
            setImageVertical(this);
        }else{
            width = 90;
            height = 25;
            setImage(this);
        }
    }

    public void setImage(Door e){
        e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/door_g.png")));
    }
    public void setImageVertical(Door e){
        e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/door_v.png")));
    }
    public void unlock(Key key){
        if(key.isRightDoor(doorNumber)){
            locked = false;
        }
    }
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
