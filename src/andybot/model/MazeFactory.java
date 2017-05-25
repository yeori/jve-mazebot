package andybot.model;

import java.io.InputStream;
import java.util.Scanner;

import andybot.Coord;
import andybot.IMaze;
import andybot.IMazeFactory;
import andybot.MazeException;

public class MazeFactory implements IMazeFactory {

    private InputStream mazeInputStream;

    public MazeFactory(InputStream in) {
        mazeInputStream = in;
    }

    private Coord start ;
    private Coord end ;
    @Override
    public IMaze createMaze() {
        Scanner sc;
        sc = new Scanner(mazeInputStream);
        String name = readMapName(sc);
        int row = readRow(sc);
        int col = readCol(sc);
        
        int [][] data = new int[row][col];
        for (int ir = 0; ir < row; ir++) {
            initMazeRow(ir, data, sc.nextLine());
        }
        
        if ( end == null ) {
        	throw new MazeException("no exit found.");
        }
        
        InternalMaze mz = new InternalMaze(name, data, end);
        
        if ( start != null ) {
        	mz.creatBotAt ( start );
        }
        return mz;
    }


	private void initMazeRow(final int ir, int[][] data, String rows) {
		if ( data[ir].length < rows.length()) {
			throw new MazeException("row string is greater than maze: " + rows + "at row " + ( ir+1));
		}
		for (int ic = 0; ic < data[ir].length; ic++) {
            char ch = rows.charAt(ic);
            if (ch == '@' || ch == 's' || ch == 'e') {
                data[ir][ic] = IMaze.ROAD;
            } else {
            	data[ir][ic] = IMaze.WALL;
            }
            
            if ( ch == 's') {
            	start = new Coord(ir, ic);
            }
            
            if ( ch == 'e') {
            	end = new Coord ( ir, ic);
            }
        }
	}


	/*private void initMazeRow(int ir, InternalMaze mz, String rows) {
        for (int ic = 0; ic < rows.length(); ic++) {
            char ch = rows.charAt(ic);
            if (ch == '@' || ch == 's' || ch == 'e') {
                mz.setRoad(ir, ic);
            } else {
            	mz.setWall ( ir,ic);
            }
            
            if ( ch == 's') {
            	start = new Coord(ic, ir);
            }
            
            if ( ch == 'e') {
            	end = new Coord ( ic, ir);
            }
        }
    }*/

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
