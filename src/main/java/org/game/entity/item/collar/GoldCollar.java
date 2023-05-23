package org.game.entity.item.collar;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.net.URL;
import java.util.Objects;

/**
 * The GoldCollar class represents a gold collar item in the game.
 * It extends the Collar class.
 */
@Log
@Getter
@Setter
public class GoldCollar extends Collar {
    /**
     * The file name for the gold collar texture.
     */
    private static final String GOLD_COLLAR_FILE_NAME = "/gold_collar.png";

    /**
     * The width size of the gold collar.
     */
    private static final int WIDTH_SIZE = 20;

    /**
     * The height size of the gold collar.
     */
    private static final int HEIGHT_SIZE = 5;

    /**
     * The x-coordinate for the gold collar's position.
     */
    private int xLocal;

    /**
     * The y-coordinate for the gold collar's position.
     */
    private int yLocal;

    /**
     * The image icon representing the gold collar.
     */
    private ImageIcon image;


    /**
     * Constructs a GoldCollar object with the specified position.
     *
     * @param x the x-coordinate of the collar's position
     * @param y the y-coordinate of the collar's position
     */
    public GoldCollar(final int x, final int y) {
        this.xLocal = x;
        this.yLocal = y;
        this.setWidth(WIDTH_SIZE);
        this.setHeight(HEIGHT_SIZE);
        setImage(this);
    }

    private void setImage(final GoldCollar collar) {
        URL img = Objects.
                requireNonNull(getClass().getResource(GOLD_COLLAR_FILE_NAME));
        collar.image = new ImageIcon(img);
    }

    /**
     * Draws the gold collar on the screen.
     *
     * @param g2d the Graphics2D object to draw on
     */
    @Override
    public void draw(final Graphics2D g2d) {
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
