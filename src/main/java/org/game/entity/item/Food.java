package org.game.entity.item;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Food class represents a food item in the game.
 * It extends the Item class and implements Serializable.
 */
@Log
@Setter
@Getter
public class Food extends Item implements Serializable {
    private static final String FOOD_FILE_NAME = "/food.png";
    private static final int SIZE = 30;
    public static final int VALUE = 1;
    ImageIcon image;
    int foodValue;

    /**
     * Constructs a Food object with the specified position.
     *
     * @param x the x-coordinate of the food's position
     * @param y the y-coordinate of the food's position
     */
    public Food(int x, int y) {
        this.setX(x);
        this.setY(y);
        this.setWidth(SIZE);
        this.setHeight(SIZE);
        this.setFoodValue(VALUE);
        setImage(this);
    }

    private void setImage(Food food) {
        food.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(FOOD_FILE_NAME)));
    }

    /**
     * Draws the food on the screen.
     *
     * @param g2d the Graphics2D object to draw on
     */
    public void draw(Graphics2D g2d) {
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
