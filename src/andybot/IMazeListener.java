package andybot;

/**
 * 미로의 상태 변경 정보(로봇 위치 변경, 게임 진행 상환 변화)에 대한 이벤트를 통보받는 리스너
 * @author chmin.seo
 *
 */
public interface IMazeListener {
    /**
     * 주어진 로봇이 미로에 추가되었음을 알림
     * @param newbot 미로에 새로 추가된 로봇
     */
    public void robotAdded(IRobot newbot);
    /**
     * <p>주어진 로봇이 이동했을때 호출됨.</p>
     * <p> 현재 로봇의 위치는 {@link Robot#getLocation() Robot.getLocation()}으로 조회할 수 있음.</p>
     * @param bot 이동한 로봇
     * @param oldCoord 이동하기 전의 위치 정보. 
     */
	public void robotMoved ( IRobot bot, Coord oldCoord );
	/**
	 * 게임이 끝났을때 호출 됨.
	 * @param bot
	 * @param cause 게임 종료 원인 {@link GameOverCause#OUT_OF_MAP
	 * <ul> 
	 * <li>GameOverCause.OUT_OF_MAP(로봇이 맵밖으로 나갔음)}, 
	 * <li>{@link GameOverCause#NOT_A_ROAD GameOverCause.NOT_A_ROAD(길이 아님)}
	 * <li>{@link GameOverCause#SUCCESS GameOverCause.SUCCESS(목적지도달)}
	 * </ul>
	 */
	public void gameOver ( IRobot bot, GameOverCause cause) ;
}
