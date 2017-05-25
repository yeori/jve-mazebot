package andybot.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import andybot.Loc;
import andybot.MazeException;

public class MazeFactory {

    private InputStream mazeInputStream;

    public MazeFactory(InputStream in) {
        mazeInputStream = in;
    }

    private Loc start ;
    private Loc end ;

    public Maze createMaze() throws MazeException {
        Scanner sc;
        sc = new Scanner(mazeInputStream);
        String name = readMapName(sc);
        
        List<int []> data = new ArrayList<>();
        while ( sc.hasNextLine()) {
        	loadEachRow(data.size(), data, sc.nextLine().trim());        	
        }
        
        if ( start == null) {
        	throw new MazeException("robot location not specified with a character 's'.");        	
        }
        if ( end == null ) {
        	throw new MazeException("an exit not specified with a character 'e'.");
        }
        
        int [][] arr = data.stream().toArray( R -> new int[R][]);
        Maze mz = new Maze(name, arr, end);
        mz.creatBotAt ( start, "ANDY");
        
        return mz;
    }

	private void loadEachRow(final int ir, List<int[] >data, String line) {
		if ( data.size() > 0 && data.get(0).length  != line.length() ) {
			throw new MazeException(
					"length of columns should be %d, but %d\n[%s] at line %d", 
					data.get(0).length,  
					line.length(),
					line,
					ir+2) ;
		}
		
		int [] row = new int[ line.length()];
		for (int ic = 0; ic < row.length; ic++) {
            char ch = line.charAt(ic);
            if (ch == '@' || ch == 's' || ch == 'e') {
                row[ic] = Maze.ROAD;
            } else {
            	row[ic] = Maze.WALL;
            }
            
            if ( ch == 's' || ch == 'S') {
            	start = new Loc(ir, ic);
            }
            
            if ( ch == 'e' || ch == 'E') {
            	end = new Loc ( ir, ic);
            }
        }
		data.add(row);
	}

	private String readMapName(Scanner sc) {
		String line = sc.nextLine();
		return line.substring(line.indexOf('=') + 1).trim();
	}
}
