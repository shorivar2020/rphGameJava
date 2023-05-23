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
    @Test
    public void testPlayerMovement() {
        Game game = new Game(false);
        game.start();

        assertEquals(100, game.getPlayer().getX());
        assertEquals(30, game.getPlayer().getY());

        game.getPlayer().moveRight(game, game.getObstacles(), game.getDoors(), game.getExit());
        game.getPlayer().moveDown(game, game.getObstacles(), game.getDoors(), game.getExit());

        assertEquals(100 + 5, game.getPlayer().getX());
        assertEquals(30 + 5, game.getPlayer().getY());
    }

    @Test
    public void testPlayerCollisionWithObstacle() {
        // Создайте экземпляр игры и игрового панели
        Game game = new Game(false);
        game.start();
        game.setPlayer(new Player(0, 0, false));
        List<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(0, 0, true, false));
        game.setObstacles(obstacles);
        game.keyPressed(new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, VK_RIGHT, KeyEvent.CHAR_UNDEFINED));
        assertEquals(0, game.getPlayer().getX());
        assertEquals(0, game.getPlayer().getY());
    }

    @Test
    public void testEnemyKill() {
        Game game = new Game(false);
        game.start();
        game.setObstacles(new ArrayList<>());
        // Create an instance of an enemy and add it to the game panel
        Rat rat = new Rat(0, 20);
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(rat);
        game.setEnemies(enemies);

        // Set the initial health value of the enemy
        int initialEnemyHealth = rat.getHealth();

        // Call the attack method or action that should result in the enemy's death
        // For example, invoke a player method that deals damage to the enemy
        game.getPlayer().attackEnemy(rat);

        // Assert that the enemy's health has decreased from its initial value
        assertTrue(initialEnemyHealth > rat.getHealth());

        // Assert that the enemy is no longer alive
        assertFalse(rat.isAlive());
    }

    @Test
    public void testGameLose() {
        // Create an instance of the game and game panel
        Game game = new Game(false);
        game.start();

        // Set the initial value of the game's state
        boolean initialGameEnded = game.isGameFinishLose();

        // Call a method or perform an action that should result in the game ending
        // For example, invoke a method that checks the player's defeat conditions
        Dog dog = new Dog(0, 0);
        game.setPlayer(new Player(0, 0, false));
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(dog);
        game.setEnemies(enemies);
        game.getPlayer().takeDamage(game.getPlayer().getHealth() + 1, dog);
        game.getPlayer().checkItemCollision(game, game.getItems(), game.getTrashCans(), game.getEnemies());

        // Check that the game's state has changed and the game has ended
        assertFalse(initialGameEnded);
        assertTrue(game.isGameFinishLose());
    }

    @Test
    public void testGameWin() {
        Game game = new Game(false);
        game.start();
        boolean initialGameEnded = game.isGameFinishLose();
        game.setObstacles(new ArrayList<>());
        game.setPlayer(new Player(0, 0, false));
        game.setExit(new Exit(10, 0));
        for (int i = 0; i < 10; i += 5) {
            game.getPlayer().moveDown(game, game.getObstacles(), game.getDoors(), game.getExit());
        }
        assertFalse(initialGameEnded);
        assertTrue(game.isGameFinishWin());
    }

    @Test
    public void testInteractWithItem() {
        Game game = new Game(false);
        game.start();
        game.setObstacles(new ArrayList<>());
        game.setPlayer(new Player(0, 0, false));
        int initialPlayerHealth = game.getPlayer().getHealth();
        int initialPlayerDamage = game.getPlayer().getDamage();
        List<Item> items = new ArrayList<>();
        Collar c = new SilverCollar(0, 0);
        items.add(c);
        Food f = new Food(0, 2);
        items.add(f);
        Key k = new Key(0, 40, 1);
        items.add(k);
        game.setItems(items);
        List<Door> doors = new ArrayList<>();
        Door d = new Door(0, 60, 1, false);
        doors.add(d);
        game.setDoors(doors);
        game.getPlayer().wear(c);
        assertEquals(game.getPlayer().getHealth() - initialPlayerHealth, c.getHealth());
        assertEquals(game.getPlayer().getDamage() - initialPlayerDamage, c.getDamage());
        game.getPlayer().setHealth(initialPlayerHealth);
        game.getPlayer().setDamage(initialPlayerDamage);
        game.getPlayer().eat(f);
        assertEquals(game.getPlayer().getHealth() - initialPlayerHealth, f.getFoodValue());
        game.getPlayer().setInventory(items);
        System.out.println(game.getPlayer().getBounds());
        for (int i = 0; i < 70; i += 5) {
            game.getPlayer().moveDown(game, game.getObstacles(), game.getDoors(), game.getExit());
        }

        assertFalse(d.isLocked());
    }
}
