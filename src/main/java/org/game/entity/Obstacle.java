package org.game.entity;

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
 * The Obstacle class represents an obstacle entity in the game.
 * It can be either a building or water.
 */
@Log
@Getter
@Setter
public class Obstacle extends Entity implements Serializable {
    /**
     * The size of the obstacle.
     */
    private static final int SIZE = 30;

    /**
     * The file name for the building obstacle texture.
     */
    private static final String FILE_NAME_BUILDING = "/bricks.png";

    /**
     * The file name for the water obstacle texture.
     */
    private static final String FILE_NAME_WATER = "/water.png";

    /**
     * The image icon representing the obstacle.
     */
    private ImageIcon image;


    /**
     * Constructs a new Obstacle object with the specified coordinates and type.
     *
     * @param x          The x-coordinate of the obstacle
     * @param y          The y-coordinate of the obstacle
     * @param isBuilding True if the obstacle is a building, false otherwise
     * @param isWater    True if the obstacle is water, false otherwise
     */
    public Obstacle(final int x, final int y,
                    final boolean isBuilding, final boolean isWater) {
        this.setX(x);
        this.setY(y);
        this.setWidth(SIZE);
        this.setHeight(SIZE);
        if (isBuilding) {
            setImageBuilding(this);
        } else if (isWater) {
            setImageWater(this);
        }
    }

    /**
     * Sets the image for the building obstacle.
     *
     * @param o The Obstacle object to set the image
     */
    public void setImageBuilding(final Obstacle o) {
        URL img = Objects.
                requireNonNull(getClass().getResource(FILE_NAME_BUILDING));
        o.image = new ImageIcon(img);
    }

    /**
     * Sets the image for the water obstacle.
     *
     * @param o The Obstacle object to set the image
     */
    public void setImageWater(final Obstacle o) {
        URL img = Objects.
                requireNonNull(getClass().getResource(FILE_NAME_WATER));
        o.image = new ImageIcon(img);
    }

    /**
     * Draws the obstacle on the specified graphics context.
     *
     * @param g2d The graphics context
     */
    public void draw(final Graphics2D g2d) {
        g2d.drawImage(image.getImage(), getX(), getY(), null);
    }

    /**
     * Retrieves the bounding rectangle of the obstacle.
     *
     * @return The bounding rectangle
     */
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
