package org.game.Entity.enemy;

import org.game.Player;

import javax.swing.*;
import java.awt.*;

public class Rat extends Enemy {
    int health = 1;
    int damage = 1;
    int width = 25;
    int height = 25;
    int move = 0;
    int speed = 0;
    ImageIcon image;

    public Rat(int x, int y){
        this.x = x;
        this.y = y;
        setImage(this);
    }

    public void setImage(Rat e){
            e.image = new ImageIcon(getClass().getResource("/rat.png"));
    }

    public void setImageLeft(Rat e){

            e.image = new ImageIcon(getClass().getResource("/ratl.png"));


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
//        System.out.println("RAT ATTACK YOU");
        player.takeDamage(damage, this);
    }

    public void takeDamage(int damage){
//        System.out.println("YOU ATTACH RAT");
        health = health - damage;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
