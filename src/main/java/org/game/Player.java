package org.game;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.game.entity.Door;
import org.game.entity.Exit;
import org.game.entity.Obstacle;
import org.game.entity.TrashCan;
import org.game.entity.enemy.Enemy;
import org.game.entity.item.Food;
import org.game.entity.item.Item;
import org.game.entity.item.Key;
import org.game.entity.item.collar.BasicCollar;
import org.game.entity.item.collar.Collar;
import org.game.entity.item.collar.GoldCollar;
import org.game.entity.item.collar.SilverCollar;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;

/**
 * The player class is controlled through the keyboards by the user.
 * Moves around the map. Interacts with objects.
 */
@Log
@Getter
@Setter
public class Player implements Serializable {
    private static final String FILE_NAME_OF_INVENTORY = "items.txt";
    private static final String FILE_NAME_OF_PLAYER_RIGHT_1 = "/cat1.png";
    private static final String FILE_NAME_OF_PLAYER_RIGHT_2 = "/cat2.png";
    private static final String FILE_NAME_OF_PLAYER_LEFT_1 = "/cat1l.png";
    private static final String FILE_NAME_OF_PLAYER_LEFT_2 = "/cat2l.png";
    private static final String FILE_NAME_OF_PLAYER_UP = "/cat_a1.png";
    private static final String FILE_NAME_OF_PLAYER_DOWN = "/cat_b.png";

    private static final int START_HEALTH = 9;
    private static final int START_DAMAGE = 5;
    public static final int SIZE = 50;
    public static final int SPEED = 5;

    private int x;
    private int y;
    private int health;
    private int damage;
    private List<Item> inventory;
    //Collision checker
    private boolean leftCollision;
    private boolean rightCollision;
    private boolean upCollision;
    private boolean downCollision;
    private boolean doorCollision;
    int moveCount = 0;
    Rectangle newBounds;
    ImageIcon image;
    Random r;

