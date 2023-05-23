/**
 * This is the player class of the game.
 *
 * @version 1.0
 * @see Player
 * @since 1.0
 */
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

import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;


/**
 * The player class is controlled through the keyboards by the user.
 * Moves around the map. Interacts with objects.
 */
@Log
@Getter
@Setter
public class Player implements Serializable {
    /**
     * The file name of the inventory file.
     */
    private static final String FILE_NAME_OF_INVENTORY = "items.txt";

    /**
     * The file name of the player's image facing right in the first frame.
     */
    private static final String FILE_NAME_RIGHT_1 = "/cat1.png";

    /**
     * The file name of the player's image facing right in the second frame.
     */
    private static final String FILE_NAME_RIGHT_2 = "/cat2.png";

    /**
     * The file name of the player's image left in the first frame.
     */
    private static final String FILE_NAME_LEFT_1 = "/cat1l.png";

    /**
     * The file name of the player's image left in the second frame.
     */
    private static final String FILE_NAME_LEFT_2 = "/cat2l.png";

    /**
     * The file name of the player's image up.
     */
    private static final String FILE_NAME_UP = "/cat_a1.png";

    /**
     * The file name of the player's image down.
     */
    private static final String FILE_NAME_DOWN = "/cat_b.png";

    /**
     * The starting health value for a player.
     */
    private static final int START_HEALTH = 9;

    /**
     * The starting damage value for a player.
     */
    private static final int START_DAMAGE = 2;

    /**
     * The size constant for a player.
     */
    public static final int SIZE = 50;

    /**
     * The speed constant for a player.
     */
    public static final int SPEED = 5;

    /**
     * The x-coordinate of the player.
     */
    private int x;

    /**
     * The y-coordinate of the player.
     */
    private int y;

    /**
     * The health value of the player.
     */
    private int health;

    /**
     * The damage value of the player.
     */
    private int damage;

    /**
     * The inventory list of items owned by player.
     */
    private List<Item> inventory;

    /**
     * A flag indicating if there is a collision
     * on the left side of the player.
     */
    private boolean leftCollision;

    /**
     * A flag indicating if there is a collision
     * on the right side of player.
     */
    private boolean rightCollision;

    /**
     * A flag indicating if there is a collision
     * on the up of the player.
     */
    private boolean upCollision;

    /**
     * A flag indicating if there is a collision
     * on the down of the player.
     */
    private boolean downCollision;

    /**
     * A flag indicating if there is a collision with a door.
     */
    private boolean doorCollision;

    /**
     * The count of movements made by the player.
     */
    private int moveCount;

    /**
     * The bounding rectangle of the game character.
     */
    private Rectangle newBounds;

    /**
     * The image icon associated with the game character.
     */
    private ImageIcon image;

    /**
     * A boolean flag indicating logging information about game.
     */
    private boolean enableLogging;

    /**
     * Constructs a Player with the initial position.
     *
     * @param xLocal The x-coordinate of the player's position
     * @param yLocal The y-coordinate of the player's position
     * @param eLog   Flag indicate logging of game
     */
    public Player(final int xLocal, final int yLocal, final boolean eLog) {
        setEnableLogging(eLog);
        this.x = xLocal;
        this.y = yLocal;
        health = START_HEALTH;
        damage = START_DAMAGE;
        inventory = takeStartInventory();
        setImage(this);
    }

