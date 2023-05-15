package org.game;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The window that will be displayed in case the player finds a way out
 */
public class GameWonView implements Serializable {

    ImageIcon image;
    public GameWonView(){
        setImage(this);
    }
    public void setImage(GameWonView t){
        t.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game_win.png")));
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), 0, 0, null);
    }
}
