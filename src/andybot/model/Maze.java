package andybot.model;

import andybot.Loc;
import andybot.BotStatus;
import andybot.MazeException;
import andybot.RobotException;

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

	final public static int WALL = 0;
	final public static int ROAD = 1;
	final public static int START =  3;
	final public static int EXIT = 5;
	
	/**
	 * out of map
	 */
	private static int OOM = 16;
	
    private int [][] map ;
    private Robot bot;
    
    /**
     * location of an exit
     */
    private Loc exit;
    /**
     * map name
     */
	private String mapName;

	/**
	 * 주어진 크기의 미로를 생성함.
	 * @param mapName name of maze
	 * @param data maze data
	 * @param exit location of an exit
	 */
    public Maze ( String mapName, int[][] data, Loc exit) {
    	this.mapName = mapName;
    	this.map = data;
    	this.exit = exit;
    }
    
	public String getMazeName() {
    	return mapName;
    }
    /**
     * 주어진 위치에 길을 설치함.
     * @param ir
     * @param ic
     */
    void setRoad ( int ir, int ic ) {
        map[ir][ic] = ROAD;
    }
    
    public BotStatus moveBot ( Robot robot, Robot.DIR dir ) {
    	if ( this.bot != robot ) {
    		throw new RobotException(String.format(
    				"ROBOT '%s' does not belong to this maze", 
    				robot.getName()) );
    	}
    	robot.move(dir, 1);
    	return getBotStatus(robot);
    }
    
    private BotStatus getBotStatus(Robot robot ) {
    	Loc loc = robot.getLocation();
    	int irow = loc.rowIndex();
    	int icol = loc.colIndex();
    	
    	if ( !isValid(irow, 0, map.length) || !isValid(icol, 0, map[0].length )) {
    		return BotStatus.OUT_OF_MAP;
        } else if ( isWall(irow, icol) ) {
        	return BotStatus.NOT_ON_THE_ROAD;
        } else if ( exit.equals(loc) ) {
        	return BotStatus.ON_THE_EXIT;
        } else {
        	return BotStatus.ON_THE_ROAD;
        }
    }
    
	void setEndCoord(int ir, int ic) {
		this.exit = new Loc(ir, ic);
		map[ir][ic] = EXIT;
	}

	public Loc getExit() {
		return exit;
	}
	
    /**
     * 맵의 정보(길 또는 벽)를 나타내는 2차원 배열을 반환합니다.
     * @return
     */
    public int[][] getMapData() {
        return map;
    }

	public int countRow() {
        return map.length;
    }

	public int countColumn() {
        return map[0].length;
    }

    /**
     * 맵에 등록된 로봇 인스턴스를 반환합니다.
     * @return
     */
    public Robot getRobot() {
        return bot;
    }
    /**
     * 목적지 좌표 정보를 반환합니다.(로봇이 도달해야하는 위치)
     * @return
     */
	public Loc getEndCoord() {
		return this.exit;
	}
	
	public Robot creatBotAt(Loc startLoc, String botName) {
		if ( bot != null) {
			throw new MazeException("bot already registered");
		}
		bot = new Robot(this, botName, startLoc);
		return bot;
	}
	
	boolean isRoad(int irow, int icol) {
		return typeOf ( irow, icol, ROAD);
	}
	
	boolean isWall(int irow, int icol) {
		return typeOf(irow, icol, WALL);
	}
	
	private boolean typeOf ( int irow, int icol, int type) {
		if ( !isValid ( irow, 0, this.map.length)) {
			return false;
		}
		
		if ( !isValid ( icol, 0, this.map[irow].length)) {
			return false;
		}
		return map[irow][icol] == type;
	}
	
	private boolean isValid(int idx, int offset, int len) {
		return idx >= offset && idx < offset + len ;
	}
	
	void setWall(int ir, int ic) {
		map[ir][ic] = WALL;
	}
	
	boolean canMove(Robot robot, int newX, int newY) {
		/*
		 * 로봇이 혼자이기 때문에 일단 무조건 움직이게 함.
		 */
		return true;
	}
	
}
