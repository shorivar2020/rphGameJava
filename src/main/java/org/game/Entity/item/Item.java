package org.game.Entity.item;

import org.game.Entity.Entity;
import org.game.Player;

import java.awt.*;
import java.io.Serializable;

public abstract class Item extends Entity implements Serializable {
    public boolean taken = false;
    public String name;
    void use(Player player){}

    public abstract void draw(Graphics2D g2d);


}
