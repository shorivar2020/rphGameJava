package org.game.entity.item;

import org.game.entity.Entity;

import java.awt.*;
import java.io.Serializable;

public abstract class Item extends Entity implements Serializable {
    public abstract void draw(Graphics2D g2d);
}
