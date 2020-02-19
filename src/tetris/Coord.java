package tetris;

public class Coord {

	private int x, y;

	public Coord( int x, int y ) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setX( int x ) {
		this.x = x;
	}

	public void setY( int y ) {
		this.y = y;
	}

	public static int calculateWidth( Coord[] coords ) {
		int width = 0;
		for ( int i = 0; i < coords.length; i++ ) {
			if ( coords[i].getX() + 1 > width )
				width = coords[i].getX() + 1;
		}
		return width;
	}

	public static int calculateHeight( Coord[] coords ) {
		int height = 0;
		for ( int i = 0; i < coords.length; i++ ) {
			if ( coords[i].getY() + 1 > height )
				height = coords[i].getY() + 1;
		}
		return height;
	}

	public static boolean isInLimits( Coord coord, int width, int height ) {
		boolean isInLimits = true;
		if ( coord.getX() < 0 || coord.getX() > width - 1 ) {
			isInLimits = false;
		}
		if ( coord.getY() < 0 || coord.getY() > height - 1 ) {
			isInLimits = false;
		}
		return isInLimits;
	}
}