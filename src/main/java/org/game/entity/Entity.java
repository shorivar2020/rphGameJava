package org.game.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * The Entity class represents a game entity.
 * It provides common properties and behaviors for entities in the game.
 */
@Log
@Getter
@Setter
public abstract class Entity {
    /**
     * The x-coordinate of the top-left corner of the rectangle.
     */
    private int x;

    /**
     * The y-coordinate of the top-left corner of the rectangle.
     */
    private int y;

    /**
     * The width of the rectangle.
     */
    private int width;

    /**
     * The height of the rectangle.
     */
    private int height;


    /**
     * Draws the entity on the specified graphics context.
     *
     * @param g2d The graphics context.
     */
    public abstract void draw(Graphics2D g2d);

    /**
     * Retrieves the bounding rectangle of the entity.
     *
     * @return The bounding rectangle.
     */
    public abstract Rectangle getBounds();
}
