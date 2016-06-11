package andybot.view;

import java.util.Arrays;
import java.util.List;

import andybot.Coord;
import andybot.IMaze;
import andybot.IMazeListener;
import andybot.IRobot;

public class ProxyMaze implements IMaze {
	private IMaze target;
	
	public ProxyMaze ( IMaze maze) {
		target = maze;
	}

	public String getMazeName() {
		return target.getMazeName();
	}

	public Coord getExit() {
		return target.getExit();
	}

	public int getRowSize() {
		return target.getRowSize();
	}

	public int getColSize() {
		return target.getColSize();
	}

	public List<IRobot> getRobots() {
		return Arrays.asList();
	}

	public void addMazeListener(IMazeListener listener) {
		// target.addMazeListener(listener);
	}

	public void removeMazeListener(IMazeListener listener) {
		// target.removeMazeListener(listener);
	}
	
	
}
