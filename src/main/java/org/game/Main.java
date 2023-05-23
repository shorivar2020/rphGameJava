/**
 * This is a main class of the game.
 *
 * @version 1.0
 * @author shorivar
 * @see org.game
 * @since 1.0
 */
package org.game;

import lombok.Getter;
import lombok.extern.java.Log;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.util.logging.Level;

@Log
@Getter
public class Main {
    /**
     * The entry point of the program.
     * Creates an instance of the game class,
     * sets up the game window,
     * and starts the game.
     */
    public static void main(final String[] args) {
        boolean enableLogging = args.length > 0 && args[0].equalsIgnoreCase("enableLogging");
        if (args.length > 0 && args[0].equalsIgnoreCase("true")) {
            enableLogging = true;
        }

        if (enableLogging) {
            log.log(Level.INFO, "Starting the Cat Adventure game");
        }
        Game game = new Game(enableLogging);
        JFrame frame = new JFrame("Cat Adventure");
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();
        if (enableLogging) {
            log.log(Level.INFO, "Game started");
        }
    }

}
