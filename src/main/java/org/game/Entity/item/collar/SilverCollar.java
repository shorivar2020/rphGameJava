package org.game.Entity.item.collar;

import java.awt.*;

public class SilverCollar extends Collar {
    public int x_c;
    public int y_c;
    private static final int health = 1;
    private static final int damage = 1;
    public SilverCollar(int x, int y){
        this.name = "SilverCollar";
        this.x_c = x;
        this.y_c = y;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(x_c, y_c, width, height);
    }
    public Rectangle getBounds() {
        return new Rectangle(x_c, y_c, width, height);
    }

}
