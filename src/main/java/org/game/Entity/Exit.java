package org.game.Entity;

import org.game.Entity.item.Key;

import java.awt.*;

public class Exit extends Entity{
    int width = 100;
    int height = 100;

    public Exit(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.CYAN);
        g2d.fillRect(x, y, width, height);
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
