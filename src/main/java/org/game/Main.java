package org.game;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame("Cat Adventure");
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();
    }

}
