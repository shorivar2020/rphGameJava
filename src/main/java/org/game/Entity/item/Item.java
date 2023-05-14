package org.game.Entity.item;

import org.game.Entity.Entity;

import java.awt.*;
import java.io.Serializable;

public abstract class Item extends Entity implements Serializable {
    public String name;
    public abstract void draw(Graphics2D g2d);

}
