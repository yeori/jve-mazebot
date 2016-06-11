package andybot.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import andybot.Coord;
import andybot.IMaze;
import andybot.IRobot;
/**
 * <pre>
 * 맵 위에서 움직이는 로봇을 나타냅니다.
 * 
 * </pre>
 * @author chminseo
 *
 */
class Robot implements IRobot {

	private InternalMaze maze;
	private Coord initLoc;
	/**
	 * 현재 위치
	 */
    private Coord curLoc = null;
    
    private List<BotListener> listeners = new ArrayList<>() ;
    private IMaze.DIR direction ;
    
    private String name ;
    
    public Robot (InternalMaze maze, String botName, Coord initialLoc) {
    	this.maze = maze;
    	this.name = botName;
    	this.initLoc = initialLoc;
    	this.curLoc = initialLoc;
    	this.direction = IMaze.DIR.NORTH;
    }

    @Override
	public Coord getLocation() {
        return new Coord(curLoc);
    }
    
    @Override
	public int columnIndex() {
        return curLoc.colIndex();
    }
    
    @Override
	public int rowIndex() {
        return curLoc.rowIndex();
    }

	@Override
	public boolean canMoveUp() {
		return maze.isRoad(curLoc.rowIndex()-1, curLoc.colIndex());
	}

	@Override
	public boolean canMoveRight() {
		return maze.isRoad(curLoc.rowIndex(), curLoc.colIndex()+1);
	}

	@Override
	public boolean canMoveDown() {
		return maze.isRoad(curLoc.rowIndex()+1, curLoc.colIndex());
	}

	@Override
	public boolean canMoveLeft() {
		return maze.isRoad(curLoc.rowIndex(), curLoc.colIndex()-1);
	}
	
    /**
     * 위로 1칸 이동함
     */
    @Override
    public void moveUp() {
    	move(IMaze.DIR.NORTH, 1);
    }
    
    /**
     * 아래로 1칸 이동함.
     */
    @Override
    public void moveDown() {
    	move(IMaze.DIR.SOUTH, 1);
    }
    
    /**
     * 왼쪽으로 1칸 이동.
     */
    @Override
    public void moveLeft() {
    	move(IMaze.DIR.WEST, 1);
    }
    
    /**
     * 오른쪽으로 한칸 이동.
     */
    @Override
    public void moveRight() {
    	move(IMaze.DIR.EAST, 1);
    }
    
    /**
     * 현재 바라보는 방향으로 step 만큼 전진합니다.
     * @param step
     */
    void move(IMaze.DIR dir, int step) {
//        int newX = curLoc.x();
//        int newY = curLoc.y();
        int newR = curLoc.rowIndex();
        int newC = curLoc.colIndex();
        
        if ( dir == IMaze.DIR.NORTH) {
            newR -= step;
        } else if ( dir == IMaze.DIR.EAST) {
            newC += step ;
        } else if ( dir == IMaze.DIR.SOUTH) {
            newR += step;
        } else if ( dir == IMaze.DIR.WEST ) {
            newC -= step; 
        } else {
            throw new RuntimeException("what id this??? dir: " + direction);
        }
        
        
        this.direction = dir;
        setLocation(newR, newC, true);
    }

    void setLocation(int irow, int icol) {
        setLocation(irow, icol, true);
    }
    
    void setLocation(int irow, int icol, boolean shouldNotify) {
        Coord oldLoc = new Coord ( curLoc ); // copy
        
        this.curLoc = new Coord( irow, icol );
        
        if( shouldNotify ) {
        	notifyLocationChanged ( oldLoc, curLoc);
        }
    }
    
    private void notifyLocationChanged(Coord oldLoc, Coord newLoc) {
        if ( listeners.isEmpty()) return ;
        for ( BotListener bl : listeners) {
            bl.locationChanged(oldLoc, newLoc);
        }
    }

    /**
     * 로봇의 상태 변화를 통보받는 리스너를 추가합니다.
     * 로봇의 위치나 바라보는 방향이 바뀔때마다 등록된 리스너들의 메소드가 호출됩니다.
     * 
     * @param listener
     */
    public void addBotListener(BotListener listener) {
        this.listeners.add(listener);
    }

    /**
     * 로봇이 현재 바라보는 방향값을 반환합니다.
     * 
     * @see InternalMaze#DIR_NORTH
     * @see InternalMaze#DIR_EAST
     * @see InternalMaze#DIR_SOUTH
     * @see InternalMaze#DIR_WEST
     * 
     * @return Maze 에 정의된 4방향값 중 하나.
     */
    public IMaze.DIR getDirection() {
        return direction;
    }
    /* (non-Javadoc)
	 * @see andybot.model.IRobot#getName()
	 */
    @Override
	public String getName() {
        return name;
    }

	void clearListeners() {
		this.listeners.clear();
	}
}

