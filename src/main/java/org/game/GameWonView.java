package org.game;

import java.awt.*;

/**
 * The window that will be displayed in case the player finds a way out
 */
public class GameWonView {
    private final int x = 0;
    private final int y = 0;
    private final int width = 1200;
    private final int height = 600;
    public GameWonView(){}

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(x, y, width, height);
        g2d.drawString("You won", 0, 0);
    }
}
