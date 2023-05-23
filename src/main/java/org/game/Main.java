/**
 * This is the main class of the game.
 *
 * @version 1.0
 * @see Game
 * @since 1.0
 */
package org.game;

import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class Main {
    /**
     * The entry point of the program.
     * Creates an instance of the game class,
     * sets up the game window,
     * and starts the game.
     *
     * @param args The command-line arguments (not used).
     */
    public static void main(final String[] args) {
        boolean enableLogging = args.length > 0
                && args[0].equalsIgnoreCase("true");

        Game game = new Game(enableLogging);
        JFrame frame = new JFrame("Cat Adventure");
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.start();
    }

}
