package org.game.Entity;

import org.game.Entity.item.Item;
import org.game.Player;

import java.awt.*;
import java.util.List;

/**
 *
 */
public class TrashCan extends Entity{
    public List<Item> content;
    void search(Player player){
    }

    int width = 25;
    int height = 25;
    public TrashCan(int x, int y, List<Item> content) {
        this.x = x;
        this.y = y;
        this.content = content;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillOval(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
