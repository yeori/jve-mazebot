package southbot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import andybot.Loc;
import andybot.model.Maze;
import andybot.model.Robot;
import andybot.model.path.IPathFinder;

public class SouthMovement implements IPathFinder {

	private int [][] map ;
	public enum MODE {FORWARD, BACKWARD };

	private Map<Loc, List<Loc>> roads = new HashMap<>();
	
	private Stack<Loc> path = new Stack<>();
	
	private MODE curMode = MODE.FORWARD;
	
	@Override
	public String getName() {
		return "SoutBot";
	}
	
	@Override
	public void onReady(Maze maze, Robot robot ) {
		int r = maze.countRow();
		int c = maze.countColumn();
		/*
		 * 2차원 배열의 각각의 값이 0이면 알 수 없음. -1 이면 갈 수 없음. 1이면 갈 수 있음.
		 */
		map = new int[r][c]; // 모두 0으로 초기화됨.
	}
	
	@Override
	public void moveRobot(Maze maze, Robot robot) {
		Loc botCoord = robot.getLocation();
		int ir = robot.rowIndex();
		int ic = robot.columnIndex();
		System.out.println("bot at : " + ir + ", "  + ic);
		checkAsVisited (ir, ic );
		Loc next = null;
		
		if  ( curMode == MODE.FORWARD ) {
			
			List<Loc> adjacent = findNeighBors( robot );
			adjacent = filterVisitedCoords ( adjacent );
			
			if ( adjacent.size() == 1) {
				roads.put(botCoord, adjacent);
				
			} else if( adjacent.size() == 0 ) {
				System.out.println("막혔음");
				curMode = MODE.BACKWARD;
				roads.put(botCoord, new ArrayList<>(Arrays.asList( path.pop() )));
			} else {
				System.out.println("두 개 이상의 이동 가능한 위치 발견");
				roads.put(botCoord, adjacent);
			}
			
			next = roads.get(botCoord).remove(0);
			if ( curMode == MODE.FORWARD ) {
				path.push(botCoord);
			}
		} else {
			if ( hasMorePath (botCoord )) {
				next = this.roads.get(botCoord).remove(0);
				curMode = MODE.FORWARD;
			} else {
				next = path.pop();				
			}
		}
		/*
		 * 다음에 이동 가능한 좌표들이 들어있음.
		 * 이중에서 이전 단계에서 이미 방문했던 위치를 제거하고 한 번도 방문하지 않은 위치만 찾아냄
		 */
		moveRobot ( robot, next );
	}


	private boolean hasMorePath(Loc coord) {
		List<Loc> r = this.roads.get(coord);
		return r != null && r.size() > 0 ; 
	}

	private void moveRobot(Robot robot, Loc next) {
		int dr = next.rowIndex() - robot.rowIndex() ;
		int dc = next.colIndex() - robot.columnIndex();
		System.out.println(dr + ", " + dc);
		if ( dr == -1 ) {
			robot.moveUp();
		} else if ( dr == 1 ) {
			robot.moveDown();
		} else if ( dc == -1) {
			robot.moveLeft();
		} else if ( dc == 1) {
			robot.moveRight();
		}
	}

	private List<Loc> filterVisitedCoords(List<Loc> coords) {
		for ( int ir = 0 ; ir < map.length; ir ++ ) {
			for ( int ic = 0 ; ic < map[ir].length; ic ++) {
				if ( map[ir][ic] > 0 ) {
					coords.remove(new Loc ( ir, ic ));
				}
			}
		}
		return coords;
	}

	private void checkAsVisited(int ir, int ic) {
		map [ir][ic] = 1;
	}

	private List<Loc> findNeighBors( Robot bot) {
		List<Loc> coords = new ArrayList<>();
		int ir = bot.rowIndex();
		int ic = bot.columnIndex();
		if ( bot.canMoveUp() ) {
			coords.add ( new Loc(ir-1, ic) );
		}
		if ( bot.canMoveRight() ) {
			coords.add( new Loc(ir, ic+1 ));
		}
		if ( bot.canMoveDown()) {
			coords.add( new Loc(ir+1, ic ));
		}
		if ( bot.canMoveLeft()) {
			coords.add( new Loc(ir, ic-1 ));
		}
		return coords;
	}

}
