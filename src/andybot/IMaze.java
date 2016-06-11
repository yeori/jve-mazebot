package andybot;

import java.util.List;

public interface IMaze {

	int START = 10;
	int END = 20;
	int WALL = 0;
	int ROAD = 1;
	enum DIR {
		NORTH(0), EAST(1), SOUTH(2), WEST(3);
		
		private int code;
		DIR(int c) {
			code = c;
		}
		
		int dir() {
			return code
					;
		}
		
	};
	/**
	 * 미로의 이름읠 반환
	 * @return
	 */
	String getMazeName();

	/**
	 * 출구의 위치를 반환함
	 * @return
	 */
	Coord getExit();
	/**
	 * 맵의 행의 길이(y값 증가 방향)를 반환합니다.
	 * @return
	 */
	int getRowSize();

	/**
	 * 맵의 열의 길이(x값 증가 방향)을 반환합니다.
	 * @return
	 */
	int getColSize();
	
	/*
	 * 주어진 행과 열이 길인지 나타냄
	 * @param irow
	 * @param icol
	 * @return 길(Road)이면 true, 길이 아니면 false
	 */
	//boolean isRoad (int irow,int icol);
	
	/*
	 * 주어진 행과 열 위치가 벽인지 나타냄
	 * @param irow
	 * @param icol
	 * @return 벽(Wall)이면 true, 벽이 아니면 false
	 */
	//boolean isWall ( int irow, int icol );
	/**
	 * 현재 미로에 등록된 로봇들을 모두 반환합니다.
	 * @return
	 */
	List<IRobot> getRobots();

	/**
     * 미로의 상태변화(로봇의 위치, 게임 진행 상황)를 통보받는 리스너를 등록합니다.
     * 
     * @see IMazeListener
     * @param listener
     */
	void addMazeListener(IMazeListener listener);
	
	/**
	 * 미로의 상태변화(로봇의 위치, 게임 진행 상황)를 통보받는 리스너를 해제합니다.
	 * 
	 * @param listener
	 */
	void removeMazeListener ( IMazeListener listener);

}