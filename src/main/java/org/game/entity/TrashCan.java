package org.game.entity;

import org.game.entity.item.Item;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 */
public class TrashCan extends Entity implements Serializable {
    public List<Item> content;
    ImageIcon image;
    public TrashCan(int x, int y, List<Item> content) {
        this.x = x;
        this.y = y;
        this.content = content;
        width = 25;
        height = 25;
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
