package org.game.Entity;

import lombok.Getter;
import lombok.Setter;
import org.game.Entity.item.Key;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class Door extends Entity{
    int doorNumber;
    public boolean locked;

    ImageIcon image;

    public Door(int x, int y, int doorNumber, boolean isVertical) {
        this.x = x;
        this.y = y;

        this.doorNumber = doorNumber;
        this.locked = true;
        if(isVertical){
            setImageVertical(this);
        }else{
            setImage(this);
        }
    }

    public void setImage(Door e){
        e.image = new ImageIcon(getClass().getResource("/door_g.png"));
    }
    public void setImageVertical(Door e){
        e.image = new ImageIcon(getClass().getResource("/door_v.png"));
    }
    public void unlock(Key key){
        if(key.isRightDoor(doorNumber)){
            locked = false;
//            System.out.println("OPEN");
        }else{
//            System.out.println("FALSE KEY");
        }
    }
    void open(){}
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
