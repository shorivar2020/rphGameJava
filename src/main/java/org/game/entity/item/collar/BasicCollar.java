package org.game.entity.item.collar;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.net.URL;
import java.util.Objects;

/**
 * The BasicCollar class represents a basic collar item in the game.
 * It extends the Collar class.
 */
@Log
@Getter
@Setter
public class BasicCollar extends Collar {
    /**
     * The file name for the basic collar texture.
     */
    private static final String BASIC_COLLAR_FILE_NAME = "/basic_collar.png";

    /**
     * The width size of the basic collar.
     */
    private static final int WIDTH_SIZE = 20;

    /**
     * The height size of the basic collar.
     */
    private static final int HEIGHT_SIZE = 5;

    /**
     * The starting x-coordinate of the basic collar.
     */
    private static final int START_X = 10;

    /**
     * The starting y-coordinate of the basic collar.
     */
    private static final int START_Y = 5;
    /**
     * The image associated with the game win view.
     */
    private ImageIcon image;

    /**
     * Constructs a BasicCollar object.
     * Sets the width and height of the collar, and initializes the image.
     */
    public BasicCollar() {
        setWidth(WIDTH_SIZE);
        setHeight(HEIGHT_SIZE);
        setImage(this);
    }

    private void setImage(final BasicCollar collar) {
        URL img = Objects.
                requireNonNull(getClass().getResource(BASIC_COLLAR_FILE_NAME));
        collar.image = new ImageIcon(img);
    }

    /**
     * Draws the basic collar on the screen.
     *
     * @param g2d the Graphics2D object to draw on
     */
    @Override
    public void draw(final Graphics2D g2d) {
        g2d.drawImage(image.getImage(), START_X, START_Y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(START_X, START_Y, getWidth(), getHeight());
    }
}
