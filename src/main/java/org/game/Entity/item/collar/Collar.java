package org.game.Entity.item.collar;

import org.game.Entity.item.Item;

public abstract class Collar extends Item {
    int health;
    int damage;

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }
}
