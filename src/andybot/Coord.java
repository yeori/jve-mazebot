package andybot;
/**
 * 좌표를 나타냄
 * @author chmin.seo
 *
 */
public class Coord {

	private int row;
	private int col;
	public Coord(int irow, int icol) {
		super();
		this.row = irow;
		this.col = icol;
	}
	
	public Coord(Coord loc) {
		this.row = loc.row;
		this.col = loc.col;
	}

	public int rowIndex() {
		return row;
	}
	
	public int colIndex() {
		return col;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + row;
		result = prime * result + col;
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
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(r: " + row + ", c: " + col + ")";
	}
	
	
	
	
}
