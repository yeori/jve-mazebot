package andybot.model.path;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import andybot.model.Coord;
import andybot.model.Maze;
import andybot.model.Robot;

public abstract class AbstractPathFinder implements ActionListener {

	private Robot robot;
	private Maze maze;
	private volatile boolean finished;
	
	protected AbstractPathFinder(Maze maze, Robot robot) {
		this.robot = robot;
		this.maze = maze;
	}
	@Override
	final public void actionPerformed(ActionEvent e) {
		Coord end = maze.getEndCoord();
		Coord loc = robot.getLocation();
		if ( loc.x() != end.x() || loc.y() != end.y() ) {
			moveBot(maze, robot);
			
		} else{
			this.finished = true;			
		}
	}

	protected abstract void moveBot(Maze maze, Robot robot);
	
	final public boolean isFinished() {
		return finished;
	}
	
}
