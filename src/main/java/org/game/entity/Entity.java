package org.game.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.awt.*;

/**
 * The Entity class represents a game entity.
 * It provides common properties and behaviors for entities in the game.
 */
@Log
@Getter
@Setter
public abstract class Entity {
    private int x;
    private int y;
    private int width;
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
