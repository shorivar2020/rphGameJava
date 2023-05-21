package org.game.entity.item.collar;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * The BasicCollar class represents a basic collar item in the game.
 * It extends the Collar class.
 */
@Log
@Getter
@Setter
public class BasicCollar extends Collar {
    private static final String BASIC_COLLAR_FILE_NAME = "/basic_collar.png";
    private static final int WIDTH_SIZE = 20;
    private static final int HEIGHT_SIZE = 5;
    private static final int START_BASIC_COLLAR_X = 10;
    private static final int START_BASIC_COLLAR_Y = 5;
    ImageIcon image;

    /**
     * Constructs a BasicCollar object.
     * Sets the width and height of the collar, and initializes the image.
     */
    public BasicCollar() {
        setWidth(WIDTH_SIZE);
        setHeight(HEIGHT_SIZE);
        setImage(this);
    }

    private void setImage(BasicCollar collar) {
        collar.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(BASIC_COLLAR_FILE_NAME)));
    }

    /**
     * Draws the basic collar on the screen.
     *
     * @param g2d the Graphics2D object to draw on
     */
    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), START_BASIC_COLLAR_X, START_BASIC_COLLAR_Y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(START_BASIC_COLLAR_X, START_BASIC_COLLAR_Y, getWidth(), getHeight());
    }
}
