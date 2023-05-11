package org.game.Entity.item;

import java.awt.*;

public class Food extends Item {
    int value = 1;
    int width = 25;
    int height = 25;

    public Food(int x, int y) {
        this.name = "Food";
        this.x = x;
        this.y = y;
    }
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.pink);
        g2d.fillRect(x, y, width, height);
    }

    public int getFoodValue(){
        return value;
    }
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
