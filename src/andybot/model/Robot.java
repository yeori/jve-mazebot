package andybot.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
/**
 * <pre>
 * 맵 위에서 움직이는 로봇을 나타냅니다.
 * 
 * <li> turnLeft - 현재 방향에서 왼쪽으로 회전합니다.
 * <li> turnRight - 현재 방향에서 오른쪽으로 회전합니다.
 * <li> moveForward - 현재 방향에서 앞으로 step만큼 전진합니다.
 * </pre>
 * @author chminseo
 *
 */
public class Robot {

	/**
	 * 현재 위치
	 */
    private Coord curLoc = null;
    private List<BotListener> listeners = new ArrayList<>() ;
    private int direction ;
    
    private String name ;
    
    /**
     * 주어진 위치에서 시작하는 로봇 인스턴스를 생성 후 반환합니다.
     * @param x - 시작할 x좌표
     * @param y - 시작할 y좌표
     */
    public Robot(int x, int y) {
        this(x, y, "unknown bot");
    }
    
    public Robot (int x, int y, String botName) {
        // 초기 방향은 북쪽, 위치는 (0,0)에서 시작합니다.
        direction = Maze.DIR_NORTH ;
        this.name = botName;
//        setLocation(x, y);
        this.curLoc = new Coord(x, y);
    }

	/**
     * 로봇의 현재 위치를 반환합니다.
     * @return (x,y)를 나타낸 Point 타입의 인스턴스
     */
    public Coord getLocation() {
        return new Coord(curLoc);
    }
    
    /**
     * 로봇의 x좌표값을 반환합니다.
     * @return x좌표
     */
    public int getX() {
        return curLoc.x();
    }
    
    /**
     * 로봇의 y좌표값을 반환합니다.
     * @return y좌표
     */
    public int getY() {
        return curLoc.y();
    }

    /**
     * 위로 1칸 이동함
     */
    public void moveUp() {
    	move(Maze.DIR_NORTH, 1);
    }
    
    /**
     * 아래로 1칸 이동함.
     */
    public void moveDown() {
    	move(Maze.DIR_SOUTH, 1);
    }
    
    /**
     * 왼쪽으로 1칸 이동.
     */
    public void moveLeft() {
    	move(Maze.DIR_WEST, 1);
    }
    
    /**
     * 오른쪽으로 한칸 이동.
     */
    public void moveRight() {
    	move(Maze.DIR_EAST, 1);
    }
    
    void move ( int newDir, int nStep ) {
    	int oldDir = this.direction;
    	this.direction = newDir;
    	if( oldDir != newDir ) {
    		notifyFacingChanged(oldDir, newDir);
    	}
    	moveForward(1);
    }
    
    /**
     * 현재 바라보는 방향으로 step 만큼 전진합니다.
     * @param step
     */
    public void moveForward(int step) {
        
        int newX = curLoc.x();
        int newY = curLoc.y();
        
        if ( direction == Maze.DIR_NORTH) {
            newY -= step;
        } else if ( direction == Maze.DIR_EAST) {
            newX += step ;
        } else if ( direction == Maze.DIR_SOUTH) {
            newY += step;
        } else if ( direction == Maze.DIR_WEST ) {
            newX -= step; 
        } else {
            throw new RuntimeException("what id this??? dir: " + direction);
        }
        setLocation(newX, newY);
    }
    
    /**
     * 현재 방향에서 왼쪽을 바라봅니다. 
     */
    public void turnLeft() {
        int oldDir = direction ;
        /* TODO 왼쪽으로 회전시킵니다.
         * 현재 방향에서 왼쪽으로 회전했을때의 새로운 방향을 계산해서
         * direction 필드에 저장합니다.
         */
        
        direction = (4 + direction -1) % 4;
        // 주의! 아래 메소드 호출 statement를 지우면 안됩니다.
        notifyFacingChanged ( oldDir, direction );
    }

    /**
     * 현재 방향에서 오른쪽을 바라봅니다.
     */
    public void turnRight() {
        int oldDir = direction;
        /* TODO 오른쪽으로 회전시킵니다.
         * 현재 방향에서 오른쪽으로 회전했을때의 새로운 방향을 계산해서
         * direction 필드에 저장합니다.
         */
        direction = (direction + 1) % 4;
        // 주의! 아래 메소드 호출 statement를 지우면 안됩니다.
        notifyFacingChanged ( oldDir, direction );
    }
    
    public void turnBack() {
    	turnRight();
    	turnRight();
    }

    private void notifyFacingChanged(int oldDir, int newDir) {
    	// FIXME 일단 막아둠.
        /*if ( listeners.isEmpty()) return ;
        for ( BotListener bl : listeners) {
            bl.directionChanged(oldDir, newDir);
        }*/
    }

    /**
     * do not modify this code
     * @param x 
     * @param y
     */
    void setLocation(int x, int y) {
        setLocation(x, y, true);
    }
    
    void setLocation(int x, int y, boolean shouldNotify) {
        Coord oldLoc = new Coord ( curLoc ); // copy
        
        this.curLoc = new Coord( x, y );
        
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
     * @see Maze#DIR_NORTH
     * @see Maze#DIR_EAST
     * @see Maze#DIR_SOUTH
     * @see Maze#DIR_WEST
     * 
     * @return Maze 에 정의된 4방향값 중 하나.
     */
    public int getDirection() {
        return direction;
    }
    public String getName() {
        return name;
    }

	void clearListeners() {
		this.listeners.clear();
	}
}

