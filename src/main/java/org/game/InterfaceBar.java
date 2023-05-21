package org.game;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.game.entity.item.Item;
import org.game.entity.item.collar.Collar;
import org.game.entity.item.collar.GoldCollar;
import org.game.entity.item.collar.SilverCollar;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

/**
 * The interface bar that displays the inventory and health bar.
 */
@Log
@Setter
@Getter
public class InterfaceBar {
    private static final int BLOCK_SIZE = 30;
    private static final int BLOCK_START = 10;
    private static final int HEART_SIZE = 25;
    private static final int HEART_BAR_X = 0;
    private static final int HEART_BAR_Y = 565;
    private static final int HEART_START_X = 20;
    private static final int HEART_START_Y = HEART_BAR_Y + 5;
    private static final int INVENTORY_X = 0;
    private static final int INVENTORY_Y = 0;
    private static final String INVENTORY_FILE_NAME = "/inv_bar.png";
    private static final String HEART_FILE_NAME = "/heart.png";
    private static final String HEART_PANEL_FILE_NAME = "/health_bar.png";

    /**
     * Draws the inventory bar on the graphics context.
     *
     * @param g2d              The graphics context.
     * @param showingInventory Indicates whether the inventory is currently being shown.
     * @param player           The player object.
     */
    public void inventoryBar(Graphics2D g2d, boolean showingInventory, Player player) {
        ImageIcon imageInv;
        imageInv = new ImageIcon(Objects.requireNonNull(getClass().getResource(INVENTORY_FILE_NAME)));
        if (showingInventory) {
            g2d.drawImage(imageInv.getImage(), INVENTORY_X, INVENTORY_Y, null);
            List<Item> inventoryItems = player.getInventory();
            int x = BLOCK_START;
            int y = BLOCK_START;
            for (Item item : inventoryItems) {
                //Change location from map to inventory
                item.setX(x);
                item.setY(y);
                item.draw(g2d);
                x += BLOCK_SIZE;
                if (item instanceof Collar) {
                    if (item instanceof SilverCollar silverCollar) {
                        silverCollar.setXLocal(x);
                        silverCollar.setYLocal(y);
                    }
                    if (item instanceof GoldCollar goldCollar) {
                        goldCollar.setXLocal(x);
                        goldCollar.setYLocal(y);
                    }
                    x += BLOCK_SIZE;
                }
            }
        }
        viewHealthBar(g2d, player);
    }

    /**
     * Draws the health bar on the graphics context.
     *
     * @param g2d    The graphics context.
     * @param player The player object.
     */
    public void viewHealthBar(Graphics2D g2d, Player player) {
        ImageIcon imageHeart;
        ImageIcon imageBar;
        imageHeart = new ImageIcon(Objects.requireNonNull(getClass().getResource(HEART_FILE_NAME)));
        imageBar = new ImageIcon(Objects.requireNonNull(getClass().getResource(HEART_PANEL_FILE_NAME)));

        g2d.drawImage(imageBar.getImage(), HEART_BAR_X, HEART_BAR_Y, null);
        for (int i = 0, x_count = HEART_START_X; i < player.getHealth(); i++, x_count += HEART_SIZE) {
            g2d.drawImage(imageHeart.getImage(), x_count, HEART_START_Y, null);
        }
    }
}
