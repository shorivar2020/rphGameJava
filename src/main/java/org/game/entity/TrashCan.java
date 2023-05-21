package org.game.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.game.entity.item.Item;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * The TrashCan class represents a trash can entity in the game.
 * It contains a list of items that can be discarded into the trash can.
 */
@Log
@Getter
@Setter
public class TrashCan extends Entity implements Serializable {
    private static final int SIZE = 25;
    private static final String TRASHCAN_FILE_NAME = "/trashcan.png";
    private List<Item> content;
    ImageIcon image;

    /**
     * Constructs a new TrashCan object with the specified coordinates and content.
     *
     * @param x       The x-coordinate of the trash can.
     * @param y       The y-coordinate of the trash can.
     * @param content The list of items in the trash can.
     */
    public TrashCan(int x, int y, List<Item> content) {
        setX(x);
        setY(y);
        this.content = content;
        setWidth(SIZE);
        setHeight(SIZE);
        setImage(this);
    }

    /**
     * Sets the image for the trash can.
     *
     * @param t The TrashCan object to set the image for.
     */
    public void setImage(TrashCan t) {
        t.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(TRASHCAN_FILE_NAME)));
    }

    /**
     * Draws the trash can on the specified graphics context.
     *
     * @param g2d The graphics context.
     */
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), getX(), getY(), null);
    }

    /**
     * Retrieves the bounding rectangle of the trash can.
     *
     * @return The bounding rectangle.
     */
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
