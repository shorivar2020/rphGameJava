package org.game.Entity.item.collar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SilverCollar extends Collar {
    public int x_c;
    public int y_c;
    private static final int health = 1;
    private static final int damage = 1;
    ImageIcon image;
    public SilverCollar(int x, int y){
        this.name = "SilverCollar";
        this.x_c = x;
        this.y_c = y;
        setImage(this);
    }

    public void setImage(SilverCollar t){
        t.image = new ImageIcon(getClass().getResource("/silvercollar.png"));
    }
    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x_c, y_c, null);
    }
    public Rectangle getBounds() {
        return new Rectangle(x_c, y_c, width, height);
    }

}
