package org.game.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

/**
 * The Exit class represents an exit entity in the game.
 * It provides the functionality to mark the exit location on the game screen.
 */
@Log
@Getter
@Setter
public class Exit extends Entity implements Serializable {
    /**
     * The size of the exit.
     */
    private static final int SIZE = 50;

    /**
     * The file name for the exit texture.
     */
    private static final String EXIT_FILE_NAME = "/exit.png";

    /**
     * The image icon representing the exit.
     */
    private ImageIcon image;

    /**
     * Constructs a new Exit object with the specified coordinates.
     *
     * @param x The x-coordinate of the exit
     * @param y The y-coordinate of the exit
     */
    public Exit(final int x, final int y) {
        this.setX(x);
        this.setY(y);
        this.setWidth(SIZE);
        this.setHeight(SIZE);
        setImage(this);
    }

    /**
     * Sets the image for the exit.
     *
     * @param e The Exit object to set the image for
     */
    public void setImage(final Exit e) {
        URL img = Objects.
                requireNonNull(getClass().getResource(EXIT_FILE_NAME));
        e.image = new ImageIcon(img);
    }

    /**
     * Draws the exit on the specified graphics context.
     *
     * @param g2d The graphics context
     */
    public void draw(final Graphics2D g2d) {
        g2d.drawImage(image.getImage(), getX(), getY(), null);
    }

    /**
     * Retrieves the bounding rectangle of the exit.
     *
     * @return The bounding rectangle
     */
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
