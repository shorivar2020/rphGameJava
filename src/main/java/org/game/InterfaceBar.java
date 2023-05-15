package org.game;

import lombok.extern.java.Log;
import org.game.entity.item.Item;
import org.game.entity.item.collar.Collar;
import org.game.entity.item.collar.GoldCollar;
import org.game.entity.item.collar.SilverCollar;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

@Log
public class InterfaceBar {

    public void inventoryBar(Graphics2D g2d, boolean showingInventory, Player player){
        ImageIcon imageInv;
        imageInv = new ImageIcon(Objects.requireNonNull(getClass().getResource("/inv_bar.png")));
        if (showingInventory) {
            g2d.drawImage(imageInv.getImage(), 0, 0, null);
            List<Item> inventoryItems = player.getInventory();
            int x = 10;
            int y = 10;
            for (Item item : inventoryItems) {
                item.x = x;
                item.y = y;
                item.draw(g2d);
                x += 30;
                if(item instanceof Collar){
                    if(item instanceof SilverCollar silverCollar){
                        silverCollar.xLocal = x;
                        silverCollar.yLocal = y;
                    }
                    if(item instanceof GoldCollar goldCollar){
                        goldCollar.xLocal = x;
                        goldCollar.yLocal = y;
                    }
                    x += 30;
                }
            }
        }
        //HEALTH PANEL
        ImageIcon imageHeart;
        ImageIcon imageBar;
        imageHeart = new ImageIcon(Objects.requireNonNull(getClass().getResource("/heart.png")));
        imageBar = new ImageIcon(Objects.requireNonNull(getClass().getResource("/health_bar.png")));

        g2d.drawImage(imageBar.getImage(), 0, 565, null);
        for(int i = 0, x_count = 20; i < player.getHealth(); i++, x_count += 25){
            g2d.drawImage(imageHeart.getImage(), x_count, 570, null);
        }
    }
    public void saveItemsInFile(List<Item> itList){
        ObjectOutputStream out;
        try {
            try(FileOutputStream fileOut = new FileOutputStream("items.txt")){
                out = new ObjectOutputStream(fileOut);
                out.writeObject(itList);
                out.close();
            }
            log.log(Level.FINE, "Items save in file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
