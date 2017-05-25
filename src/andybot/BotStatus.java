package andybot;

public enum BotStatus {
	ON_THE_ROAD ("KEEP GOING"),
	/**
	 * 로봇이 미로 밖으로 나갔음.
	 */
    OUT_OF_MAP("ROBOT OUT OF MAP"),
    /**
     * 로봇이 현재 길이 아닌 곳에 있음.
     */
    NOT_ON_THE_ROAD("ROBOT OUT OF ROAD"),
    /**
     * 종점에 도착했음.
     */
    ON_THE_EXIT("SUCCESS");

    String message;
    
    BotStatus( String msg) {
        message = msg;
    }
    public String getCause() {
        return message;
    }
}