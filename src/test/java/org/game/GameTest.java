package org.game;

import org.game.entity.Door;
import org.game.entity.Exit;
import org.game.entity.Obstacle;
import org.game.entity.enemy.Dog;
import org.game.entity.enemy.Enemy;
import org.game.entity.enemy.Rat;
import org.game.entity.item.Food;
import org.game.entity.item.Item;
import org.game.entity.item.Key;
import org.game.entity.item.collar.Collar;
import org.game.entity.item.collar.SilverCollar;
import org.junit.Test;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static java.awt.event.KeyEvent.VK_RIGHT;
import static org.junit.Assert.*;

public class GameTest {
    private static final int START_POSITION_X = 100;
    private static final int START_POSITION_Y = 30;
    private static final int ZERO_POSITION_X = 0;
    private static final int ZERO_POSITION_Y = 0;
    private static final int PLAYER_STEP = 5;

    @Test
    public void testPlayerMovement() {

        Game game = new Game(false);
        game.start();

        //Check player initial position
        assertEquals(START_POSITION_X, game.getPlayer().getX());
        assertEquals(START_POSITION_Y, game.getPlayer().getY());
        //Change movement
        game.getPlayer().moveRight(game, game.getObstacles(), game.getDoors(), game.getExit());
        game.getPlayer().moveDown(game, game.getObstacles(), game.getDoors(), game.getExit());
        //Player position after moving right and down
        assertEquals(START_POSITION_X + PLAYER_STEP, game.getPlayer().getX());
        assertEquals(START_POSITION_Y + PLAYER_STEP, game.getPlayer().getY());
    }

    @Test
    public void testPlayerCollisionWithObstacle() {
        Game game = new Game(false);
        game.start();
        //Add player on map
        game.setPlayer(new Player(ZERO_POSITION_X, ZERO_POSITION_Y, false));
        //Add obstacles on map
        List<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(ZERO_POSITION_X, ZERO_POSITION_Y, true, false));
        game.setObstacles(obstacles);
        //Change movement of player
        game.keyPressed(new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, VK_RIGHT, KeyEvent.CHAR_UNDEFINED));
        //Check player position
        assertEquals(0, game.getPlayer().getX());
        assertEquals(0, game.getPlayer().getY());
    }

    @Test
    public void testEnemyKill() {
        Game game = new Game(false);
        game.start();
        //Remove all obstacles
        game.setObstacles(new ArrayList<>());
        // Create enemy and add it on map
        Rat rat = new Rat(0, 20);
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(rat);
        game.setEnemies(enemies);
        // Set the initial health value of the enemy
        int initialEnemyHealth = rat.getHealth();
        // Call the attack method
        game.getPlayer().attackEnemy(rat);
        // Check that the enemy's health has decreased from its initial value
        assertTrue(initialEnemyHealth > rat.getHealth());
        // Assert that the enemy is died
        assertFalse(rat.isAlive());
    }

    @Test
    public void testGameLose() {
        Game game = new Game(false);
        game.start();

        // Set the initial value of the game's state
        boolean initialGameEnded = game.isGameFinishLose();
        //Create enemy and add it on map
        Dog dog = new Dog(ZERO_POSITION_X, ZERO_POSITION_Y);
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(dog);
        game.setEnemies(enemies);
        //Add player on map
        game.setPlayer(new Player(ZERO_POSITION_X, ZERO_POSITION_Y, false));
        //Take damage from enemy
        game.getPlayer().takeDamage(game.getPlayer().getHealth() + 1, dog);
        //Check collision with enemy
        game.getPlayer().checkItemCollision(game, game.getItems(), game.getTrashCans(), game.getEnemies());
        //Check that the game state has changed
        assertFalse(initialGameEnded);
        //Check that the game end
        assertTrue(game.isGameFinishLose());
    }

    @Test
    public void testGameWin() {
        Game game = new Game(false);
        game.start();

        // Set the initial value of the game's state
        boolean initialGameEnded = game.isGameFinishLose();
        //Remove all obstacles
        game.setObstacles(new ArrayList<>());
        //Add player on map
        game.setPlayer(new Player(ZERO_POSITION_X, ZERO_POSITION_Y, false));
        //Add exit on map
        game.setExit(new Exit(ZERO_POSITION_X + 2 * PLAYER_STEP, ZERO_POSITION_Y));
        //Change movement of the player
        for (int i = ZERO_POSITION_X; i < ZERO_POSITION_X + 2 * PLAYER_STEP; i += PLAYER_STEP) {
            game.getPlayer().moveDown(game, game.getObstacles(), game.getDoors(), game.getExit());
        }
        //Check that the game state has changed
        assertFalse(initialGameEnded);
        //Check that the game end
        assertTrue(game.isGameFinishWin());
    }

    @Test
    public void testInteractWithItem() {
        Game game = new Game(false);
        game.start();

        //Remove all obstacles
        game.setObstacles(new ArrayList<>());
        //Add player on map
        game.setPlayer(new Player(0, 0, false));
        //Set the initial value of the players health
        int initialPlayerHealth = game.getPlayer().getHealth();
        //Set the initial value of the players damage
        int initialPlayerDamage = game.getPlayer().getDamage();
        //Add items on map
        List<Item> items = new ArrayList<>();
        Collar c = new SilverCollar(ZERO_POSITION_X, ZERO_POSITION_Y);
        items.add(c);
        Food f = new Food(ZERO_POSITION_X, ZERO_POSITION_Y);
        items.add(f);
        Key k = new Key(ZERO_POSITION_X, 8 * PLAYER_STEP, 1);
        items.add(k);
        game.setItems(items);
        List<Door> doors = new ArrayList<>();
        Door d = new Door(ZERO_POSITION_X, 12 * PLAYER_STEP, 1, false);
        doors.add(d);
        game.setDoors(doors);

        //Interact with collar
        game.getPlayer().wear(c);
        //Check that the players health has increased from its initial value
        assertEquals(game.getPlayer().getHealth() - initialPlayerHealth, c.getHealth());
        //Check that the players damage has increased from its initial value
        assertEquals(game.getPlayer().getDamage() - initialPlayerDamage, c.getDamage());
        //Return to initial value
        game.getPlayer().setHealth(initialPlayerHealth);
        game.getPlayer().setDamage(initialPlayerDamage);

        //Interact with food
        game.getPlayer().eat(f);
        //Check that the players health has increased from its initial value
        assertEquals(game.getPlayer().getHealth() - initialPlayerHealth, f.getFoodValue());
        game.getPlayer().setInventory(items);

        //Check interact with key and door
        //Change player movement, pick up and go throw the door
        for (int i = ZERO_POSITION_X; i < 14 * PLAYER_STEP; i += PLAYER_STEP) {
            game.getPlayer().moveDown(game, game.getObstacles(), game.getDoors(), game.getExit());
        }
        //Check if door close
        assertFalse(d.isLocked());
    }
}
