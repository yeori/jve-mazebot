package andybot.model;
/**
 * 미로에서 움직이는 로봇에 대한 이벤트를 통보받는 리스너
 * @author chmin.seo
 *
 */
public interface IMazeListener {
    public enum GameOverCause {
    	/**
    	 * 로봇이 미로 밖으로 나갔음.
    	 */
        OUT_OF_MAP("OUT OF MAP"),
        /**
         * 로봇이 현재 길이 아닌 곳에 있음.
         */
        NOT_A_ROAD("NOT A ROAD"),
        /**
         * 종점에 도착했음.
         */
        SUCCESS("SUCCESS");

        String message;
        
        GameOverCause( String msg) {
            message = msg;
        }
        public String getCause() {
            return message;
        }
    };

    public void robotAdded(Robot newbot);

	public void robotMoved ( Robot bot, Coord oldCoord );
	
	public void gameOver ( Robot bot, GameOverCause cause) ;
}
