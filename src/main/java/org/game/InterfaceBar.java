package org.game;

import org.game.Entity.item.Item;
import org.game.Entity.item.collar.Collar;
import org.game.Entity.item.collar.GoldCollar;
import org.game.Entity.item.collar.SilverCollar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;

public class InterfaceBar {
    public InterfaceBar(){

    }

    public void inventoryBar(Graphics2D g2d, boolean showingInventory, Player player){
        BufferedImage imageInv;
        try {
            imageInv = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/inv_bar.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (showingInventory) {
            g2d.drawImage(imageInv, 0, 0, null);
            List<Item> inventoryItems = player.inventory;
            int x = 10;
            int y = 10;
            for (Item item : inventoryItems) {
                item.x = x;
                item.y = y;
                item.draw(g2d);
                x += 30;
                if(item instanceof Collar){
                    if(item instanceof SilverCollar){
                        ((SilverCollar) item).x_c = x;
                        ((SilverCollar) item).y_c = y;
                    }
                    if(item instanceof GoldCollar){
                        ((GoldCollar) item).x_c = x;
                        ((GoldCollar) item).y_c = y;
                    }
                    x += 30;
                }
            }
        }
        //HEALTH PANEL
        int x_count = 20;
        BufferedImage imageHeart;
        BufferedImage imageBar;
        try {
            imageHeart = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/heart.png")));
            imageBar = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/health_bar.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2d.drawImage(imageBar, 0, 565, null);
        for(int i = 0; i < player.health; i++){
            g2d.drawImage(imageHeart, x_count, 570, null);
            x_count += 25;
        }
    }
    public void saveItemsInFile(List<Item> itList){
        try {
            FileOutputStream fileOut = new FileOutputStream("items.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(itList);
            out.close();
            fileOut.close();
            System.out.println("Items save in file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
