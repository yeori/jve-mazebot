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

    private Point loc = null;
    private List<BotListener> listeners = new ArrayList<>() ;
    private int direction ;
    
    /**
     * 로봇 인스턴스를 생성 후 반환합니다.
     */
    public Robot ( ) {
        // 초기 방향은 북쪽, 위치는 (0,0)에서 시작합니다.
        direction = Maze.DIR_NORTH ;
        setLocation(0, 0);
    }
    /**
     * 주어진 위치에서 시작하는 로봇 인스턴스를 생성 후 반환합니다.
     * @param x - 시작할 x좌표
     * @param y - 시작할 y좌표
     */
    public Robot(int x, int y) {
        direction = Maze.DIR_NORTH;
        setLocation(x, y);
    }

    /**
     * 로봇의 현재 위치를 반환합니다.
     * @return (x,y)를 나타낸 Point 타입의 인스턴스
     */
    public Point getLocation() {
        return new Point(loc.x, loc.y);
    }
    
    /**
     * 로봇의 x좌표값을 반환합니다.
     * @return x좌표
     */
    public int getX() {
        return loc.x;
    }
    
    /**
     * 로봇의 y좌표값을 반환합니다.
     * @return y좌표
     */
    public int getY() {
        return loc.y;
    }

    /**
     * 현재 바라보는 방향으로 step 만큼 전진합니다.
     * @param step
     */
    public void moveForward(int step) {
        /* TODO 앞으로 step만큼 전진하는 코드를 구현해봅니다.
         * 현재 방향에 따라서 적절한 위치를 계산한 후 setLocation을 호출합니다. 
         */
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
        
        // 주의! 아래 메소드 호출 statement를 지우면 안됩니다.
        notifyFacingChanged ( oldDir, direction );
    }

    private void notifyFacingChanged(int oldDir, int newDir) {
        if ( listeners.isEmpty()) return ;
        for ( BotListener bl : listeners) {
            bl.directionChanged(oldDir, newDir);
        }
    }

    /**
     * do not modify this code
     * @param x 
     * @param y
     */
    private void setLocation(int x, int y) {
        Point oldLoc = loc ==null ? null : new Point ( loc.x, loc.y);
        if ( loc == null) {
            loc = new Point();
        }
        this.loc.x = x;
        this.loc.y = y;
        
        notifyLocationChanged ( oldLoc, new Point(x, y));
    }
    
    private void notifyLocationChanged(Point oldLoc, Point newLoc) {
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
}

