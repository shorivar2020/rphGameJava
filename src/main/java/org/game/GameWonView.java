package org.game;

import lombok.extern.java.Log;

import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

/**
 * The window that will be displayed when the player wins the game.
 */
@Log
public class GameWonView implements Serializable {

    /**
     * The file name of the game win view image.
     */
    private static final String GAME_WON_VIEW_NAME_FILE = "/game_win.png";

    /**
     * The starting window value.
     */
    private static final int START_WINDOW = 0;

    /**
     * The image associated with the game win view.
     */
    private ImageIcon image;

    /**
     * Constructs a new GameWonView object.
     */
    public GameWonView() {
        setImage(this);
    }

    /**
     * Sets the image for the gameWonView.
     *
     * @param gameWonView The GameWonView object to set the image for
     */
    public void setImage(final GameWonView gameWonView) {
        URL img = Objects.
                requireNonNull(getClass().getResource(GAME_WON_VIEW_NAME_FILE));
        gameWonView.image = new ImageIcon(img);
    }

    /**
     * Draws the gameWonView on the specified graphics context.
     *
     * @param g2d The graphics context
     */
    public void draw(final Graphics2D g2d) {
        g2d.drawImage(image.getImage(), START_WINDOW, START_WINDOW, null);
    }
}
