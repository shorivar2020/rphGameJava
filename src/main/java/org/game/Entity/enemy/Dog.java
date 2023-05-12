package org.game.Entity.enemy;

import org.game.Player;

import javax.swing.*;
import java.awt.*;

public class Dog extends Enemy {
    int health = 2;
    int damage = 3;
    int width = 70;
    int height = 70;
    int move = 0;
    int speed = 0;
    ImageIcon image;
    int move_count = 0;
    public Dog(int x, int y){
        this.x = x;
        this.y = y;
        setImage(this);
    }

    public void setImage(Dog e){
        if(move_count % 2 == 0){
            e.image = new ImageIcon(getClass().getResource("/dog1.png"));

        }else{
            e.image = new ImageIcon(getClass().getResource("/dog2.png"));

        }
        move_count++;
    }
    public void setImageLeft(Dog e){
        if(move_count % 2 == 0){
            e.image = new ImageIcon(getClass().getResource("/dog1l.png"));

        }else{
            e.image = new ImageIcon(getClass().getResource("/dog2l.png"));

        }
        move_count++;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
    }

    public void move(int x_d, int y_d) {
            if(speed % 10 == 0){
                if(move%2==0){
                    setImage(this);
                    x += x_d;
                    y += y_d;
                }else{
                    setImageLeft(this);
                    x -= x_d;
                    y -= y_d;
                }
                move++;
            }
            speed++;
    }

    public void attack(Player player) {
//        System.out.println("DOG ATTACK YOU");
        player.takeDamage(damage, this);
    }

    public void takeDamage(int damage){
//        System.out.println("YOU ATTACH DOG");
        health = health - damage;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
