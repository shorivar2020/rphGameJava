/**
 * This is a class for enemies type rat
 *
 * @version 1.0
 * @author shorivar
 * @see org.game.entity.enemy
 * @since 1.0
 */
package org.game.entity.enemy;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.game.Player;

import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

/**
 * The Rat class represents an enemy rat entity in the game.
 */
@Log
@Setter
@Getter
public class Rat extends Enemy implements Serializable {
    /**
     * The file name of the rat's right-facing image.
     */
    private static final String RAT_FILE_NAME_LEFT = "/rat_left.png";
    /**
     * The file name of the rat's second right-facing image.
     */
    private static final String RAT_FILE_NAME_RIGHT = "/rat.png";
    /**
     * The frequency value for the rat.
     */
    private static final int FREQUENCY = 15;
    /**
     * The pitch range value for the rat.
     */
    private static final int PITCH_RANGE = 5;
    /**
     * The walking range value for the rat.
     */
    private static final int WALKING_RANGE = 10;
    /**
     * The size value of the rat.
     */
    private static final int SIZE = 25;
    /**
     * The health value of the rat.
     */
    private static final int HEALTH_VALUE = 1;
    /**
     * The damage value inflicted by the rat.
     */
    private static final int DAMAGE_VALUE = 1;
    /**
     * The amount of movement made by the rat.
     */
    private int move = 0;
    /**
     * The speed of the rat's movement.
     */
    private int speed = 0;
    /**
     * The image icon for the rat facing right.
     */
    private ImageIcon imageRight;
    /**
     * The image icon for the rat facing left.
     */
    private ImageIcon imageLeft;
    /**
     * The current image icon for the rat.
     */
    private ImageIcon image;
    /**
     * Flag indicating the rat's facing direction
     * (true for right, false for left).
     */
    private boolean rightDirection;
    /**
     * The count of directions the rat has moved.
     */
    private int countDirection;

    /**
     * Creates a new Rat instance with the specified position.
     *
     * @param x the x-coordinate of the rat's position
     * @param y the y-coordinate of the rat's position
     */
    public Rat(final int x, final int y) {
        this.setX(x);
        this.setY(y);
        this.setHealth(HEALTH_VALUE);
        this.setDamage(DAMAGE_VALUE);
        this.setWidth(SIZE);
        this.setHeight(SIZE);
        setImage(this);
    }

    private void setImage(final Rat rat) {
        URL img = Objects.
                requireNonNull(getClass().getResource(RAT_FILE_NAME_RIGHT));
        rat.imageRight = new ImageIcon(img);
        img = Objects.
                requireNonNull(getClass().getResource(RAT_FILE_NAME_LEFT));
        rat.imageLeft = new ImageIcon(img);
    }

    /**
     * Draws the rat on the screen.
     *
     * @param g2d the Graphics2D object to draw on
     */
    public void draw(final Graphics2D g2d) {
        g2d.drawImage(image.getImage(), getX(), getY(), null);
    }

    /**
     * Retrieves the bounding rectangle of the rat.
     *
     * @return The bounding rectangle.
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Takes the amount of damage from player.
     *
     * @param playerDamage the amount of damage to take
     * @see Player
     */
    @Override
    public void takeDamage(final int playerDamage) {
        int ratHealth = getHealth();
        setHealth(ratHealth - playerDamage);
    }

    /**
     * Attacks player by method in class Player.
     *
     * @param player the player to attack
     * @see Player
     */
    @Override
    public void attack(final Player player) {
        player.takeDamage(getDamage(), this);
    }

    /**
     * Checks if the rat is alive.
     *
     * @return true if the health is not over yet, false otherwise
     */
    @Override
    public boolean isAlive() {
        return getHealth() > 0;
    }

    /**
     * Moving the rat from side to side
     * with the change of direction of the picture.
     */
    @Override
    public void move() {
        if (speed % FREQUENCY == 0) {
            countDirection++;
            int xLocal = this.getX();
            this.setX(xLocal);
            if (!rightDirection) {
                image = imageLeft;
                xLocal += PITCH_RANGE;
                if (countDirection == WALKING_RANGE) {
                    rightDirection = true;
                    countDirection = 0;
                }
            } else {
                image = imageRight;
                xLocal -= PITCH_RANGE;
                if (countDirection == WALKING_RANGE) {
                    rightDirection = false;
                    countDirection = 0;
                }
            }
            this.setX(xLocal);
            move++;
        }
        speed++;
    }

}
