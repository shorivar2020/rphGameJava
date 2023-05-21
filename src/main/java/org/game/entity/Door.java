package org.game.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.game.entity.item.Key;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Door class represents a door entity in the game.
 * It extends the Entity class and implements Serializable.
 */
@Log
@Getter
@Setter
public class Door extends Entity implements Serializable {
    private static final String DOOR_FILE_NAME_HORIZONTAL = "/door_g.png";
    private static final String DOOR_FILE_NAME_VERTICAL = "/door_v.png";

    private static final int SIZE_FIRST = 25;
    private static final int SIZE_SECOND = 90;
    private int doorNumber;
    private boolean locked;
    private ImageIcon image;

    /**
     * Constructs a Door object with the specified position, door number, and orientation.
     *
     * @param x            The x-coordinate of the door's position.
     * @param y            The y-coordinate of the door's position.
     * @param doorNumber   The number of the door.
     * @param isVertical   Flag indicating if the door is vertical or horizontal.
     */
    public Door(int x, int y, int doorNumber, boolean isVertical) {
        this.setX(x);
        this.setY(y);
        this.doorNumber = doorNumber;
        this.locked = true;
        if (isVertical) {
            setWidth(SIZE_FIRST);
            setHeight(SIZE_SECOND);
            setImageVertical(this);
        } else {
            setWidth(SIZE_SECOND);
            setHeight(SIZE_FIRST);
            setImage(this);
        }
    }

    /**
     * Sets the image of the door to a horizontal door image.
     *
     * @param e The Door object.
     */
    public void setImage(Door e) {
        e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(DOOR_FILE_NAME_HORIZONTAL)));
    }

    /**
     * Sets the image of the door to a vertical door image.
     *
     * @param e The Door object.
     */
    public void setImageVertical(Door e) {
        e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(DOOR_FILE_NAME_VERTICAL)));
    }

    /**
     * Unlocks the door with the specified key.
     * If the key matches the door's number, the door is unlocked.
     *
     * @param key The key used to unlock the door.
     */
    public void unlock(Key key) {
        if (key.isRightDoor(doorNumber)) {
            locked = false;
        }
    }

    /**
     * Draws the door on the specified graphics context.
     *
     * @param g2d The graphics context.
     */
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), getX(), getY(), null);
    }

    /**
     * Retrieves the bounding rectangle of the door.
     *
     * @return The bounding rectangle.
     */
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
