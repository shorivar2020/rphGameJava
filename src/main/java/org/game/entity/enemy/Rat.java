package org.game.entity.enemy;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.game.Player;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Rat class represents an enemy rat entity in the game.
 */
@Log
@Setter
@Getter
public class Rat extends Enemy implements Serializable {
    private static final String RAT_FILE_NAME_LEFT = "/rat_left.png";
    private static final String RAT_FILE_NAME_RIGHT = "/rat.png";
    private static final int FREQUENCY = 15;
    private static final int PITCH_RANGE = 5;
    private static final int WALKING_RANGE = 10;
    private static final int SIZE = 25;
    private static final int HEALTH_VALUE = 1;
    private static final int DAMAGE_VALUE = 1;
    int move = 0;
    int speed = 0;
    ImageIcon imageRight;
    ImageIcon imageLeft;
    ImageIcon image;
    boolean rightDirection;
    int countDirection;

    /**
     * Creates a new Rat instance with the specified position.
     *
     * @param x the x-coordinate of the rat's position
     * @param y the y-coordinate of the rat's position
     */
    public Rat(int x, int y) {
        this.setX(x);
        this.setY(y);
        this.setHealth(HEALTH_VALUE);
        this.setDamage(DAMAGE_VALUE);
        this.setWidth(SIZE);
        this.setHeight(SIZE);
        setImage(this);
    }

    private void setImage(Rat rat) {
        rat.imageRight = new ImageIcon(Objects.requireNonNull(getClass().getResource(RAT_FILE_NAME_RIGHT)));
        rat.imageLeft = new ImageIcon(Objects.requireNonNull(getClass().getResource(RAT_FILE_NAME_LEFT)));
    }

    /**
     * Draws the rat on the screen.
     *
     * @param g2d the Graphics2D object to draw on
     */
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), getX(), getY(), null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public void attack(Player player) {
        player.takeDamage(damage, this);
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

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
