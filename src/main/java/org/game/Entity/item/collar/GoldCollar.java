package org.game.Entity.item.collar;


import java.awt.*;

public class GoldCollar extends Collar {
    public int x_c;
    public  int y_c;
    private static final int health = 3;
    private static final int damage = 3;
    public GoldCollar(int x, int y){
        this.name = "GoldCollar";
        this.x_c = x;
        this.y_c = y;
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.orange);
        g2d.fillRect(x_c, y_c, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x_c, y_c, width, height);
    }

}
