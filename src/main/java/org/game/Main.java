package org.game;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.swing.*;
import java.util.logging.Level;

@Log
@Getter
@Setter
public class Main {
    /**
     * The entry point of the program.
     * Creates an instance of the Game class, sets up the game window, and starts the game.
     *
     * @param args The command-line arguments (not used).
     */
    public static void main(String[] args) {
        log.log(Level.INFO, "Starting the Cat Adventure game");
        Game game = new Game();
        JFrame frame = new JFrame("Cat Adventure");
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();
        log.log(Level.INFO, "Game started");
    }

}
