package org.game;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.game.entity.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

@Log
@Getter
@Setter
public class MapLoader {

    private List<Background> backgrounds;
    private List<Obstacle> obstacles;
    private List<Door> doors;
    private List<TrashCan> trashCans;
    private List<Item> items;
    private Exit exit;
    private List<Enemy> enemies;

    public static char[][] loadMap(String filename) throws FileNotFoundException {
        char[][] map = new char[40][20]; // создаем карту размером 40 на 20 квадратов
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        int row = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int col = 0; col < line.length(); col++) {
                char c = line.charAt(col);
                switch (c){
                    case '0' -> map[col][row] = 0;
                    case '1' -> map[col][row] = 1;//obstacles
                    case '2' -> map[col][row] = 2;//trashcan
                    case '3' -> map[col][row] = 3;//dog
                    case '4' -> map[col][row] = 4;//rat
                    case '5' -> map[col][row] = 5;//exit
                    case '6' -> map[col][row] = 6;//horizontal door
                    case '7' -> map[col][row] = 7;//vertical door
                    case '8' -> map[col][row] = 8;//silver collar
                    case '9' -> map[col][row] = 9;//gold collar
                    case '+' -> map[col][row] = 10;//food
                    case 'g' -> map[col][row] = 'g';//grass
                    case 'f' -> map[col][row] = 'f';//floor
                    case 'w' -> map[col][row] = 'w';
                    default -> log.log(Level.WARNING, "Undefined part of map");
                }
            }
            row++;
        }
        scanner.close();
        return map;
    }

    public void mapParser(){
        backgrounds = new ArrayList<>();
        obstacles = new ArrayList<>();
        doors = new ArrayList<>();
        trashCans = new ArrayList<>();
        items = new ArrayList<>();
        enemies = new ArrayList<>();
        char[][] map = new char[40][20];
        try {
            map = MapLoader.loadMap("src/main/resources/map.txt");
        } catch (FileNotFoundException e) {
            log.log(Level.WARNING, "File not find" + e.getMessage());
        }
        int xLocal = 0;
        int yLocal = 0;
        int doorCount = 0;
        int trashCount = 0;
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 40; j++){
                switch (map[j][i]) {
                    case 0 -> backgrounds.add(new Background(xLocal, yLocal, 30, 30, false, true, false, false, false));
                    case 'g' ->
                            backgrounds.add(new Background(xLocal, yLocal, 30, 30, true, false, false, false, false));
                    case 'f' ->
                            backgrounds.add(new Background(xLocal, yLocal, 30, 30, false, false, true, false, false));
                    case 1 -> obstacles.add(new Obstacle(xLocal, yLocal, 30, 30, true, false));
                    case 'w' -> obstacles.add(new Obstacle(xLocal, yLocal, 30, 30, false, true));
                    case 2 -> {
                        List<Item> c = new ArrayList<>();
                        backgrounds.add(new Background(xLocal, yLocal, 30, 30, true, false, false, false, false));
                        trashCans.add(new TrashCan(xLocal, yLocal, c));
                        trashCount++;
                    }
                    case 3 -> {
                        backgrounds.add(new Background(xLocal, yLocal, 30, 30, false, true, false, false, false));
                        enemies.add(new Dog(xLocal, yLocal));
                    }
                    case 4 -> {
                        backgrounds.add(new Background(xLocal, yLocal, 30, 30, false, true, false, false, false));
                        enemies.add(new Rat(xLocal, yLocal));
                    }
                    case 5 -> {
                        backgrounds.add(new Background(xLocal, yLocal, 30, 30, false, false, true, false, false));
                        exit = new Exit(xLocal, yLocal);
                    }
                    case 6 -> {
                        backgrounds.add(new Background(xLocal, yLocal, 30, 30, false, true, false, false, false));
                        doors.add(new Door(xLocal, yLocal, 0, false));
                        doorCount++;
                    }
                    case 7 -> {
                        backgrounds.add(new Background(xLocal, yLocal, 30, 30, false, true, false, false, false));
                        doors.add(new Door(xLocal, yLocal, 0, true));
                        doorCount++;
                    }
                    case 8 -> {
                        backgrounds.add(new Background(xLocal, yLocal, 30, 30, false, false, true, false, false));
                        backgrounds.add(new Background(xLocal, yLocal, 30, 30, false, false, false, false, true));
                        items.add(new SilverCollar(xLocal, yLocal));
                    }
                    case 9 -> {
                        backgrounds.add(new Background(xLocal, yLocal, 30, 30, false, false, true, false, false));
                        backgrounds.add(new Background(xLocal, yLocal, 30, 30, false, false, false, false, true));
                        items.add(new GoldCollar(xLocal, yLocal));
                    }
                    case 10 -> {
                        backgrounds.add(new Background(xLocal, yLocal, 30, 30, false, false, false, true, false));
                        items.add(new Food(xLocal, yLocal));
                    }
                    default -> log.log(Level.INFO, "Undefined part of map");
                }
                xLocal += 30;
            }
            xLocal = 0;
            yLocal += 30;
        }
        keyDistributionAlgorithm(doorCount, trashCount);
    }
    private void keyDistributionAlgorithm(int doorCount, int trashCount){
        if(doorCount <= trashCount){
            for(int i = 0; i < doorCount; i++){
                doors.get(i).setDoorNumber(i);
                trashCans.get(i).content.add(new Key(doors.get(i).x, doors.get(i).y, i));
            }
        }else{
            log.log(Level.WARNING, "TOO LITTLE TRASHCANS");
        }
    }

}
