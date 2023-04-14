package org.game.Entity;

import org.game.Entity.item.Item;
import org.game.Player;

import java.awt.*;
import java.util.List;

/**
 *
 */
public class TrashCan extends Entity{
    List<Item> content;
    void search(Player player){
    }

    int width = 100;
    int height = 200;
    public TrashCan(int x, int y, List<Item> content) {
        this.x = x;
        this.y = y;
        this.content = content;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
