package tetris;

public final class Point {

	// CONSTANTS
	private final int x, y;

	// CONSTRUCTOR
	public Point( int x, int y ) {
		this.x = x;
		this.y = y;
	}

	// RETURNS X
	public int getX() {
		return this.x;
	}

	// RETURNS Y
	public int getY() {
		return this.y;
	}

	// RETURNS WIDTH OF AN ARRAY OF COORDINATES
	public static int calculateWidth( Point[] coords ) {
		int width = 0;
		for ( int i = 0; i < coords.length; i++ ) {
			if ( coords[i].getX() + 1 > width )
				width = coords[i].getX() + 1;
		}
		return width;
	}

	// RETURNS HEIGHT OF AN ARRAY OF COORDINATES
	public static int calculateHeight( Point[] coords ) {
		int height = 0;
		for ( int i = 0; i < coords.length; i++ ) {
			if ( coords[i].getY() + 1 > height )
				height = coords[i].getY() + 1;
		}
		return height;
	}

	// CHECKS IF COORDINATES ARE WITHIN THE BOARD
	public static boolean isInLimits( Point point, int width, int height ) {
		boolean isInLimits = true;
		if ( point.getX() < 0 || point.getX() > width - 1 ) {
			isInLimits = false;
		}
		if ( point.getY() < 0 || point.getY() > height - 1 ) {
			isInLimits = false;
		}
		return isInLimits;
	}
}
