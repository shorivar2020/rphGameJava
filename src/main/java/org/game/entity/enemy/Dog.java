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
 * The Dog class represents an enemy dog entity in the game.
 */
@Log
@Getter
@Setter
public class Dog extends Enemy implements Serializable {
    private static final String DOG_FILE_NAME_LEFT = "/dog1l.png";
    private static final String DOG_FILE_NAME_LEFT_2 = "/dog2l.png";
    private static final String DOG_FILE_NAME_RIGHT = "/dog1.png";
    private static final String DOG_FILE_NAME_RIGHT_2 = "/dog2.png";
    private static final int FREQUENCY = 10;
    private static final int PITCH_RANGE = 5;
    private static final int WALKING_RANGE = 10;
    private static final int SIZE = 70;
    private static final int HEALTH_VALUE = 5;
    private static final int DAMAGE_VALUE = 2;

    ImageIcon imageRight;
    ImageIcon imageRight2;
    ImageIcon imageLeft;
    ImageIcon imageLeft2;
    ImageIcon image;
    int moveCount = 0;
    boolean rightDirection;
    int move = 0;
    int speed = 0;
    int countDirection;

    /**
     * Creates a new Dog instance with the specified position.
     *
     * @param x the x-coordinate of the dog's position
     * @param y the y-coordinate of the dog's position
     */
    public Dog(int x, int y) {
        this.setX(x);
        this.setY(y);
        this.setHeight(HEALTH_VALUE);
        this.setDamage(DAMAGE_VALUE);
        this.setWidth(SIZE);
        this.setHeight(SIZE);
        setImage(this);
    }

    private void setImage(Dog dog) {
        dog.imageRight = new ImageIcon(Objects.requireNonNull(getClass().getResource(DOG_FILE_NAME_RIGHT)));
        dog.imageLeft = new ImageIcon(Objects.requireNonNull(getClass().getResource(DOG_FILE_NAME_LEFT)));
        dog.imageLeft2 = new ImageIcon(Objects.requireNonNull(getClass().getResource(DOG_FILE_NAME_LEFT_2)));
        dog.imageRight2 = new ImageIcon(Objects.requireNonNull(getClass().getResource(DOG_FILE_NAME_RIGHT_2)));
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

    @Override
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
