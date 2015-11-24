package andybot.model;
/**
 * 좌표를 나타냄
 * @author chmin.seo
 *
 */
public class Coord {

	private int x;
	private int y;
	public Coord(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Coord(Coord loc) {
		this.x = loc.x;
		this.y = loc.y;
	}

	public int x() {
		return x;
	}
	public void x(int nx) {
		x = nx;
	}
	
	public int y() {
		return y;
	}
	public void y(int ny) {
		y = ny;
	}
	
	public int rowIndex() {
		return y;
	}
	
	public int colIndex() {
		return x;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coord other = (Coord) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	
}
