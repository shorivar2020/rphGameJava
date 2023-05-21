package org.game.entity.item;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Key class represents a key item in the game.
 * It extends the Item class and implements Serializable.
 */
@Log
@Getter
@Setter
public class Key extends Item implements Serializable {
    private static final String KEY_FILE_NAME = "/key.png";
    private static final int SIZE = 30;
    private int doorNumber;
    private ImageIcon image;

    /**
     * Constructs a Key object with the specified position and door number.
     *
     * @param x          The x-coordinate of the key's position.
     * @param y          The y-coordinate of the key's position.
     * @param doorNumber The door number associated with the key.
     */
    public Key(int x, int y, int doorNumber) {
        this.setX(x);
        this.setY(y);
        this.setDoorNumber(doorNumber);
        this.setWidth(SIZE);
        this.setHeight(SIZE);
        setImage(this);
    }

    /**
     * Sets the image of the key.
     *
     * @param t The Key object.
     */
    public void setImage(Key t) {
        t.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(KEY_FILE_NAME)));
    }

    /**
     * Draws the key on the specified graphics context.
     *
     * @param g2d The graphics context.
     */
    public void draw(Graphics2D g2d) {
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
     * @param doorNumber The door number to check against.
     * @return true if the key matches the door number, false otherwise.
     */
    public boolean isRightDoor(int doorNumber) {
        return this.doorNumber == doorNumber;
    }
}
