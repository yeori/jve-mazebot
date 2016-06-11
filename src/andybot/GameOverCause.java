package andybot;

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
}