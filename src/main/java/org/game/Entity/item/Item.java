package org.game.Entity.item;

import org.game.Entity.Entity;
import org.game.Player;

import java.awt.*;

public abstract class Item extends Entity {
    public boolean taken = false;
    public String name;
    void use(Player player){}

    public abstract void draw(Graphics2D g2d);


}