    /**
     * Checks if the player collides with obstacles, doors, or the exit
     * and performs the corresponding actions.
     *
     * @param game            The current game object
     * @param obstacles       The list of obstacles in the game
     * @param doors           The list of doors in the game
     * @param exit            The exit in the game
     * @param playerNewBounds The new bounds of player in the game
     * @return True if the player can move to the new position, false otherwise
     */
    private boolean checkObstacle(final Game game,
                                  final List<Obstacle> obstacles,
                                  final List<Door> doors,
                                  final Exit exit,
                                  final Rectangle playerNewBounds) {
        leftCollision = false;
        rightCollision = false;
        upCollision = false;
        downCollision = false;
        doorCollision = false;
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getBounds().intersects(playerNewBounds)) {
                if (enableLogging) {
                    log.log(Level.FINE, "Collision with obstacle");
                }
                interactWithObstacles(obstacle);
                return false;
            }
        }
        for (Door door : doors) {
            if (door.getBounds().intersects(playerNewBounds)) {
                if (enableLogging) {
                    log.log(Level.FINE, "Collision with door");
                }
                return !interactWithDoor(door);
            }
        }
        if (exit.getBounds().intersects(playerNewBounds)) {
            if (enableLogging) {
                log.log(Level.FINE, "Player find exit");
            }
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
     * @param game      The current game object
     * @param items     The list of items in the game
     * @param trashCans The list of trash cans in the game
     * @param enemies   The list of enemies in the game
     */
    public void checkItemCollision(final Game game,
                                   final List<Item> items,
                                   final List<TrashCan> trashCans,
                                   final List<Enemy> enemies) {
        newBounds = new Rectangle(x - SPEED, y, SIZE, SIZE);
        for (Item item : items) {
            if (item instanceof Key key
                    && (key.getBounds().intersects(newBounds))) {
                findKey(game, key);
                return;
            }
            if (item instanceof Food food
                    && (food.getBounds().intersects(newBounds))) {
                findFood(game, food);
                return;
            }
            if (item instanceof SilverCollar silverCollar
                    && ((silverCollar).getBounds().intersects(newBounds))) {
                findCollar(game, silverCollar);
                return;
            }
            if (item instanceof GoldCollar goldCollar
                    && ((goldCollar).getBounds().intersects(newBounds))) {
                findCollar(game, goldCollar);
                return;
            }
        }
        checkTrashCanCollision(trashCans);
        checkEnemyCollision(game, enemies);
    }

    /**
     * Checks for collision between the player and trashcans.
     *
     * @param trashCans The list of trashcans in the game
     */
    public void checkTrashCanCollision(final List<TrashCan> trashCans) {
        for (TrashCan trashCan : trashCans) {
            if (trashCan.getBounds().intersects(newBounds)) {
                if (enableLogging) {
                    log.log(Level.FINE, "Player find in trashcan");
                }
                findInTrashcan(trashCan.getContent());
                if (enableLogging) {
                    log.log(Level.FINE, "Clear trashcan");
                }
                trashCan.setContent(new ArrayList<>());
            }
        }
    }

    /**
     * Interact with trashcan and find item in it.
     *
     * @param content List of item in trashcan
     */
    public void findInTrashcan(final List<Item> content) {
        for (Item item : content) {
            if (enableLogging) {
                log.log(Level.FINE, "Add new item in inventory");
            }
            inventory.add(item);
            if (item instanceof Collar collar) {
                wear(collar);
            }
            if (item instanceof Food food) {
                eat(food);
            }
        }
    }

    /**
     * Checks if the player collides with an enemy.
     *
     * @param game    The current game object.
     * @param enemies The list of enemies in the game.
     */
    public void checkEnemyCollision(final Game game,
                                    final List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy.getBounds().intersects(newBounds)) {
                if (enableLogging) {
                    log.log(Level.FINE, "Player interact with enemy");
                }
                enemy.attack(this);
                postBattleReview(game, enemy);
                return;
            }
        }
    }

    /**
     * After interaction with enemy check health of player and enemy.
     *
     * @param game  The current game object.
     * @param enemy The enemy we interact with
     */
    public void postBattleReview(final Game game, final Enemy enemy) {
        if (!enemy.isAlive()) {
            if (enableLogging) {
                log.log(Level.FINE, "Remove enemy from map");
            }
            List<Enemy> enemyList = game.getEnemies();
            enemyList.remove(enemy);
            game.setEnemies(enemyList);
        }
        if (health < 1) {
            if (enableLogging) {
                log.log(Level.FINE, "Player die, game was finished");
            }
            saveItemsInFile(inventory);
            game.loseGame();
        }
    }

    /**
     * Moves the player to the left
     * and checks for collisions with obstacles, doors, and the exit.
     *
     * @param game      The current game object.
     * @param obstacles The list of obstacles in the game.
     * @param doors     The list of doors in the game.
     * @param exit      The exit object in the game.
     */
    public void moveLeft(final Game game, final List<Obstacle> obstacles,
                         final List<Door> doors, final Exit exit) {
        setImageL(this);
        if ((checkObstacle(game, obstacles, doors, exit,
                new Rectangle(x - SPEED, y, SIZE, SIZE)))
                || (!leftCollision && !doorCollision)) {
            x -= SPEED;
        }
    }

    /**
     * Moves the player to the right
     * and checks for collisions with obstacles, doors, and the exit.
     *
     * @param game      The current game object.
     * @param obstacles The list of obstacles in the game.
     * @param doors     The list of doors in the game.
     * @param exit      The exit object in the game.
     */
    public void moveRight(final Game game,
                          final List<Obstacle> obstacles,
                          final List<Door> doors,
                          final Exit exit) {
        setImage(this);
        if ((checkObstacle(game, obstacles, doors, exit,
                new Rectangle(x - SPEED, y, SIZE, SIZE)))
                || (!rightCollision)) {
            x += SPEED;
        }
    }

    /**
     * Moves the player up
     * and checks for collisions with obstacles, doors, and the exit.
     *
     * @param game      The current game object.
     * @param obstacles The list of obstacles in the game.
     * @param doors     The list of doors in the game.
     * @param exit      The exit object in the game.
     */
    public void moveUp(final Game game,
                       final List<Obstacle> obstacles,
                       final List<Door> doors,
                       final Exit exit) {
        setImageB(this);
        if ((checkObstacle(game, obstacles, doors, exit,
                new Rectangle(x - SPEED, y, SIZE, SIZE)))
                || (!upCollision && !doorCollision)) {
            y -= SPEED;
        }
    }

    /**
     * Moves the player down
     * and checks for collisions with obstacles, doors, and the exit.
     *
     * @param game      The current game object.
     * @param obstacles The list of obstacles in the game.
     * @param doors     The list of doors in the game.
     * @param exit      The exit object in the game.
     */
    public void moveDown(final Game game,
                         final List<Obstacle> obstacles,
                         final List<Door> doors,
                         final Exit exit) {
        setImageA(this);
        if ((checkObstacle(game, obstacles, doors, exit,
                new Rectangle(x - SPEED, y, SIZE, SIZE))) || (!downCollision)) {
            y += SPEED;
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
    public void setImage(final Player e) {
        URL img;
        if (moveCount % 2 == 0) {
            img = Objects.
                    requireNonNull(getClass().getResource(FILE_NAME_RIGHT_1));
        } else {
            img = Objects.
                    requireNonNull(getClass().getResource(FILE_NAME_RIGHT_2));
        }
        e.image = new ImageIcon(img);
        if (enableLogging) {
            log.log(Level.FINER, "Set new image of player right");
        }
        moveCount++;
    }

    /**
     * Sets the image of the player when facing up.
     *
     * @param e The player object.
     */
    public void setImageA(final Player e) {
        URL img = Objects.
                requireNonNull(getClass().getResource(FILE_NAME_UP));
        e.image = new ImageIcon(img);
        if (enableLogging) {
            log.log(Level.FINER, "Set image of player up");
        }
    }

    /**
     * Sets the image of the player when facing down.
     *
     * @param e The player object.
     */
    public void setImageB(final Player e) {
        URL img = Objects.
                requireNonNull(getClass().getResource(FILE_NAME_DOWN));
        e.image = new ImageIcon(img);
        if (enableLogging) {
            log.log(Level.FINER, "Set image of player down");
        }
    }

    /**
     * Sets the image of the player when facing left.
     *
     * @param e The player object.
     */
    public void setImageL(final Player e) {
        URL img;
        if (moveCount % 2 != 0) {
            img = Objects.
                    requireNonNull(getClass().getResource(FILE_NAME_LEFT_1));
        } else {
            img = Objects.
                    requireNonNull(getClass().getResource(FILE_NAME_LEFT_2));
        }
        e.image = new ImageIcon(img);
        if (enableLogging) {
            log.log(Level.FINER, "Set image of player left");
        }
        moveCount++;
    }

    /**
     * Draws the player on the screen.
     *
     * @param g2d The graphics context.
     */
    public void draw(final Graphics2D g2d) {
        g2d.drawImage(image.getImage(), x, y, null);
    }

    /**
     * Wears the given collar,
     * adding its health and damage values to the player.
     *
     * @param collar The collar item to be worn
     */
    void wear(final Collar collar) {
        if (enableLogging) {
            log.log(Level.FINE, "Player use collar and add health, damage");
        }
        health += collar.getHealth();
        damage += collar.getDamage();
    }

    /**
     * Eats the given food item, increasing the player's health.
     *
     * @param food The food item to be eaten
     */
    void eat(final Food food) {
        if (enableLogging) {
            log.log(Level.FINE, "Player use food and add health");
        }
        health += food.getFoodValue();
    }

    /**
     * Takes damage from an enemy and attacks the enemy in return.
     *
     * @param enemyDamage The amount of damage to be taken
     * @param enemy       The enemy that is attacking the player
     */
    public void takeDamage(final int enemyDamage, final Enemy enemy) {
        if (enableLogging) {
            log.log(Level.FINE, "Player take damage, less health");
        }
        health = health - enemyDamage;
        attackEnemy(enemy);
    }

    /**
     * Attacks an enemy with a damage value.
     *
     * @param enemy The enemy to be attacked
     */
    public void attackEnemy(final Enemy enemy) {
        if (enableLogging) {
            log.log(Level.FINE, "Player attack enemy");
        }
        enemy.takeDamage(damage);
    }

    /**
     * Takes damage items from file where they were added in last game (lose).
     *
     * @return List<Item> items from file
     */
    public List<Item> takeStartInventory() {
        ArrayList<Item> loadInv = new ArrayList<>();
        ObjectInputStream in;
        try (FileInputStream fileIn =
                     new FileInputStream(FILE_NAME_OF_INVENTORY)) {
            in = new ObjectInputStream(fileIn);
            loadInv = (ArrayList<Item>) in.readObject();
            in.close();
            if (enableLogging) {
                log.log(Level.FINE, "Take items from file");
            }
        } catch (IOException | ClassNotFoundException e) {
            if (enableLogging) {
                log.log(Level.WARNING, "Dont taken inventory from file");
            }
        }
        return loadInv;
    }

    /**
     * Take all items from inventory and save it in file.
     *
     * @param itemList The current list of items
     */
    public void saveItemsInFile(final List<Item> itemList) {
        ObjectOutputStream out;
        try {
            try (FileOutputStream fileOut =
                         new FileOutputStream(FILE_NAME_OF_INVENTORY)) {
                out = new ObjectOutputStream(fileOut);
                out.writeObject(itemList);
                out.close();
            }
            if (enableLogging) {
                log.log(Level.FINE, "Items save in file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds a key item and adds it to the player's inventory.
     *
     * @param game The current game object
     * @param key  The key item to be found
     */
    public void findKey(final Game game, final Key key) {
        inventory.add(key);
        if (enableLogging) {
            log.log(Level.FINER, "Add key in inventory");
        }
        List<Item> itemList = game.getItems();
        itemList.remove(key);
        game.setItems(itemList);
    }

    /**
     * Finds a food item and adds it to the player's inventory.
     *
     * @param game The current game object
     * @param food The food item to be found
     */
    public void findFood(final Game game, final Food food) {
        inventory.add(food);
        eat(food);
        if (enableLogging) {
            log.log(Level.FINER, "Eat food and remove food from map");
        }
        List<Item> itemList = game.getItems();
        itemList.remove(food);
        game.setItems(itemList);
    }

    /**
     * Finds a collar item and adds it to the player's inventory.
     *
     * @param game   The current game object
     * @param collar The collar item to be found
     */
    public void findCollar(final Game game,
                           final Collar collar) {
        inventory.add(collar);
        wear(collar);
        if (enableLogging) {
            log.log(Level.FINER, "Wear collar and remove from map");
        }
        List<Item> itemList = game.getItems();
        itemList.remove(collar);
        game.setItems(itemList);
    }


    /**
     * Interacts with a door.
     *
     * @param door The door to interact with
     * @return {@code true} if the door is locked,
     * {@code false} if it is unlocked
     */
    public boolean interactWithDoor(final Door door) {
        for (Item item : inventory) {
            if (item instanceof Key key) {
                door.unlock(key);
                if (!door.isLocked()) {
                    if (enableLogging) {
                        log.log(Level.FINER, "Door open");
                    }
                    return false;
                }
            }
        }
        if (door.isLocked()) {
            doorCollision = true;
            if (enableLogging) {
                log.log(Level.FINER, "Door not open");
            }
            return true;
        }
        return false;
    }

    /**
     * Interacts with an obstacle.
     *
     * @param obstacle The obstacle to interact with.
     */
    public void interactWithObstacles(final Obstacle obstacle) {
        Rectangle intersection = obstacle.getBounds().intersection(newBounds);
        //Finding player boundaries
        double width = intersection.getWidth();
        double height = intersection.getHeight();
        //Finding obstacle center
        double obstacleX = obstacle.getBounds().getCenterX();
        double obstacleY = obstacle.getBounds().getCenterY();
        //Find intersect
        double diffX = newBounds.getCenterX() - obstacleX;
        double diffY = newBounds.getCenterY() - obstacleY;
        double countedX = (diffX) / width;
        double countedY = (diffY) / height;
        //Find where intersect
        if (Math.abs(countedX) > Math.abs(countedY)) {
            if (countedX < 0) {
                rightCollision = true;
            } else {
                leftCollision = true;
            }
        } else {
            if (countedY < 0) {
                downCollision = true;
            } else {
                upCollision = true;
            }
        }
    }
}
