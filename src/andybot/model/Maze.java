package andybot.model;

import java.awt.Point;

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
    public final static int WALL = 0;
    public final static int ROAD = 1;
    
    public static final int DIR_NORTH = 0;
    public static final int DIR_EAST = 1;
    public static final int DIR_SOUTH = 2;
    public static final int DIR_WEST = 3;
    
    private Robot bot;
    /**
     * 
     * @param rowSize - length of Y direction
     * @param colSize - length of X direction
     */
    public Maze ( int rowSize, int colSize) {
        map = new int[rowSize][colSize];
    }
    
    public void setRoad ( int x, int y ) {
        map[y][x] = ROAD;
    }

    public void setBot(Robot bot) {
        this.bot = bot;
        this.bot.addBotListener ( new BotUpdater() );
    }
    
    public class BotUpdater implements BotListener {

        @Override
        public void locationChanged(Point oldLoc, Point curLoc) {
            checkBotLocation ( curLoc);
        }

        @Override
        public void directionChanged(int oldDir, int newDir) {
            // TODO Auto-generated method stub
            
        }
    }

    private void checkBotLocation(Point loc) {
        try {
            if ( map[loc.y][loc.x] != ROAD ) {
                System.out.println("BOT DEAD");
            }
        } catch ( IndexOutOfBoundsException e) {
            System.out.println("OUT OF BOUND");
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
}
