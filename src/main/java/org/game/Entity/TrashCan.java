package org.game.Entity;

import org.game.Entity.item.Item;
import org.game.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 *
 */
public class TrashCan extends Entity{
    public List<Item> content;
    void search(Player player){
    }
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
        t.image = new ImageIcon(getClass().getResource("/trashcan.png"));
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
//        g2d.setColor(Color.DARK_GRAY);
//        g2d.fillOval(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
