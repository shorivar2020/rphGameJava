package org.game.Entity;

import org.game.Entity.item.Item;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public class TrashCan extends Entity{
    public List<Item> content;
    ImageIcon image;
    int width = 25;
    int height = 25;
    public TrashCan(int x, int y, List<Item> content) {
        this.x = x;
        this.y = y;
        this.content = content;
        setImage(this);
    }
    public void setImage(TrashCan t){
        t.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/trashcan.png")));
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
