package andybot;

public class MazeException extends RuntimeException {

	public MazeException(String msg) {
		super(msg);
	}
	
	public MazeException(String format, Object ... args) {
		super ( String.format(format, args));
	}

}
