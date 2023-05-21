package org.game.entity.enemy;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.game.entity.Entity;
import org.game.Player;

import java.awt.*;
import java.io.Serializable;

/**
 * The Enemy class represents an abstract enemy entity in the game.
 */
@Log
@Getter
@Setter
public abstract class Enemy extends Entity implements Serializable {
    int health;
    int damage;

    /**
     * Takes the specified amount of damage.
     *
     * @param damage the amount of damage to take
     */
    public abstract void takeDamage(int damage);

    /**
     * Returns the bounding rectangle of the enemy.
     *
     * @return the bounding rectangle
     */
    public abstract Rectangle getBounds();

    /**
     * Draws the enemy on the screen.
     *
     * @param g2d the Graphics2D object to draw on
     */
    public abstract void draw(Graphics2D g2d);

    /**
     * Attacks the specified player.
     *
     * @param player the player to attack
     */
    public abstract void attack(Player player);

    /**
     * Checks if the enemy is alive.
     *
     * @return true if the enemy is alive, false otherwise
     */
    public abstract boolean isAlive();

    /**
     * Moves the enemy.
     */
    public abstract void move();
}
