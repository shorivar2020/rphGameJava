package org.game;

import lombok.extern.java.Log;

import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

/**
 * The window that will be displayed in the case of player die and lose game.
 */
@Log
public class GameOverView implements Serializable {

    /**
     * The file name of the game lose view image.
     */
    private static final String NAME_FILE = "/game_over.png";

    /**
     * The starting window value.
     */
    private static final int START_WINDOW = 0;

    /**
     * The image associated with the game lose view.
     */
    private ImageIcon image;

    /**
     * Constructs a new GameOverView object.
     */
    public GameOverView() {
        setImage(this);
    }

    /**
     * Sets the image for the gameOverView.
     *
     * @param gameOverView The GameOverView object to set the image for
     */
    public void setImage(final GameOverView gameOverView) {
        URL img = Objects.
                requireNonNull(getClass().getResource(NAME_FILE));
        gameOverView.image = new ImageIcon(img);
    }

    /**
     * Draws the gameOverView on the specified graphics context.
     *
     * @param g2d The graphics context
     */
    public void draw(final Graphics2D g2d) {
        g2d.drawImage(image.getImage(), START_WINDOW, START_WINDOW, null);
    }
}
