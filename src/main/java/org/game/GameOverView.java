package org.game;

import lombok.extern.java.Log;

import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

/**
 * The window that will be displayed in the case of health reduction to 0.
 */
@Log
public class GameOverView implements Serializable {
    /**
     * The file name of the game win view image.
     */
    private static final String NAME_FILE = "/game_over.png";
    /**
     * The starting window value.
     */
    private static final int START_WINDOW = 0;

    /**
     * The image associated with the game win view.
     */
    private ImageIcon image;

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
    public void setImage(final GameOverView t) {
        URL img = Objects.
                requireNonNull(getClass().getResource(NAME_FILE));
        t.image = new ImageIcon(img);
    }

    /**
     * Draws the game over view on the specified graphics context.
     *
     * @param g2d The graphics context.
     */
    public void draw(final Graphics2D g2d) {
        g2d.drawImage(image.getImage(), START_WINDOW, START_WINDOW, null);
    }
}
