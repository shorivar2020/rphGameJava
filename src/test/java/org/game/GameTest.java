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
        Game game = new Game();
        game.start();

        assertEquals(100, game.getPlayer().getX());
        assertEquals(30, game.getPlayer().getY());

        game.getPlayer().moveRight(game, game.getObstacles(),game.getDoors(), game.getExit());
        game.getPlayer().moveDown(game, game.getObstacles(),game.getDoors(), game.getExit());

        assertEquals(100+5, game.getPlayer().getX());
        assertEquals(30+5, game.getPlayer().getY());
    }

    @Test
    public void testPlayerCollisionWithObstacle() {
        // Создайте экземпляр игры и игрового панели
        Game game = new Game();
        game.start();
        game.setPlayer(new Player(0, 0));
        List<Obstacle> obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(0, 0, true, false));
        game.setObstacles(obstacles);
        game.keyPressed(new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, VK_RIGHT, KeyEvent.CHAR_UNDEFINED));
        assertEquals(0, game.getPlayer().getX());
        assertEquals(0, game.getPlayer().getY());
    }
    @Test
    public void testEnemyKill() {
        // Создайте экземпляр игры и игрового панели
        Game game = new Game();
        game.start();
        game.setObstacles(new ArrayList<>());
        // Создайте экземпляр врага и добавьте его на игровую панель
        Rat rat = new Rat(0, 20);
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(rat);
        game.setEnemies(enemies);

        // Установите начальное значение жизней врага
        int initialEnemyHealth = rat.getHealth();

        // Вызовите метод атаки или действие, которое должно привести к убийству врага
        // Например, вызовите метод игрока, который наносит урон врагу
        game.getPlayer().attackEnemy(rat);

        // Проверьте, что жизни врага уменьшились на правильное значение после атаки
        assertTrue(initialEnemyHealth > rat.getHealth());
        // Проверьте, что враг был убит
        assertTrue(!rat.isAlive());
    }

    @Test
    public void testGameLose() {
        // Создайте экземпляр игры и игровой панели
        Game game = new Game();
        game.start();

        // Установите начальное значение состояния игры
        boolean initialGameEnded = game.isGameFinishLose();

        // Вызовите метод или выполните действие, которое должно привести к концу игры
        // Например, вызовите метод, который проверяет условия поражения игрока
        Dog dog = new Dog(0, 0);
        game.setPlayer(new Player(0, 0));
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(dog);
        game.setEnemies(enemies);
        game.getPlayer().takeDamage(game.getPlayer().getHealth()+1, dog);
        game.getPlayer().checkItemCollision(game, game.getItems(), game.getTrashCans(), game.getEnemies());
        // Проверьте, что состояние игры изменилось и игра закончилась
        assertFalse(initialGameEnded);
        assertTrue(game.isGameFinishLose());
    }

    @Test
    public void testGameWin(){
        Game game = new Game();
        game.start();
        boolean initialGameEnded = game.isGameFinishLose();
        game.setObstacles(new ArrayList<>());
        game.setPlayer(new Player(0, 0));
        game.setExit(new Exit(10, 0));
        for (int i = 0; i < 10; i+=5){
            game.getPlayer().moveDown(game, game.getObstacles(),game.getDoors(), game.getExit());
        }
        assertFalse(initialGameEnded);
        assertTrue(game.isGameFinishWin());
    }

    @Test
    public void testInteractWithItem(){
        Game game = new Game();
        game.start();
        game.setObstacles(new ArrayList<>());
        game.setPlayer(new Player(0,0));
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
        for (int i = 0; i < 70; i+=5){
            game.getPlayer().moveDown(game, game.getObstacles(),game.getDoors(), game.getExit());
        }

        assertFalse(d.isLocked());
    }
}
