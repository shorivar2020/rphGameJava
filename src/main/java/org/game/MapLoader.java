/**
 * This is the loader map class of the game
 *
 * @version 1.0
 * @see Game
 * @since 1.0
 */
package org.game;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.game.entity.TrashCan;
import org.game.entity.Obstacle;
import org.game.entity.Background;
import org.game.entity.Door;
import org.game.entity.Exit;
import org.game.entity.enemy.Dog;
import org.game.entity.enemy.Enemy;
import org.game.entity.enemy.Rat;
import org.game.entity.item.Food;
import org.game.entity.item.Item;
import org.game.entity.item.Key;
import org.game.entity.item.collar.GoldCollar;
import org.game.entity.item.collar.SilverCollar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;


/**
 * The class loads and parses the map and its objects from a file.
 */
@Log
@Getter
@Setter
public class MapLoader {
    /**
     * The file name of the map file.
     */
    private static final String FILE_MAP_NAME = "src/main/resources/map.txt";

    /**
     * The height of the map.
     */
    private static final int MAP_HEIGHT = 20;

    /**
     * The width of the map.
     */
    private static final int MAP_WIDTH = 40;

    /**
     * The size of a block on the map.
     */
    private static final int BLOCK_SIZE = 30;

    /**
     * The starting point for blocks on the map.
     */
    private static final int BLOCK_START = 0;

    /**
     * The list of background objects on the map.
     */
    private List<Background> backgrounds = new ArrayList<>();

    /**
     * The list of obstacle objects on the map.
     */
    private List<Obstacle> obstacles = new ArrayList<>();

    /**
     * The list of door objects on the map.
     */
    private List<Door> doors = new ArrayList<>();

    /**
     * The list of trashcan objects on the map.
     */
    private List<TrashCan> trashCans = new ArrayList<>();

    /**
     * The list of item objects on the map.
     */
    private List<Item> items = new ArrayList<>();

    /**
     * The exit object on the map.
     */
    private Exit exit;

    /**
     * The list of enemy objects on the map.
     */
    private List<Enemy> enemies = new ArrayList<>();

    /**
     * The x-coordinate for block layout.
     */
    private int xLocal = 0;

    /**
     * The y-coordinate for block layout.
     */
    private int yLocal = 0;

    /**
     * The count of door objects on the map.
     */
    private int doorCount = 0;

    /**
     * The count of trash can objects on the map.
     */
    private int trashCount = 0;

    /**
     * The background object for items.
     */
    private Background b;

