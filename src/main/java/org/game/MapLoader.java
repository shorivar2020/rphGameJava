package org.game;

import org.game.Entity.*;
import org.game.Entity.enemy.Dog;
import org.game.Entity.enemy.Enemy;
import org.game.Entity.enemy.Rat;
import org.game.Entity.item.Food;
import org.game.Entity.item.Item;
import org.game.Entity.item.Key;
import org.game.Entity.item.collar.GoldCollar;
import org.game.Entity.item.collar.SilverCollar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapLoader {

    public List<Background> backgrounds;
    public List<Obstacle> obstacles;
    public List<Door> doors;
    public List<TrashCan> trashCans;
    public List<Item> items;
    public Exit exit;
    public List<Enemy> enemies;

    public MapLoader(){

    }

    public static char[][] loadMap(String filename) throws FileNotFoundException {
        char[][] map = new char[40][20]; // создаем карту размером 40 на 20 квадратов
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        int row = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
//            System.out.println(line.length());
            for (int col = 0; col < line.length(); col++) {
                char c = line.charAt(col);
                if (c == '0') {
                    map[col][row] = 0;
                } else if (c == '1') { //obstacles
                    map[col][row] = 1;
                } else if (c == '2') { //trashcan
                    map[col][row] = 2;
                }else if (c == '3') { //dog
                    map[col][row] = 3;
                }else if (c == '4') { //rat
                    map[col][row] = 4;
                }else if (c == '5') { //exit
                    map[col][row] = 5;
                }else if (c == '6') { //horizontal door
                    map[col][row] = 6;
                }else if (c == '7') { //vertical door
                    map[col][row] = 7;
                }else if (c == '8') { //silver collar
                    map[col][row] = 8;
                }else if (c == '9') { //gold collar
                    map[col][row] = 9;
                }else if (c == '+') { //food
                    map[col][row] = 10;
                }else if (c == 'g') { //grass
                    map[col][row] = 'g';
                }else if (c == 'f') { //floor
                    map[col][row] = 'f';
                }else if (c == 'w') { //floor
                    map[col][row] = 'w';
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
            System.out.println("Файл не найден: " + e.getMessage());
        }
        int x_loc = 0, y_loc = 0;
        int door_count = 0;
        int trash_count = 0;
        for(int i = 0; i < 20; i++){
            for(int j = 0; j < 40; j++){
                if (map[j][i] == 0){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, true, false, false, false));
                }
                if (map[j][i] == 'g'){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, true, false, false, false, false));
                }
                if (map[j][i] == 'f'){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, false, true, false, false));
                }
                if (map[j][i] == 1){
                    obstacles.add(new Obstacle(x_loc,y_loc,30,30, true, false));
                }
                if (map[j][i] == 'w'){
                    obstacles.add(new Obstacle(x_loc,y_loc,30,30, false, true));
                }
                if(map[j][i] == 2){
                    List<Item> c = new ArrayList<>();
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, true, false, false, false, false));
                    trashCans.add(new TrashCan(x_loc, y_loc, c));
                    trash_count++;
                }
                if(map[j][i] == 3){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, true, false, false, false));
                    enemies.add(new Dog(x_loc, y_loc));
                }
                if(map[j][i] == 4){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, true, false, false, false));
                    enemies.add(new Rat(x_loc, y_loc));
                }
                if(map[j][i] == 5){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, false, true, false, false));
                    exit = new Exit(x_loc, y_loc);
                }
                if(map[j][i] == 6){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, true, false, false, false));
                    doors.add(new Door(x_loc, y_loc, 0, false));
                    door_count++;
                }
                if(map[j][i] == 7){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, true, false, false, false));
                    doors.add(new Door(x_loc, y_loc, 0, true));
                    door_count++;
                }
                if(map[j][i] == 8){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, false, true, false, false));
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, false, false, false, true));
                    items.add(new SilverCollar(x_loc, y_loc));
                }
                if(map[j][i] == 9){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, false, true, false, false));

                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, false, false, false, true));
                    items.add(new GoldCollar(x_loc, y_loc));
                }
                if(map[j][i] == 10){
                    backgrounds.add(new Background(x_loc, y_loc, 30, 30, false, false, false, true, false));

                    items.add(new Food(x_loc, y_loc));
                }
                x_loc += 30;
            }
            x_loc = 0;
            y_loc += 30;
        }
        if(door_count <= trash_count){
            for(int i = 0; i < door_count; i++){
                doors.get(i).setDoorNumber(i);
                trashCans.get(i).content.add(new Key(doors.get(i).x, doors.get(i).y, i));
            }
        }else{
            System.out.print("TOO MANY TRASHCANS");
        }

    }
}
