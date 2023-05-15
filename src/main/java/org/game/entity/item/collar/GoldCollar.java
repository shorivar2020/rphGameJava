package org.game.entity.item.collar;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GoldCollar extends Collar {
    public int xLocal;
    public  int yLocal;
    ImageIcon image;
    public GoldCollar(int x, int y){
        this.xLocal = x;
        this.yLocal = y;
        width = 20;
        height = 5;
        setImage(this);
    }

    public void setImage(GoldCollar t){
        t.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/gold_collar.png")));
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), xLocal, yLocal, null);
    }
    public Rectangle getBounds() {
        return new Rectangle(xLocal, yLocal, width, height);
    }

}
