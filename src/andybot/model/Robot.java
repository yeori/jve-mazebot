package andybot.model;

import andybot.Loc;
/**
 * <pre>
 * 맵 위에서 움직이는 로봇을 나타냅니다.
 * 
 * </pre>
 * @author chminseo
 *
 */
public class Robot {

	public enum DIR { NORTH, EAST, SOUTH, WEST};
	private Maze maze;
	
	private Loc curLoc = null;
    
    private DIR direction ;
    
    private String name ;
    
    public Robot (Maze maze, String botName, Loc initialLoc) {
    	this.maze = maze;
    	this.name = botName;
    	this.curLoc = initialLoc;
    	this.direction = DIR.NORTH;
    }

	public Loc getLocation() {
        return new Loc(curLoc);
    }
    
	public int columnIndex() {
        return curLoc.colIndex();
    }
    
	public int rowIndex() {
        return curLoc.rowIndex();
    }

	public boolean canMoveUp() {
		return maze.isRoad(curLoc.rowIndex()-1, curLoc.colIndex());
	}

	public boolean canMoveRight() {
		return maze.isRoad(curLoc.rowIndex(), curLoc.colIndex()+1);
	}

	public boolean canMoveDown() {
		return maze.isRoad(curLoc.rowIndex()+1, curLoc.colIndex());
	}

	public boolean canMoveLeft() {
		return maze.isRoad(curLoc.rowIndex(), curLoc.colIndex()-1);
	}
	
    /**
     * 위로 1칸 이동함
     */
    public void moveUp() {
    	move(DIR.NORTH, 1);
    }
    
    /**
     * 아래로 1칸 이동함.
     */
    public void moveDown() {
    	move(DIR.SOUTH, 1);
    }
    
    /**
     * 왼쪽으로 1칸 이동.
     */
    public void moveLeft() {
    	move(DIR.WEST, 1);
    }
    
    /**
     * 오른쪽으로 한칸 이동.
     */
    public void moveRight() {
    	move(DIR.EAST, 1);
    }
    
    /**
     * 현재 바라보는 방향으로 step 만큼 전진합니다.
     * @param step
     */
    void move(DIR dir, int step) {
//        int newX = curLoc.x();
//        int newY = curLoc.y();
        int newR = curLoc.rowIndex();
        int newC = curLoc.colIndex();
        
        if ( dir == DIR.NORTH) {
            newR -= step;
        } else if ( dir == DIR.EAST) {
            newC += step ;
        } else if ( dir == DIR.SOUTH) {
            newR += step;
        } else if ( dir == DIR.WEST ) {
            newC -= step; 
        } else {
            throw new RuntimeException("what id this??? dir: " + direction);
        }
        
        this.direction = dir;
        this.curLoc = new Loc( newR, newC );
    }

    /**
     * 로봇이 현재 바라보는 방향값을 반환합니다.
     * 
     * @see Maze#DIR_NORTH
     * @see Maze#DIR_EAST
     * @see Maze#DIR_SOUTH
     * @see Maze#DIR_WEST
     * 
     * @return Maze 에 정의된 4방향값 중 하나.
     */
    public DIR getDirection() {
        return direction;
    }
    /**
     * name of robot
     * @return
     */
	public String getName() {
        return name;
    }
}

