package org.game.entity.item.collar;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BasicCollar extends Collar {
    private int xLocal;
    private int yLocal;
    ImageIcon image;
    public BasicCollar(int x, int y){
        this.xLocal = x;
        this.yLocal = y;
        width = 20;
        height = 5;
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
        return new Rectangle(xLocal, yLocal, width, height);
    }

}
