package org.game.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.game.entity.item.Key;

import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

/**
 * The Door class represents a door entity in the game.
 * It extends the Entity class and implements Serializable.
 */
@Log
@Getter
@Setter
public class Door extends Entity implements Serializable {
    /**
     * The file name for the horizontal door texture.
     */
    private static final String FILE_NAME_HORIZONTAL = "/door_g.png";

    /**
     * The file name for the vertical door texture.
     */
    private static final String FILE_NAME_VERTICAL = "/door_v.png";

    /**
     * The size of the door's first dimension.
     */
    private static final int SIZE_FIRST = 25;

    /**
     * The size of the door's second dimension.
     */
    private static final int SIZE_SECOND = 90;

    /**
     * The number associated with the door.
     */
    private int doorNumber;

    /**
     * Indicates whether the door is locked or not.
     */
    private boolean locked;

    /**
     * The image icon representing the door.
     */
    private ImageIcon image;

    /**
     * Constructs a Door object with the specified position,
     * door number, and orientation.
     *
     * @param x          The x-coordinate of the door's position
     * @param y          The y-coordinate of the door's position
     * @param dNum       The number of the door
     * @param isVertical Flag indicating if the door is vertical/horizontal
     */
    public Door(final int x, final int y,
                final int dNum, final boolean isVertical) {
        this.setX(x);
        this.setY(y);
        this.doorNumber = dNum;
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
     * @param e The Door object
     */
    public void setImage(final Door e) {
        URL img = Objects.
                requireNonNull(getClass().getResource(FILE_NAME_HORIZONTAL));
        e.image = new ImageIcon(img);
    }

    /**
     * Sets the image of the door to a vertical door image.
     *
     * @param e The Door object
     */
    public void setImageVertical(final Door e) {
        URL img = Objects.
                requireNonNull(getClass().getResource(FILE_NAME_VERTICAL));
        e.image = new ImageIcon(img);
    }

    /**
     * Unlocks the door with the specified key.
     * If the key matches the door's number, the door is unlocked.
     *
     * @param key The key used to unlock the door
     */
    public void unlock(final Key key) {
        if (key.isRightDoor(doorNumber)) {
            locked = false;
        }
    }

    /**
     * Draws the door on the specified graphics context.
     *
     * @param g2d The graphics context
     */
    public void draw(final Graphics2D g2d) {
        g2d.drawImage(image.getImage(), getX(), getY(), null);
    }

    /**
     * Retrieves the bounding rectangle of the door.
     *
     * @return The bounding rectangle
     */
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
