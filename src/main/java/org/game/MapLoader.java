package org.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MapLoader {
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
}
