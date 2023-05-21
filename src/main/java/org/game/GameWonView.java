package org.game;

import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The window that will be displayed when the player wins the game.
 */
@Log
public class GameWonView implements Serializable {
    private static final String GAME_WON_VIEW_NAME_FILE = "/game_win.png";
    private static final int START_WINDOW = 0;
    ImageIcon image;

    /**
     * Constructs a new GameWonView object.
     */
    public GameWonView() {
        setImage(this);
    }

    /**
     * Sets the image for the game won view.
     *
     * @param t The GameWonView object to set the image for.
     */
    public void setImage(GameWonView t) {
        t.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(GAME_WON_VIEW_NAME_FILE)));
    }

    /**
     * Draws the game won view on the specified graphics context.
     *
     * @param g2d The graphics context.
     */
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), START_WINDOW, START_WINDOW, null);
    }
}
