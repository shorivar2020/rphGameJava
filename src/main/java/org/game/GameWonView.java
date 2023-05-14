package org.game;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * The window that will be displayed in case the player finds a way out
 */
public class GameWonView {
    private static final int x = 0;
    private static final int y = 0;
    ImageIcon image;
    public GameWonView(){
        setImage(this);
    }
    public void setImage(GameWonView t){
        t.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game_win.png")));
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
    }
}
