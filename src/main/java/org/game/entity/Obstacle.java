package org.game.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Obstacle class represents an obstacle entity in the game.
 * It can be either a building or water.
 */
@Log
@Getter
@Setter
public class Obstacle extends Entity implements Serializable {
    private static final int SIZE = 30;
    private static final String OBSTACLE_FILE_NAME_BUILDING = "/bricks.png";
    private static final String OBSTACLE_FILE_NAME_WATER = "/water.png";
    ImageIcon image;

    /**
     * Constructs a new Obstacle object with the specified coordinates and type.
     *
     * @param x          The x-coordinate of the obstacle.
     * @param y          The y-coordinate of the obstacle.
     * @param isBuilding True if the obstacle is a building, false otherwise.
     * @param isWater    True if the obstacle is water, false otherwise.
     */
    public Obstacle(int x, int y, boolean isBuilding, boolean isWater) {
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
     * @param o The Obstacle object to set the image for.
     */
    public void setImageBuilding(Obstacle o) {
        o.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(OBSTACLE_FILE_NAME_BUILDING)));
    }

    /**
     * Sets the image for the water obstacle.
     *
     * @param o The Obstacle object to set the image for.
     */
    public void setImageWater(Obstacle o) {
        o.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(OBSTACLE_FILE_NAME_WATER)));
    }

    /**
     * Draws the obstacle on the specified graphics context.
     *
     * @param g2d The graphics context.
     */
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), getX(), getY(), null);
    }

    /**
     * Retrieves the bounding rectangle of the obstacle.
     *
     * @return The bounding rectangle.
     */
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
