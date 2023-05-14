package org.game.Entity.item.collar;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GoldCollar extends Collar {
    public int x_c;
    public  int y_c;
    private static final int health = 3;
    private static final int damage = 3;
    ImageIcon image;
    public GoldCollar(int x, int y){
        this.name = "GoldCollar";
        this.x_c = x;
        this.y_c = y;
        setImage(this);
    }

    public void setImage(GoldCollar t){
        t.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/gold_collar.png")));
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x_c, y_c, null);
    }
    public Rectangle getBounds() {
        return new Rectangle(x_c, y_c, width, height);
    }

}
