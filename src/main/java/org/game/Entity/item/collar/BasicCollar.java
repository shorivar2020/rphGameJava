package org.game.Entity.item.collar;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BasicCollar extends Collar {
    public int x_c;
    public int y_c;
    private static final int health = 0;
    private static final int damage = 0;
    ImageIcon image;
    public BasicCollar(int x, int y){
        this.name = "SilverCollar";
        this.x_c = x;
        this.y_c = y;
        setImage(this);
    }

    public void setImage(BasicCollar t){
        t.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/basic_collar.png")));
    }
    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
    }
    public Rectangle getBounds() {
        return new Rectangle(x_c, y_c, width, height);
    }

}
