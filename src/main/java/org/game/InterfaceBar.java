package org.game;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.game.entity.item.Item;
import org.game.entity.item.collar.Collar;
import org.game.entity.item.collar.GoldCollar;
import org.game.entity.item.collar.SilverCollar;

import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.net.URL;
import java.util.List;
import java.util.Objects;

/**
 * The interface bar that displays the inventory and health bar.
 */
@Log
@Setter
@Getter
public class InterfaceBar {
    /**
     * The size of a block in pixels.
     */
    public static final int BLOCK_SIZE = 30;

    /**
     * The starting position of the blocks.
     */
    public static final int BLOCK_START = 10;

    /**
     * The size of a heart icon in pixels.
     */
    public static final int HEART_SIZE = 25;

    /**
     * The x-coordinate of the heart bar on the game interface.
     */
    public static final int HEART_BAR_X = 0;

    /**
     * The y-coordinate of the heart bar on the game interface.
     */
    public static final int HEART_BAR_Y = 565;

    /**
     * The x-coordinate of the first heart icon on the heart bar.
     */
    public static final int HEART_START_X = 20;

    /**
     * The y-coordinate of the heart icons on the heart bar.
     */
    public static final int HEART_START_Y = HEART_BAR_Y + 5;

    /**
     * The x-coordinate of the inventory bar on the game interface.
     */
    public static final int INVENTORY_X = 0;

    /**
     * The y-coordinate of the inventory bar on the game interface.
     */
    public static final int INVENTORY_Y = 0;

    /**
     * The file name of the inventory bar image.
     */
    public static final String INVENTORY_FILE_NAME = "/inv_bar.png";

    /**
     * The file name of the heart icon image.
     */
    public static final String HEART_FILE_NAME = "/heart.png";

    /**
     * The file name of the health bar image.
     */
    public static final String HEART_PANEL_FILE_NAME = "/health_bar.png";

    /**
     * Draws the inventory bar on the graphics context.
     *
     * @param g2d              The graphics context.
     * @param showingInventory Indicates showing inventory.
     * @param player           The player object.
     */
    public void inventoryBar(final Graphics2D g2d,
                             final boolean showingInventory,
                             final Player player) {
        ImageIcon imageInv;
        URL img = Objects.
                requireNonNull(getClass().getResource(INVENTORY_FILE_NAME));
        imageInv = new ImageIcon(img);
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
    public void viewHealthBar(final Graphics2D g2d, final Player player) {
        ImageIcon imageHeart;
        ImageIcon imageBar;
        URL img = Objects.
                requireNonNull(getClass().getResource(HEART_FILE_NAME));
        imageHeart = new ImageIcon(img);
        img = Objects.
                requireNonNull(getClass().getResource(HEART_PANEL_FILE_NAME));
        imageBar = new ImageIcon(img);

        g2d.drawImage(imageBar.getImage(), HEART_BAR_X, HEART_BAR_Y, null);
        for (int i = 0, xCount = HEART_START_X;
             i < player.getHealth();
             i++, xCount += HEART_SIZE) {
            g2d.drawImage(imageHeart.getImage(), xCount, HEART_START_Y, null);
        }
    }
}
