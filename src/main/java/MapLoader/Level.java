package MapLoader;

import geometry.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


public class Level {
    char matrix[][] = null;
    private GraphicsContext gc;
    private Canvas canvas;

    public void createMap() throws IOException {
        // createMap
        createMapFromFile();
    }

    public void createMapFromFile() throws IOException {
        BufferedReader bufferedReader = null;

        try {
            Reader reader = new FileReader("resources/levels/Level1.txt");

            bufferedReader = new BufferedReader(reader);


            String firstLine = bufferedReader.readLine();
            System.out.println(firstLine);

            int level = 0;
            int row = 0;
            int column = 0;

            String[] tokens = firstLine.split(" ");
            level = Integer.parseInt(tokens[0]);
            row = Integer.parseInt(tokens[1]);
            column = Integer.parseInt(tokens[2]);

            matrix = new char[row][column];

            for (int i = 0; i < row; i++) {
                String line = bufferedReader.readLine();
                for (int j = 0; j < column; j++) {
                    char character = line.charAt(j);
                    matrix[i][j] = character;
                }
            }

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    char character = matrix[i][j];
                    switch (character) {
                        case '#':
                            Point stillObjects;
                        {
                            //walltile
                            break;
                        }
                        case '*': {
                            //bricktile
                            break;
                        }
                        case '1': {
                            //enemyballom
                            break;
                        }
                        case '2': {
                            //enemyoneal
                        }

                        default: { //grasstile
                        }
                    }
                }
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
    }
}
