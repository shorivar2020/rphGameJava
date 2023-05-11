package org.game.Entity.enemy;

import org.game.Player;

import java.awt.*;

public class Dog extends Enemy {
    int health = 2;
    int damage = 3;
    int width = 50;
    int height = 50;
    int move = 0;
    int speed = 0;
    public Dog(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.black);
        g2d.fillRect(x, y, width, height);
    }

    public void move(int x_d, int y_d) {
            if(speed % 10 == 0){
                if(move%2==0){
                    x += x_d;
                    y += y_d;
                }else{
                    x -= x_d;
                    y -= y_d;
                }
                move++;
            }
            speed++;
    }

    public void attack(Player player) {
        System.out.println("DOG ATTACK YOU");
        player.takeDamage(damage, this);
    }

    public void takeDamage(int damage){
        System.out.println("YOU ATTACH DOG");
        health = health - damage;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
