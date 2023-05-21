package org.game;

import lombok.extern.java.Log;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The window that will be displayed in the case of health reduction to 0.
 */
@Log
public class GameOverView implements Serializable {

    private static final String GAME_OVER_VIEW_NAME_FILE = "/game_over.png";
    private static final int START_WINDOW = 0;
    ImageIcon image;

    /**
     * Constructs a new GameOverView object.
     */
    public GameOverView() {
        setImage(this);
    }

    /**
     * Sets the image for the game over view.
     *
     * @param t The GameOverView object to set the image for.
     */
    public void setImage(GameOverView t) {
        t.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(GAME_OVER_VIEW_NAME_FILE)));
    }

    /**
     * Draws the game over view on the specified graphics context.
     *
     * @param g2d The graphics context.
     */
    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), START_WINDOW, START_WINDOW, null);
    }
}
