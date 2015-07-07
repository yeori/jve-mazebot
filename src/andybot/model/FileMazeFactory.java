package andybot.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileMazeFactory implements IMazeFactory {

    private File mazeFile;

    public FileMazeFactory(File f) {
        mazeFile = f;
    }

    @Override
    public Maze createMaze() {
        Scanner sc;
        try {
            sc = new Scanner(mazeFile);
            int row = readRow(sc);
            int col = readCol(sc);
            Maze mz = new Maze(row, col);
            for (int ir = 0; ir < row; ir++) {
                initMazeRow(ir, mz, sc.nextLine());
            }
            return mz;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("check file path: " + mazeFile.getAbsolutePath());
        }
    }

    private void initMazeRow(int ir, Maze mz, String rows) {
        for (int ic = 0; ic < rows.length(); ic++) {
            char ch = rows.charAt(ic);
            if (ch != '.') {
                mz.setRoad(ic, ir);
            }
        }
    }

    private int readRow(Scanner sc) {
        // r=x
        String line = sc.nextLine();
        return Integer.parseInt(line.substring(line.indexOf('=') + 1).trim());
    }

    private int readCol(Scanner sc) {
        // c=x
        String line = sc.nextLine();
        return Integer.parseInt(line.substring(line.indexOf('=') + 1).trim());
    }

}
