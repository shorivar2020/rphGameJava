/**
 * Objects, that interact with player
 *
 * @version 1.0
 * @see Item
 * @since 1.0
 */
package org.game.entity.item;

import org.game.entity.Entity;
import lombok.extern.java.Log;

import java.io.Serializable;

/**
 * The Item class represents an item in the game.
 * It extends the Entity class and implements Serializable.
 */
@Log
public abstract class Item extends Entity implements Serializable {
}
