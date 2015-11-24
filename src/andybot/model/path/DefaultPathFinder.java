package andybot.model.path;

import andybot.model.Maze;
import andybot.model.Robot;

public class DefaultPathFinder extends AbstractPathFinder {

	public DefaultPathFinder(Maze maze, Robot robot) {
		super(maze, robot);
	}

	@Override
	protected void moveBot(Maze maze, Robot robot) {
		robot.moveDown();
	}

}
