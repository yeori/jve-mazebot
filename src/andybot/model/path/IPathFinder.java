package andybot.model.path;

import andybot.IMaze;
import andybot.IRobot;

public interface IPathFinder {
	String getName();
	void moveRobot(IMaze maze, IRobot robot);
	void onStart(IMaze maze, IRobot robot);
}