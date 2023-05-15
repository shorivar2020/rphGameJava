package org.game.entity.enemy;

import org.game.Player;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

public class Rat extends Enemy implements Serializable {
    private static final int FREQUENCY = 15;
    private static final int PITCH_RANGE = 5;
    private static final int WALKING_RANGE = 10;
    int move = 0;
    int speed = 0;
    ImageIcon imageRight;
    ImageIcon imageLeft;
    ImageIcon image;
    boolean rightDirection;
    int i;

    public Rat(int x, int y){
        this.x = x;
        this.y = y;
        health = 1;
        damage = 1;
        width = 25;
        height = 25;
        setImage(this);
    }

    public void setImage(Rat e){
        e.imageRight = new ImageIcon(Objects.requireNonNull(getClass().getResource("/rat.png")));
        e.imageLeft = new ImageIcon(Objects.requireNonNull(getClass().getResource("/ratl.png")));
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
    }

    public void move() {
        if(speed % FREQUENCY == 0){
            if(!rightDirection){
                i++;
                image = imageLeft;
                x += PITCH_RANGE;
                if(i == WALKING_RANGE){
                    rightDirection = true;
                    i = 0;
                }
            }else {
                i++;
                image = imageRight;
                x -= PITCH_RANGE;
                if(i == WALKING_RANGE){
                    rightDirection = false;
                    i = 0;
                }
            }
            move++;
        }
        speed++;
    }

    public void attack(Player player) {
        player.takeDamage(damage, this);
    }

    public void takeDamage(int damage){
        health = health - damage;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

}
