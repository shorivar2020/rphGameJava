package org.game.entity.enemy;

import org.game.Player;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

public class Dog extends Enemy implements Serializable {
    private static final int FREQUENCY = 10;
    private static final int PITCH_RANGE = 5;
    private static final int WALKING_RANGE = 10;

    ImageIcon imageRight;
    ImageIcon imageRight2;
    ImageIcon imageLeft;
    ImageIcon imageLeft2;
    ImageIcon image;
    int moveCount = 0;
    boolean rightDirection;
    int move;
    int speed;
    int i;
    public Dog(int x, int y){
        this.x = x;
        this.y = y;
        health = 5;
        damage = 2;
        width = 70;
        height = 70;
        move = 0;
        speed = 0;
        setImage(this);
    }

    public void setImage(Dog e){
        e.imageRight = new ImageIcon(Objects.requireNonNull(getClass().getResource("/dog1.png")));
        e.imageLeft = new ImageIcon(Objects.requireNonNull(getClass().getResource("/dog1l.png")));
        e.imageLeft2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/dog2l.png")));
        e.imageRight2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/dog2.png")));
    }

    public void changeImageRight(){
        if(moveCount % 2 == 0){
            image = imageRight;
        }else{
            image = imageRight2;
        }
        moveCount++;
    }

    public void changeImageLeft(){
        if(moveCount % 2 == 0){
            image = imageLeft;
        }else{
            image = imageLeft2;
        }
        moveCount++;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
    }

    public void move() {
        if(speed % FREQUENCY == 0){
            if(!rightDirection){
                i++;
                changeImageLeft();
                x += PITCH_RANGE;
                if(i == WALKING_RANGE){
                    rightDirection = true;
                    i = 0;
                }
            }else {
                i++;
                changeImageRight();
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
