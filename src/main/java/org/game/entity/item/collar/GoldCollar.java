package org.game.entity.item.collar;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * The GoldCollar class represents a gold collar item in the game.
 * It extends the Collar class.
 */
@Log
@Getter
@Setter
public class GoldCollar extends Collar {
    private static final String GOLD_COLLAR_FILE_NAME = "/gold_collar.png";
    private static final int WIDTH_SIZE = 20;
    private static final int HEIGHT_SIZE = 5;
    private int xLocal;
    private int yLocal;
    ImageIcon image;

    /**
     * Constructs a GoldCollar object with the specified position.
     *
     * @param x the x-coordinate of the collar's position
     * @param y the y-coordinate of the collar's position
     */
    public GoldCollar(int x, int y) {
        this.xLocal = x;
        this.yLocal = y;
        this.setWidth(WIDTH_SIZE);
        this.setHeight(HEIGHT_SIZE);
        setImage(this);
    }

    private void setImage(GoldCollar collar) {
        collar.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(GOLD_COLLAR_FILE_NAME)));
    }

    /**
     * Draws the gold collar on the screen.
     *
     * @param g2d the Graphics2D object to draw on
     */
    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), xLocal, yLocal, null);
    }

    /**
     * Returns the bounding rectangle of the gold collar.
     *
     * @return the bounding rectangle of the gold collar
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(xLocal, yLocal, getWidth(), getHeight());
    }
}
