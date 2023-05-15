package org.game.entity;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

public class Exit extends Entity implements Serializable {
    ImageIcon image;
    public Exit(int x, int y) {
        this.x = x;
        this.y = y;
        width = 50;
        height = 50;
        setImage(this);
    }

    public void setImage(Exit e){
        e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/exit.png")));
    }
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
