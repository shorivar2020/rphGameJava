/**
 * This is a class for enemies type dog.
 *
 * @version 1.0
 * @author shorivar
 * @see org.game.entity.enemy
 * @since 1.0
 */
package org.game.entity.enemy;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.Serializable;
import java.net.URL;
import java.util.Objects;
import javax.swing.ImageIcon;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.game.Player;


/**
 * The Dog class represents an enemy dog entity in the game.
 */
@Log
@Getter
@Setter
public class Dog extends Enemy implements Serializable {
    /**
     * The file name of the dog's left-facing image.
     */
    private static final String DOG_FILE_NAME_LEFT = "/dog1l.png";

    /**
     * The file name of the dog's second left-facing image.
     */
    private static final String DOG_FILE_NAME_LEFT_2 = "/dog2l.png";

    /**
     * The file name of the dog's right-facing image.
     */
    private static final String DOG_FILE_NAME_RIGHT = "/dog1.png";

    /**
     * The file name of the dog's second right-facing image.
     */
    private static final String DOG_FILE_NAME_RIGHT_2 = "/dog2.png";

    /**
     * The frequency value for the dog moving.
     */
    private static final int FREQUENCY = 10;

    /**
     * The pitch range value for the dog.
     */
    private static final int PITCH_RANGE = 5;

    /**
     * The walking range value for the dog.
     */
    private static final int WALKING_RANGE = 10;

    /**
     * The size value of the dog.
     */
    private static final int SIZE = 70;

    /**
     * The health value of the dog.
     */
    private static final int HEALTH_VALUE = 5;

    /**
     * The damage value inflicted by the dog.
     */
    private static final int DAMAGE_VALUE = 2;
    /**
     * The image icon for the dog facing right.
     */
    private ImageIcon imageRight;

    /**
     * The second image icon for the dog facing right.
     */
    private ImageIcon imageRight2;

    /**
     * The image icon for the dog facing left.
     */
    private ImageIcon imageLeft;

    /**
     * The second image icon for the dog facing left.
     */
    private ImageIcon imageLeft2;

    /**
     * The current image icon for the dog.
     */
    private ImageIcon image;

    /**
     * The count of movements made by the dog.
     */
    private int moveCount = 0;

    /**
     * Flag indicating the dog's facing direction
     * (true for right, false for left).
     */
    private boolean rightDirection;

    /**
     * The amount of movement made by the dog.
     */
    private int move = 0;

    /**
     * The speed of the dog's movement.
     */
    private int speed = 0;

    /**
     * The count of directions the dog has moved.
     */
    private int countDirection;

    /**
     * Creates a new Dog instance with the specified position.
     *
     * @param x the x-coordinate of the dog's position
     * @param y the y-coordinate of the dog's position
     */
    public Dog(final int x, final int y) {
        this.setX(x);
        this.setY(y);
        this.setHeight(HEALTH_VALUE);
        this.setDamage(DAMAGE_VALUE);
        this.setWidth(SIZE);
        this.setHeight(SIZE);
        setImage(this);
    }

    private void setImage(final Dog dog) {
        URL img = Objects
                .requireNonNull(getClass().getResource(DOG_FILE_NAME_RIGHT));
        dog.imageRight = new ImageIcon(img);
        img = Objects
                .requireNonNull(getClass().getResource(DOG_FILE_NAME_LEFT));
        dog.imageLeft = new ImageIcon(img);
        img = Objects
                .requireNonNull(getClass().getResource(DOG_FILE_NAME_LEFT_2));
        dog.imageLeft2 = new ImageIcon(img);
        img = Objects
                .requireNonNull(getClass().getResource(DOG_FILE_NAME_RIGHT_2));
        dog.imageRight2 = new ImageIcon(img);
    }

    /**
     * Changes the dog's image to the right-facing image.
     */
    public void changeImageRight() {
        if (moveCount % 2 == 0) {
            image = imageRight;
        } else {
            image = imageRight2;
        }
        moveCount++;
    }

    /**
     * Changes the dog's image to the left-facing image.
     */
    public void changeImageLeft() {
        if (moveCount % 2 == 0) {
            image = imageLeft;
        } else {
            image = imageLeft2;
        }
        moveCount++;
    }

    /**
     * Draws the dog from image.
     *
     * @param g2d The graphics context.
     */
    @Override
    public void draw(final Graphics2D g2d) {
        g2d.drawImage(image.getImage(), getX(), getY(), null);
    }

    /**
     * Retrieves the bounding rectangle of the dog.
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
        int dogHealth = getHealth();
        setHealth(dogHealth - playerDamage);
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
     * Checks if the dog is alive.
     *
     * @return true if the health is not over yet, false otherwise
     */
    @Override
    public boolean isAlive() {
        return getHealth() > 0;
    }

    /**
     * Moving the dog from side to side
     * with the change of direction of the picture.
     */
    @Override
    public void move() {
        if (speed % FREQUENCY == 0) {
            countDirection++;
            int xLocal = this.getX();
            this.setX(xLocal);
            if (!rightDirection) {
                changeImageLeft();
                xLocal += PITCH_RANGE;
                if (countDirection == WALKING_RANGE) {
                    rightDirection = true;
                    countDirection = 0;
                }
            } else {
                changeImageRight();
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
