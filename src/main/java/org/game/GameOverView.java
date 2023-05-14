package org.game;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * The window that will be displayed in the case of health reduction to 0
 */
public class GameOverView{
    private static final int x = 0;
    private static final int y = 0;

    ImageIcon image;
    public GameOverView(){
        setImage(this);
    }

    public void setImage(GameOverView t){
        t.image = new ImageIcon(Objects.requireNonNull(getClass().getResource("/game_over.png")));
    }
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
    }
}
