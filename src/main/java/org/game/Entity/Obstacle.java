package org.game.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Obstacle extends Entity{
    BufferedImage image;
    public Obstacle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setImage(this);
    }

    public void setImage(Obstacle o){
        try {
            o.image = ImageIO.read(getClass().getResourceAsStream("/brics.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2d) {
//        g2d.setColor(Color.lightGray);
//        g2d.fillRect(x, y, width, height);
        g2d.drawImage(image, x, y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
