/**
 * Objects, that interact with player
 *
 * @version 1.0
 * @see Item
 * @since 1.0
 */
package org.game.entity.item;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

/**
 * The Key class represents a key in the game.
 * It extends the Item class.
 */
@Log
@Getter
@Setter
public class Key extends Item implements Serializable {
    /**
     * The file name for the key texture.
     */
    private static final String KEY_FILE_NAME = "/key.png";

    /**
     * The size of the key.
     */
    private static final int SIZE = 30;

    /**
     * The number associated with the door that the key unlocks.
     */
    private int doorNumber;

    /**
     * The image icon representing the key.
     */
    private ImageIcon image;

    /**
     * Constructs a Key object with the specified position and door number.
     *
     * @param x    The x-coordinate of the key's position.
     * @param y    The y-coordinate of the key's position.
     * @param dNum The door number associated with the key.
     */
    public Key(final int x, final int y, final int dNum) {
        this.setX(x);
        this.setY(y);
        this.setDoorNumber(dNum);
        this.setWidth(SIZE);
        this.setHeight(SIZE);
        setImage(this);
    }

    /**
     * Sets the image of the key.
     *
     * @param t The Key object.
     */
    public void setImage(final Key t) {
        URL img = Objects.requireNonNull(getClass().getResource(KEY_FILE_NAME));
        t.image = new ImageIcon(img);
    }

    /**
     * Draws the key on the specified graphics context.
     *
     * @param g2d The graphics context.
     */
    public void draw(final Graphics2D g2d) {
        g2d.drawImage(image.getImage(), getX(), getY(), null);
    }

    /**
     * Retrieves the bounding rectangle of the key.
     *
     * @return The bounding rectangle.
     */
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Checks if the key matches the given door number.
     *
     * @param dNum The door number to check against.
     * @return true if the key matches the door number, false otherwise.
     */
    public boolean isRightDoor(final int dNum) {
        return this.doorNumber == dNum;
    }
}
