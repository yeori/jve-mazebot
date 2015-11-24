package andybot.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import andybot.model.IMazeListener.GameOverCause;

/**
 *  <pre>
 *  로봇이 움직이는 2차원 맵을 나타냅니다.
 *     
 *     +------------> X(colSize)
 *     | . P . . . .
 *     | . P . P P P
 *     | P P P P . .
 *     | P . . P . .
 *     | P P . . . .
 *     v 
 *  Y(rowSize)
 *  
 *  좌측 상단 (0,0)을 기준으로 x좌표는 가로 방향, y좌표는 세로 방향으로 증가합니다. 
 * 
 * </pre>
 * @author chminseo
 *
 */
public class Maze {

    private int [][] map ;
    public final static int START = 10;
    public final static int END   = 20;
    
    public final static int WALL = 0;
    public final static int ROAD = 1;
    
    public static final int DIR_NORTH = 0;
    public static final int DIR_EAST = 1;
    public static final int DIR_SOUTH = 2;
    public static final int DIR_WEST = 3;
    
    private Robot bot;
    
    private Coord start;
    private Coord end;
    
    private List<IMazeListener> listeners = new ArrayList<>();
	private String mapName;
    
    private static int seq = 10;
    
    private static int nextSeq() {
    	return seq ++ ;
    }
    /**
     * 
     * @param rowSize - length of Y direction
     * @param colSize - length of X direction
     */
    public Maze ( int rowSize, int colSize) {
    	this("no named map + " + nextSeq(), rowSize, colSize);
    }
    
    public Maze(String mapName, int row, int col) {
    	this.mapName = mapName;
    	map = new int[row][col];
	}
    
    public String getMazeName() {
    	return mapName;
    }

	public void setRoad ( int ir, int ic ) {
        map[ir][ic] = ROAD;
    }
	
	public Coord getStartLoc() {
		return new Coord(this.start);
	}
	
	public void setStartLoc(int ir, int ic) {
		this.start = new Coord(ic, ir);
		map[ir][ic] = Maze.START;
		
		setBot(ir, ic, "AndyBot");
	}
	
	public void setEndLoc(int ir, int ic) {
		this.end = new Coord(ic, ir);
		map[ir][ic] = Maze.END;
	}

    Robot setBot(int ir, int ic, String botName) {
    	this.bot = new Robot(ic, ir, botName);
//        this.bot.setLocation(ic, ir, false);
        this.bot.clearListeners();
        this.bot.addBotListener ( new BotUpdater() );
        notifyBotAdded(bot);
        return this.bot;
    }
    
    public void addMazeListener ( IMazeListener l) {
        if ( listeners.contains(l)){
            listeners.remove(l);
        }
        listeners.add(l);
    }
    
    public void removeMazeListener ( IMazeListener l){
        listeners.remove(l);
    }
    
    class BotUpdater implements BotListener {

        @Override
        public void locationChanged(Coord oldLoc, Coord curLoc) {
            checkBotLocation ( oldLoc, curLoc);
        }

        @Override
        public void directionChanged(int oldDir, int newDir) {
            // TODO Auto-generated method stub
            
        }
    }

    private void checkBotLocation(Coord oldLoc, Coord loc) {
        try {
        	if ( map[loc.y()][loc.x()] == START ) {
        		System.out.println("START");
        	} else if ( map[loc.y()][loc.x()] == END ) {
        		notifySuccess(bot);
        	} else if ( map[loc.y()][loc.x()] != ROAD ) {
                notifyBotDead(bot, GameOverCause.NOT_A_ROAD);
            } else {
            	notifyBotMoved( bot, oldLoc);
            }
        } catch ( IndexOutOfBoundsException e) {
            notifyBotDead(bot, GameOverCause.OUT_OF_MAP);
        }
    }
    
    void notifyBotMoved ( Robot bot, Coord oldCoord) {
    	for( IMazeListener ml : listeners) {
    		ml.robotMoved(bot, oldCoord);
    	}
    }

    void notifySuccess(Robot bot) {
    	for(IMazeListener ml : listeners){
            ml.gameOver(bot, GameOverCause.SUCCESS);
        }
	}
    
	void notifyBotDead(Robot bot, GameOverCause cause) {
        for(IMazeListener ml : listeners){
            ml.gameOver(bot, cause);
        }
    }
	void notifyBotAdded ( Robot bot ){
        for(IMazeListener ml : listeners){
            ml.robotAdded(bot);
        }
    }

    /**
     * 맵의 정보(길 또는 벽)를 나타내는 2차원 배열을 반환합니다.
     * @return
     */
    public int[][] getMapData() {
        return map;
    }

    /**
     * 맵의 행의 길이(y값 증가 방향)를 반환합니다.
     * @return
     */
    public int getRowSize() {
        return map.length;
    }

    /**
     * 맵의 열의 길이(x값 증가 방향)을 반환합니다.
     * @return
     */
    public int getColSize() {
        return map[0].length;
    }

    /**
     * 맵에 등록된 로봇 인스턴스를 반환합니다.
     * @return
     */
    public Robot getRobot() {
        return bot;
    }
    
	public Coord getEndCoord() {
		return this.end;
	}
	public Robot addRobot(int x, int y, String botName) {
		return setBot(y, x, botName);
		
	}

	
}