    /**
     * Loads the map from a file.
     *
     * @param filename The filename of the map file
     * @param eLog     Flag indicate logging of game
     * @return The 2D char array representation of the map
     * @throws FileNotFoundException if the map file is not found
     */
    public static char[][] loadMap(final String filename, final boolean eLog)
            throws IOException {
        char[][] map = new char[MAP_WIDTH][MAP_HEIGHT];
        File file = new File(filename);
        Scanner scanner = new Scanner(file, StandardCharsets.UTF_8);
        int row = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int col = 0; col < line.length(); col++) {
                char c = line.charAt(col);
                switch (c) {
                    case '0' -> map[col][row] = 0;
                    case '1' -> map[col][row] = 1; // obstacles
                    case '2' -> map[col][row] = 2; // trashcan
                    case '3' -> map[col][row] = 3; // dog
                    case '4' -> map[col][row] = 4; // rat
                    case '5' -> map[col][row] = 5; // exit
                    case '6' -> map[col][row] = 6; // horizontal door
                    case '7' -> map[col][row] = 7; // vertical door
                    case '8' -> map[col][row] = 8; // silver collar
                    case '9' -> map[col][row] = 9; // gold collar
                    case '+' -> map[col][row] = 10; // food
                    case 'g' -> map[col][row] = 'g'; // grass
                    case 'f' -> map[col][row] = 'f'; // floor
                    case 'w' -> map[col][row] = 'w'; //water
                    default -> {
                        if (eLog) {
                            log.log(Level.WARNING, "Undefined part of map");
                        }
                    }
                }
            }
            row++;
        }
        scanner.close();
        return map;
    }

    /**
     * Loads the map and its objects from the map file.
     *
     * @param enableLogging Flag indicate logging of game
     */
    public void getMapFromFile(final boolean enableLogging) {
        char[][] map = new char[MAP_WIDTH][MAP_HEIGHT];
        try {
            map = MapLoader.loadMap(FILE_MAP_NAME, enableLogging);
            if (enableLogging) {
                log.log(Level.FINE, "Map file was loaded successfully");
            }
        } catch (IOException e) {
            if (enableLogging) {
                log.log(Level.SEVERE, "Map file not found", e);
            }
        }
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                mapParser(map[j][i], enableLogging);
                xLocal += BLOCK_SIZE;
            }
            xLocal = BLOCK_START;
            yLocal += BLOCK_SIZE;
        }
        if (enableLogging) {
            log.log(Level.FINE, "Map was parsed successfully");
        }
        keyDistributionAlgorithm(enableLogging);
    }

    /**
     * Parses the character from the map and creates the corresponding objects.
     *
     * @param parserChar    The character to be parsed
     * @param enableLogging Flag indicate logging of game
     */
    public void mapParser(final char parserChar, boolean enableLogging) {
        switch (parserChar) {
            case 0 -> {
                b = new Background(xLocal, yLocal);
                b.setImage(false, true, false, false, false);
                backgrounds.add(b);
            }
            case 'g' -> {
                b = new Background(xLocal, yLocal);
                b.setImage(true, false, false, false, false);
                backgrounds.add(b);
            }
            case 'f' -> {
                b = new Background(xLocal, yLocal);
                b.setImage(false, false, true, false, false);
                backgrounds.add(b);
            }
            case 1 -> obstacles.add(new Obstacle(xLocal, yLocal, true, false));
            case 'w' -> obstacles.add(new Obstacle(xLocal, yLocal, false, true));
            case 2 -> {
                List<Item> c = new ArrayList<>();
                b = new Background(xLocal, yLocal);
                b.setImage(true, false, false, false, false);
                backgrounds.add(b);
                trashCans.add(new TrashCan(xLocal, yLocal, c));
                trashCount++;
            }
            case 3 -> {
                b = new Background(xLocal, yLocal);
                b.setImage(false, true, false, false, false);
                backgrounds.add(b);
                enemies.add(new Dog(xLocal, yLocal));
            }
            case 4 -> {
                b = new Background(xLocal, yLocal);
                b.setImage(false, true, false, false, false);
                backgrounds.add(b);
                enemies.add(new Rat(xLocal, yLocal));
            }
            case 5 -> {
                b = new Background(xLocal, yLocal);
                b.setImage(false, false, true, false, false);
                backgrounds.add(b);
                exit = new Exit(xLocal, yLocal);
            }
            case 6 -> {
                b = new Background(xLocal, yLocal);
                b.setImage(false, true, false, false, false);
                backgrounds.add(b);
                doors.add(new Door(xLocal, yLocal, 0, false));
                doorCount++;
            }
            case 7 -> {
                b = new Background(xLocal, yLocal);
                b.setImage(false, true, false, false, false);
                backgrounds.add(b);
                doors.add(new Door(xLocal, yLocal, 0, true));
                doorCount++;
            }
            case 8 -> {
                addCushionBackground();
                items.add(new SilverCollar(xLocal, yLocal));
            }
            case 9 -> {
                addCushionBackground();
                items.add(new GoldCollar(xLocal, yLocal));
            }
            case 10 -> {
                b = new Background(xLocal, yLocal);
                b.setImage(false, false, false, true, false);
                backgrounds.add(b);
                items.add(new Food(xLocal, yLocal));
            }
            default -> {
                if (enableLogging) {
                    log.log(Level.WARNING, "Undefined part of the map");
                }
            }
        }
    }

    /**
     * Adds a background for cushion to the list of backgrounds.
     */
    public void addCushionBackground() {
        b = new Background(xLocal, yLocal);
        b.setImage(false, false, true, false, false);
        backgrounds.add(b);
        b = new Background(xLocal, yLocal);
        b.setImage(false, false, false, false, true);
        backgrounds.add(b);
    }

    /**
     * Distributes keys of doors to trashcans
     * based on a key distribution algorithm.
     *
     * @param enableLogging Flag indicate logging of game
     */
    private void keyDistributionAlgorithm(final boolean enableLogging) {
        if (doorCount <= trashCount) {
            for (int i = 0; i < doorCount; i++) {
                doors.get(i).setDoorNumber(i);
                List<Item> itemList = trashCans.get(i).getContent();
                Key key = new Key(doors.get(i).getX(), doors.get(i).getY(), i);
                itemList.add(key);
                trashCans.get(i).setContent(itemList);
            }
            if (enableLogging) {
                log.log(Level.FINE, "Keys were distributed successfully");
            }
        } else {
            if (enableLogging) {
                log.log(Level.WARNING, "Too few trashcans for distribution");
            }
        }
    }
}
