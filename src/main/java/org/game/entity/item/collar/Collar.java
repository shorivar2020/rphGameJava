/**
 * Package of game with item type of collar.
 */
package org.game.entity.item.collar;

import org.game.entity.item.Item;

/**
 * The Collar class represents a collar item in the game.
 * It is an abstract class that extends the Item class.
 */
public abstract class Collar extends Item {

    /**
     * The health value of the character.
     */
    private int health;

    /**
     * The damage value of the character.
     */
    private int damage;

    /**
     * Returns the damage value of the collar.
     *
     * @return the damage value of the collar
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Returns the health value of the collar.
     *
     * @return the health value of the collar
     */
    public int getHealth() {
        return health;
    }

}
