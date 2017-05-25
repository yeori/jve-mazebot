package andybot.model.path;

import andybot.model.Maze;
import andybot.model.Robot;

public interface IPathFinder {
	String getName();
	
	void onReady(Maze maze, Robot robot);

	void moveRobot(Maze maze, Robot robot);
}