package org.game.entity.item.collar;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * The SilverCollar class represents a silver collar item in the game.
 * It extends the Collar class.
 */
@Log
@Getter
@Setter
public class SilverCollar extends Collar {
    private static final String SILVER_COLLAR_FILE_NAME = "/silver_collar.png";
    private static final int WIDTH_SIZE = 20;
    private static final int HEIGHT_SIZE = 5;
    private int xLocal;
    private int yLocal;
    ImageIcon image;

    /**
     * Constructs a SilverCollar object with the specified position.
     *
     * @param x the x-coordinate of the collar's position
     * @param y the y-coordinate of the collar's position
     */
    public SilverCollar(int x, int y) {
        this.xLocal = x;
        this.yLocal = y;
        this.setWidth(WIDTH_SIZE);
        this.setHeight(HEIGHT_SIZE);
        setImage(this);
    }

    private void setImage(SilverCollar collar) {
        collar.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(SILVER_COLLAR_FILE_NAME)));
    }

    /**
     * Draws the silver collar on the screen.
     *
     * @param g2d the Graphics2D object to draw on
     */
    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), xLocal, yLocal, null);
    }

    /**
     * Returns the bounding rectangle of the silver collar.
     *
     * @return the bounding rectangle of the silver collar
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(xLocal, yLocal, getWidth(), getHeight());
    }
}
