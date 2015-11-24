package andybot.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class MazeFactory implements IMazeFactory {

    private InputStream mazeInputStream;

    public MazeFactory(InputStream in) {
        mazeInputStream = in;
    }

    @Override
    public Maze createMaze() {
        Scanner sc;
        sc = new Scanner(mazeInputStream);
        String name = readMapName(sc);
        int row = readRow(sc);
        int col = readCol(sc);
        Maze mz = new Maze(name, row, col);
        for (int ir = 0; ir < row; ir++) {
            initMazeRow(ir, mz, sc.nextLine());
        }
        return mz;
    }


	private void initMazeRow(int ir, Maze mz, String rows) {
        for (int ic = 0; ic < rows.length(); ic++) {
            char ch = rows.charAt(ic);
            if (ch == '@') {
                mz.setRoad(ir, ic);
            } else if ( ch == 's') {
            	mz.setStarCoord (ir, ic);
            } else if ( ch == 'e') {
            	mz.setEndCoord(ir, ic);
            }
        }
    }

	private String readMapName(Scanner sc) {
		String line = sc.nextLine();
		return line.substring(line.indexOf('=') + 1).trim();
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
