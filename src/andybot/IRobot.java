package andybot;

public interface IRobot {

	/**
	 * 로봇의 현재 위치를 반환합니다.
	 * @return (row,col)를 나타낸 Point 타입의 인스턴스
	 */
	Coord getLocation();

	/**
	 * 로봇의 x좌표값을 반환합니다.
	 * @return x좌표
	 */
	int columnIndex();

	/**
	 * 로봇의 y좌표값을 반환합니다.
	 * @return y좌표
	 */
	int rowIndex();
	/**
	 * 로봇의 이름을 반환합니다.
	 * @return
	 */
	String getName();
	/**
	 * 로봇이 현재 바라보고 있는 방향을 나타냅니다.
	 * 
	 * @return
	 */
	IMaze.DIR getDirection();
	/**
	 * 로봇을 서쪽(왼쪽)으로 한칸 이동시킵니다.
	 */
	void moveLeft();
	/**
	 * 로봇을 동쪽(오른쪽)으로 한칸 이동시킵니다.
	 */
	void moveRight();
	/**
	 * 로봇을 북쪽(위쪽)으로 한칸 이동시킵니다.
	 */
	void moveUp();
	/**
	 * 로봇을 남쪽(아래로)한칸 이동시킵니다.
	 */
	void moveDown();

	/**
	 * 북쪽(위로) 방향으로 한칸 이동할 수 있는지 나타냅니다. 
	 * @return 갈 수 있는 길이면 true, 길이 아니거나 미로의 경계를 벗어나게 되면 false를 반환함.
	 */
	boolean canMoveUp();
	/**
	 * 동쪽(오른쪽) 방향으로 한칸 이동할 수 있는지 나타냅니다. 
	 * @return 갈 수 있는 길이면 true, 길이 아니거나 미로의 경계를 벗어나게 되면 false를 반환함.
	 */
	boolean canMoveRight();
	/**
	 * 남쪽(아래) 방향으로 한칸 이동할 수 있는지 나타냅니다. 
	 * @return 갈 수 있는 길이면 true, 길이 아니거나 미로의 경계를 벗어나게 되면 false를 반환함.
	 */
	boolean canMoveDown();
	/**
	 * 서쪽(왼쪽) 방향으로 한칸 이동할 수 있는지 나타냅니다. 
	 * @return 갈 수 있는 길이면 true, 길이 아니거나 미로의 경계를 벗어나게 되면 false를 반환함.
	 */
	boolean canMoveLeft();

}