    /**
     * Constructs a Player object with the specified initial position.
     *
     * @param x The x-coordinate of the player's position.
     * @param y The y-coordinate of the player's position.
     */
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        health = START_HEALTH;
        damage = START_DAMAGE;
        inventory = takeStartInventory();
        setImage(this);
    }

    /**
     * Checks if the player collides with obstacles, doors, or the exit
     * and performs the corresponding actions.
     *
     * @param game      The current game object.
     * @param obstacles The list of obstacles in the game.
     * @param doors     The list of doors in the game.
     * @param exit      The exit object in the game.
     * @return True if the player can move to the new position, false otherwise.
     */
    private boolean checkObstacle(Game game, List<Obstacle> obstacles, List<Door> doors, Exit exit, Rectangle newBounds) {
        leftCollision = false;
        rightCollision = false;
        upCollision = false;
        downCollision = false;
        doorCollision = false;
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getBounds().intersects(newBounds)) {
                log.log(Level.FINE, "Collision with obstacle");
                interactWithObstacles(obstacle);
                return false;
            }
        }
        for (Door door : doors) {
            if (door.getBounds().intersects(newBounds)) {
                log.log(Level.FINE, "Collision with door");
                return !interactWithDoor(door);
            }
        }
        if (exit.getBounds().intersects(newBounds)) {
            log.log(Level.FINE, "Player find exit");
            List<Item> itList = new ArrayList<>();
            itList.add(new BasicCollar());
            saveItemsInFile(itList);
            game.winGame();
        }
        return true;
    }

    /**
     * Checks if the player collides with items, trash cans, or enemies
     * and performs the corresponding actions.
     *
     * @param game      The current game object.
     * @param items     The list of items in the game.
     * @param trashCans The list of trash cans in the game.
     * @param enemies   The list of enemies in the game.
     */
    public void checkItemCollision(Game game, List<Item> items, List<TrashCan> trashCans, List<Enemy> enemies) {
        newBounds = new Rectangle(x - SPEED, y, SIZE, SIZE);
        for (Item item : items) {
            if (item instanceof Key key && (key.getBounds().intersects(newBounds))) {
                log.log(Level.FINE, "Player pick up key");
                findKey(game, key);
                return;
            }
            if (item instanceof Food food && (food.getBounds().intersects(newBounds))) {
                log.log(Level.FINE, "Player pick up food");
                findFood(game, food);
                return;
            }
            if (item instanceof SilverCollar silverCollar && ((silverCollar).getBounds().intersects(newBounds))) {
                log.log(Level.FINE, "Player pick up silver collar");
                findSilverCollar(game, silverCollar);
                return;
            }
            if (item instanceof GoldCollar goldCollar && ((goldCollar).getBounds().intersects(newBounds))) {
                log.log(Level.FINE, "Player pick up gold collar");
                findGoldCollar(game, goldCollar);
                return;
            }
        }
        checkTrashCanCollision(trashCans);
        checkEnemyCollision(game, enemies);
    }
    /**
     * Checks for collision between the player and trash cans and performs the corresponding actions.
     *
     * @param trashCans The list of trash cans in the game.
     */
    public void checkTrashCanCollision(List<TrashCan> trashCans) {
        for (TrashCan trashCan : trashCans) {
            if (trashCan.getBounds().intersects(newBounds)) {
                log.log(Level.FINE, "Player find in trashcan");
                for (Item item : trashCan.getContent()) {
                    log.log(Level.FINE, "Add new item in inventory");
                    inventory.add(item);
                    if (item instanceof Collar collar) {
                        wear(collar);
                    }
                    if (item instanceof Food food) {
                        eat(food);
                    }
                }
                log.log(Level.FINE, "Clear trashcan");
                trashCan.setContent(new ArrayList<>());
            }
        }
    }
    /**
     * Checks if the player collides with an enemy and performs the corresponding actions.
     *
     * @param game    The current game object.
     * @param enemies The list of enemies in the game.
     */
    public void checkEnemyCollision(Game game, List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy.getBounds().intersects(newBounds)) {
                log.log(Level.FINE, "Player interact with enemy");
                enemy.attack(this);
                if (!enemy.isAlive()) {
                    log.log(Level.FINE, "Remove enemy from map");
                    List<Enemy> enemyList = game.getEnemies();
                    enemyList.remove(enemy);
                    game.setEnemies(enemyList);
                }
                if (health < 1) {
                    log.log(Level.FINE, "Player die, game was finished");
                    saveItemsInFile(inventory);
                    game.loseGame();
                }
                return;
            }
        }
    }

    /**
     * Moves the player to the left and checks for collisions with obstacles, doors, and the exit.
     *
     * @param game      The current game object.
     * @param obstacles The list of obstacles in the game.
     * @param doors     The list of doors in the game.
     * @param exit      The exit object in the game.
     */
    public void moveLeft(Game game, List<Obstacle> obstacles, List<Door> doors, Exit exit) {
        setImageL(this);
        if (checkObstacle(game, obstacles, doors, exit, new Rectangle(x - SPEED, y, SIZE, SIZE))) {
            x -= SPEED;
            log.log(Level.FINEST, "Change positioning of the player left");
        } else {
            if (!leftCollision && !doorCollision) {
                x -= SPEED;
                log.log(Level.FINEST, "Change positioning of the player left");
            }
        }
    }

    /**
     * Moves the player to the right and checks for collisions with obstacles, doors, and the exit.
     *
     * @param game      The current game object.
     * @param obstacles The list of obstacles in the game.
     * @param doors     The list of doors in the game.
     * @param exit      The exit object in the game.
     */
    public void moveRight(Game game, List<Obstacle> obstacles, List<Door> doors, Exit exit) {
        setImage(this);
        if (checkObstacle(game, obstacles, doors, exit, new Rectangle(x - SPEED, y, SIZE, SIZE))) {
            x += SPEED;
            log.log(Level.FINEST, "Change positioning of the player right");
        } else {
            if (!rightCollision) {
                x += SPEED;
                log.log(Level.FINEST, "Change positioning of the player right");
            }
        }

    }
    /**
     * Moves the player up and checks for collisions with obstacles, doors, and the exit.
     *
     * @param game      The current game object.
     * @param obstacles The list of obstacles in the game.
     * @param doors     The list of doors in the game.
     * @param exit      The exit object in the game.
     */
    public void moveUp(Game game, List<Obstacle> obstacles, List<Door> doors, Exit exit) {
        setImageB(this);
        if (checkObstacle(game, obstacles, doors, exit, new Rectangle(x - SPEED, y, SIZE, SIZE))) {
            y -= SPEED;
            log.log(Level.FINEST, "Change positioning of the player up");
        } else {
            if (!upCollision && !doorCollision) {
                y -= SPEED;
                log.log(Level.FINEST, "Change positioning of the player up");
            }
        }
    }
    /**
     * Moves the player down and checks for collisions with obstacles, doors, and the exit.
     *
     * @param game      The current game object.
     * @param obstacles The list of obstacles in the game.
     * @param doors     The list of doors in the game.
     * @param exit      The exit object in the game.
     */
    public void moveDown(Game game, List<Obstacle> obstacles, List<Door> doors, Exit exit) {
        setImageA(this);
        if (checkObstacle(game, obstacles, doors, exit, new Rectangle(x - SPEED, y, SIZE, SIZE))) {
            y += SPEED;
            log.log(Level.FINEST, "Change positioning of the player down");
        } else {
            if (!downCollision) {
                y += SPEED;
                log.log(Level.FINEST, "Change positioning of the player down");
            }
        }

    }
    /**
     * Retrieves the bounding rectangle of the player.
     *
     * @return The bounding rectangle of the player.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }
    /**
     * Sets the image of the player when facing right.
     *
     * @param e The player object.
     */
    public void setImage(Player e) {
        if (moveCount % 2 == 0) {
            e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(FILE_NAME_OF_PLAYER_RIGHT_1)));
            log.log(Level.FINER, "Set image of player №1 right");
        } else {
            e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(FILE_NAME_OF_PLAYER_RIGHT_2)));
            log.log(Level.FINER, "Set image of player №2 right");
        }
        moveCount++;
    }
    /**
     * Sets the image of the player when facing up.
     *
     * @param e The player object.
     */
    public void setImageA(Player e) {
        e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(FILE_NAME_OF_PLAYER_UP)));
        log.log(Level.FINER, "Set image of player up");
    }
    /**
     * Sets the image of the player when facing down.
     *
     * @param e The player object.
     */
    public void setImageB(Player e) {
        e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(FILE_NAME_OF_PLAYER_DOWN)));
        log.log(Level.FINER, "Set image of player down");
    }
    /**
     * Sets the image of the player when facing left.
     *
     * @param e The player object.
     */
    public void setImageL(Player e) {
        if (moveCount % 2 == 1) {
            e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(FILE_NAME_OF_PLAYER_LEFT_1)));
            log.log(Level.FINER, "Set image of player №1 left");
        } else {
            e.image = new ImageIcon(Objects.requireNonNull(getClass().getResource(FILE_NAME_OF_PLAYER_LEFT_2)));
            log.log(Level.FINER, "Set image of player №2 left");
        }
        moveCount++;
    }
    /**
     * Draws the player on the screen.
     *
     * @param g2d The graphics context.
     */

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
    }
    /**
     * Wears the given collar, adding its health and damage values to the player.
     *
     * @param collar The collar item to be worn.
     */
    void wear(Collar collar) {
        log.log(Level.FINE, "Player use collar and add health, damage");
        health += collar.getHealth();
        damage += collar.getDamage();
    }
    /**
     * Eats the given food item, increasing the player's health.
     *
     * @param food The food item to be eaten.
     */
    void eat(Food food) {
        log.log(Level.FINE, "Player use food and add health");
        health += food.getFoodValue();
    }
    /**
     * Takes damage from an enemy and attacks the enemy in return.
     *
     * @param damage The amount of damage to be taken.
     * @param enemy  The enemy that is attacking the player.
     */
    public void takeDamage(int damage, Enemy enemy) {
        log.log(Level.FINE, "Player take damage, less health");
        health = health - damage;
        attackEnemy(enemy);
    }
    /**
     * Attacks an enemy with a random damage value.
     *
     * @param enemy The enemy to be attacked.
     */
    public void attackEnemy(Enemy enemy) {
        r = new Random();
        log.log(Level.FINE, "Player attack enemy, use random for damage");
        enemy.takeDamage(r.nextInt(damage));
    }

    public List<Item> takeStartInventory() {
        ArrayList<Item> loadInv = new ArrayList<>();
        ObjectInputStream in;
        try (FileInputStream fileIn = new FileInputStream(FILE_NAME_OF_INVENTORY)) {
                in = new ObjectInputStream(fileIn);
                loadInv = (ArrayList<Item>) in.readObject();
                in.close();
                log.log(Level.FINE, "Take items from file");
        } catch (IOException | ClassNotFoundException e) {
            log.log(Level.WARNING, "Dont taken inventory from file");
        }
        return loadInv;
    }

    public void saveItemsInFile(List<Item> itemList) {
        ObjectOutputStream out;
        try {
            try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME_OF_INVENTORY)) {
                out = new ObjectOutputStream(fileOut);
                out.writeObject(itemList);
                out.close();
            }
            log.log(Level.FINE, "Items save in file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Finds a key item and adds it to the player's inventory.
     *
     * @param game The current game object.
     * @param key  The key item to be found.
     */
    public void findKey(Game game, Key key) {
        inventory.add(key);
        log.log(Level.FINER, "Add key in inventory and remove key from map");
        List<Item> itemList = game.getItems();
        itemList.remove(key);
        game.setItems(itemList);
    }
    /**
     * Finds a food item and adds it to the player's inventory.
     *
     * @param game The current game object.
     * @param food The food item to be found.
     */
    public void findFood(Game game, Food food) {
        inventory.add(food);
        eat(food);
        log.log(Level.FINER, "Eat food, add food in inventory and remove food from map");
        List<Item> itemList = game.getItems();
        itemList.remove(food);
        game.setItems(itemList);
    }
    /**
     * Finds a silver collar item and adds it to the player's inventory.
     *
     * @param game         The current game object.
     * @param silverCollar The silver collar item to be found.
     */
    public void findSilverCollar(Game game, SilverCollar silverCollar) {
        inventory.add(silverCollar);
        wear(silverCollar);
        log.log(Level.FINER, "Wear collar, add silver collar in inventory and remove from map");
        List<Item> itemList = game.getItems();
        itemList.remove(silverCollar);
        game.setItems(itemList);
    }
    /**
     * Finds a gold collar item and adds it to the player's inventory.
     *
     * @param game       The current game object.
     * @param goldCollar The gold collar item to be found.
     */
    public void findGoldCollar(Game game, GoldCollar goldCollar) {
        inventory.add(goldCollar);
        wear(goldCollar);
        log.log(Level.FINER, "Wear collar, add gold collar in inventory and remove from map");
        List<Item> itemList = game.getItems();
        itemList.remove(goldCollar);
        game.setItems(itemList);
    }

    /**
     * Interacts with a door.
     *
     * @param door The door to interact with.
     * @return {@code true} if the door is locked, {@code false} if it is unlocked.
     */
    public boolean interactWithDoor(Door door) {
        for (Item item : inventory) {
            if (item instanceof Key key) {
                door.unlock(key);
                if (!door.isLocked()) {
                    log.log(Level.FINER, "Door open");
                    return false;
                }
            }
        }
        if (door.isLocked()) {
            doorCollision = true;
            log.log(Level.FINER, "Door not open");
            return true;
        }
        return false;
    }

    /**
     * Interacts with an obstacle.
     *
     * @param obstacle The obstacle to interact with.
     */
    public void interactWithObstacles(Obstacle obstacle) {
        Rectangle intersection = obstacle.getBounds().intersection(newBounds);

        double width = intersection.getWidth();
        double height = intersection.getHeight();

        double dx = (newBounds.getCenterX() - obstacle.getBounds().getCenterX()) / width;
        double dy = (newBounds.getCenterY() - obstacle.getBounds().getCenterY()) / height;

        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx < 0) {
                rightCollision = true;
            } else {
                leftCollision = true;
            }
        } else {
            if (dy < 0) {
                downCollision = true;
            } else {
                upCollision = true;
            }
        }
    }
}