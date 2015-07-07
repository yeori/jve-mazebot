package andybot.model;

public interface IMazeListener {
    public enum DeathCause {
        OUT_OF_BOUND("OUT OF BOUND"), DROPPED("NOT A ROAD");

        String message;
        
        DeathCause( String msg) {
            message = msg;
        }
        public String getCause() {
            return message;
        }
    };

    public void robotAdded(Robot newbot);

    public void robotDead(Robot bot, DeathCause cause);
}
