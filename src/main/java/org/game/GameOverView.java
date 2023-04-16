package org.game;

import org.game.Entity.Entity;

import java.awt.*;

/**
 * The window that will be displayed in the case of health reduction to 0
 */
public class GameOverView{
    private int x = 0;
    private int y = 0;
    private int width = 1200;
    private int height = 600;
    public GameOverView(){

    }
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(x, y, width, height);
        g2d.drawString("You lose", 0, 0);
    }
}
