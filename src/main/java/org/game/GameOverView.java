package org.game;

import org.game.Entity.Entity;
import org.game.Entity.item.Key;

import javax.swing.*;
import java.awt.*;

/**
 * The window that will be displayed in the case of health reduction to 0
 */
public class GameOverView{
    private int x = 0;
    private int y = 0;

    ImageIcon image;
    public GameOverView(){
        setImage(this);
    }

    public void setImage(GameOverView t){
        t.image = new ImageIcon(getClass().getResource("/game_over.png"));
    }
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
    }
}
