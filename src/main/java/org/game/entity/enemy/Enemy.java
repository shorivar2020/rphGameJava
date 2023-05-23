/**
 * This is a class for enemies
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

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.game.Player;
import org.game.entity.Entity;


/**
 * The Enemy class is abstract for classes
 * represents an enemy entity in the game.
 */
@Log
@Getter
@Setter
public abstract class Enemy extends Entity implements Serializable {
    /**
     * Represents an enemy's health.
     */
    private int health;
    /**
     * Represents an enemy's damage.
     */
    private int damage;

    /**
     * Takes the amount of damage from player.
     *
     * @param playerDamage the amount of damage to take
     */
    public abstract void takeDamage(int playerDamage);

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
     * Attacks player.
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
