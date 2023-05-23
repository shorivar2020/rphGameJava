/**
 * Objects, that interact with player
 *
 * @version 1.0
 * @see Item
 * @since 1.0
 */
package org.game.entity.item;

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
 * The Food class represents food in the game.
 * It extends the Item class.
 */
@Log
@Setter
@Getter
public class Food extends Item implements Serializable {

    /**
     * The file name for the food texture.
     */
    private static final String FILE_NAME = "/food.png";

    /**
     * The size of the food item.
     */
    private static final int SIZE = 30;

    /**
     * The value associated with the food item.
     */
    public static final int VALUE = 1;

    /**
     * The image icon representing the food item.
     */
    private ImageIcon image;

    /**
     * The value of the food item.
     */
    private int foodValue;

    /**
     * Constructs a Food object with the specified position.
     *
     * @param x the x-coordinate of the food's position
     * @param y the y-coordinate of the food's position
     */
    public Food(final int x, final int y) {
        this.setX(x);
        this.setY(y);
        this.setWidth(SIZE);
        this.setHeight(SIZE);
        this.setFoodValue(VALUE);
        setImage(this);
    }

    private void setImage(final Food food) {
        URL img = Objects.
                requireNonNull(getClass().getResource(FILE_NAME));
        food.image = new ImageIcon(img);
    }

    /**
     * Draws the food on the screen.
     *
     * @param g2d the Graphics2D object to draw on
     */
    public void draw(final Graphics2D g2d) {
        g2d.drawImage(image.getImage(), getX(), getY(), null);
    }

    /**
     * Returns the bounding rectangle of the food.
     *
     * @return the bounding rectangle of the food
     */
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}
