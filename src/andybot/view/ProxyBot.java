package andybot.view;

import andybot.Coord;
import andybot.IRobot;
import andybot.IMaze.DIR;

class ProxyBot implements IRobot {
	private IRobot target ;
	
	boolean called = false;
	
	public ProxyBot ( IRobot r) {
		target = r;
	}

	public Coord getLocation() {
		return target.getLocation();
	}

	public int columnIndex() {
		return target.columnIndex();
	}

	public int rowIndex() {
		return target.rowIndex();
	}

	public String getName() {
		return target.getName();
	}

	public DIR getDirection() {
		return target.getDirection();
	}

	public void moveLeft() {
		checkDupMovement();
		target.moveLeft();
	}

	public void moveRight() {
		checkDupMovement();
		target.moveRight();
	}

	public void moveUp() {
		checkDupMovement();
		target.moveUp();
	}

	public void moveDown() {
		checkDupMovement();
		target.moveDown();
	}
	
	private void checkDupMovement ( ) {
		if( called) {
			throw new RuntimeException("Only one movement is allowed.");
		}
		called = true;
	}

	public boolean canMoveUp() {
		return target.canMoveUp();
	}

	public boolean canMoveRight() {
		return target.canMoveRight();
	}

	public boolean canMoveDown() {
		return target.canMoveDown();
	}

	public boolean canMoveLeft() {
		return target.canMoveLeft();
	}
	
	
}