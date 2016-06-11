package andybot.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import andybot.Coord;
import andybot.GameOverCause;
import andybot.IMaze;
import andybot.IMazeListener;
import andybot.IRobot;
import andybot.MazeException;

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
public class InternalMaze implements IMaze {

    private int [][] map ;
    private Robot bot;
    
    /**
     * 로봇이 가야할 목적지
     */
    private Coord end;
    /**
     * 미로의 상태변경(로봇의 이동, 게임 종료여부)을 통보받는 리스너들의 모음
     */
    private List<IMazeListener> listeners = new ArrayList<>();
    /**
     * 현재 맵의 이름
     */
	private String mapName;
    
    private static int seq = 10;
    
    private static int nextSeq() {
    	return seq ++ ;
    }
    /**
     * 주어진 크기의 미로를 생성함.
     * @param rowSize - length of Y direction
     * @param colSize - length of X direction
     */
//	public InternalMaze ( int rowSize, int colSize) {
//    	this("no named map + " + nextSeq(), rowSize, colSize);
//    }
//    
//    public InternalMaze(String mapName, int row, int col) {
//    	this.mapName = mapName;
//    	map = new int[row][col];
//	}
    
    public InternalMaze ( String mapName, int[][] data, Coord exit) {
    	this.mapName = mapName;
    	this.map = data;
    	this.end = exit;
    }
    
    @Override
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
//	/**
//	 * 로봇의 시작 위치를 반환합니다.
//	 */
//	public Coord getStartCoord() {
//		return new Coord(this.start);
//	}
	
//	void setStarCoord(int ir, int ic) {
//		this.start = new Coord(ic, ir);
//		map[ir][ic] = IMaze.START;
//		
//		setBot(ir, ic, "AndyBot");
//	}
	
	void setEndCoord(int ir, int ic) {
		this.end = new Coord(ir, ic);
		map[ir][ic] = IMaze.END;
	}


	@Override
	public Coord getExit() {
		return end;
	}
    
    @Override
    public void addMazeListener ( IMazeListener l) {
        if ( listeners.contains(l)){
            listeners.remove(l);
        }
        listeners.add(l);
    }
    
    @Override
    public void removeMazeListener ( IMazeListener l){
        listeners.remove(l);
    }
    
    class BotUpdater implements BotListener {

        @Override
        public void locationChanged(Coord oldLoc, Coord curLoc) {
            checkBotLocation ( oldLoc, curLoc);
        }
    }

    private void checkBotLocation(Coord oldLoc, Coord loc) {
        try {
        	if ( map[loc.rowIndex()][loc.colIndex()] != ROAD ) {
                notifyBotDead(bot, GameOverCause.NOT_A_ROAD);
            } else {
            	notifyBotMoved( bot, oldLoc);
            }
        	
        	if ( end.equals(loc) ) {
        		notifySuccess(bot);
        	} 
        } catch ( IndexOutOfBoundsException e) {
            notifyBotDead(bot, GameOverCause.OUT_OF_MAP);
        }
    }

    void notifyBotMoved ( IRobot bot, Coord oldCoord) {
    	for( IMazeListener ml : listeners) {
    		ml.robotMoved(bot, oldCoord);
    	}
    }

    void notifySuccess(IRobot bot) {
    	for(IMazeListener ml : listeners){
            ml.gameOver(bot, GameOverCause.SUCCESS);
        }
	}
    
	void notifyBotDead(IRobot bot, GameOverCause cause) {
        for(IMazeListener ml : listeners){
            ml.gameOver(bot, cause);
        }
    }
	void notifyBotAdded ( IRobot bot ){
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

    /* (non-Javadoc)
	 * @see andybot.model.IMaze#getRowSize()
	 */
    @Override
	public int getRowSize() {
        return map.length;
    }

    /* (non-Javadoc)
	 * @see andybot.model.IMaze#getColSize()
	 */
    @Override
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
    /**
     * 목적지 좌표 정보를 반환합니다.(로봇이 도달해야하는 위치)
     * @return
     */
	public Coord getEndCoord() {
		return this.end;
	}
	
	
	public IRobot creatBotAt(Coord start) {
		if ( this.bot != null) {
			throw new MazeException("bot already registered");
		}
		return setBot(start.rowIndex(), start.colIndex(), "robot");
	}
	
    private IRobot setBot(int ir, int ic, String botName) {
    	this.bot = new Robot(this, botName, new Coord(ir, ic));
        this.bot.clearListeners();
        this.bot.addBotListener ( new BotUpdater() );
        notifyBotAdded(bot);
        return this.bot;
    }
	
	
	boolean isRoad(int irow, int icol) {
		return typeOf ( irow, icol, IMaze.ROAD);
	}
	
	boolean isWall(int irow, int icol) {
		return typeOf(irow, icol, IMaze.WALL);
	}
	
	private boolean typeOf ( int irow, int icol, int type) {
		if ( !checkRange ( irow, 0, this.map.length)) {
			return false;
		}
		
		if ( !checkRange ( icol, 0, this.map[irow].length)) {
			return false;
		}
		return map[irow][icol] == IMaze.ROAD;
		
	}
	
	private boolean checkRange(int idx, int offset, int len) {
		return idx >= offset && idx < offset + len ;
	}
	
	void setWall(int ir, int ic) {
		map[ir][ic] = 0;
	}
	
	boolean canMove(Robot robot, int newX, int newY) {
		/*
		 * 로봇이 혼자이기 때문에 일단 무조건 움직이게 함.
		 */
		return true;
	}
	@Override
	public List<IRobot> getRobots() {
		return Arrays.asList(this.bot);
	}

	
}
