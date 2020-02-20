package tetris;


public final class Coord {
	
	// SHAPE CONSTANTS
	public final static Coord[] SHAPE_T = { new Coord( 0, 0 ), new Coord( 1, 0 ), new Coord( 2, 0 ),
			new Coord( 1, 1 ) }; // T-Block
	public final static Coord[] SHAPE_O = { new Coord( 0, 0 ), new Coord( 0, 1 ), new Coord( 1, 0 ),
			new Coord( 1, 1 ) }; // O-Block
	public final static Coord[] SHAPE_S = { new Coord( 0, 0 ), new Coord( 1, 0 ), new Coord( 1, 1 ),
			new Coord( 2, 1 ) }; // S-Block
	public final static Coord[] SHAPE_Z = { new Coord( 0, 1 ), new Coord( 1, 1 ), new Coord( 1, 0 ),
			new Coord( 2, 0 ) }; // Z-Block
	public final static Coord[] SHAPE_L = { new Coord( 0, 0 ), new Coord( 1, 0 ), new Coord( 2, 0 ),
			new Coord( 2, 1 ) }; // L-Block
	public final static Coord[] SHAPE_J = { new Coord( 0, 1 ), new Coord( 0, 0 ), new Coord( 1, 0 ),
			new Coord( 2, 0 ) }; // J-Block
	public final static Coord[] SHAPE_I = { new Coord( 0, 0 ), new Coord( 1, 0 ), new Coord( 2, 0 ),
			new Coord( 3, 0 ) }; // I-Block

	public final static Coord[][] SHAPES = { SHAPE_T, SHAPE_O, SHAPE_S, SHAPE_Z, SHAPE_L, SHAPE_J, SHAPE_I };

	// CONSTANTS
	private final int x, y;
	
	// CONSTRUCTOR
	public Coord( int x, int y ) {
		this.x = x;
		this.y = y;
	}
	
	// RETURNS A RANDOM SHAPE
	public static Coord[] randomShape() {
		return SHAPES[(int) ( Math.random() * SHAPES.length )];
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
	public static int calculateWidth( Coord[] coords ) {
		int width = 0;
		for ( int i = 0; i < coords.length; i++ ) {
			if ( coords[i].getX() + 1 > width )
				width = coords[i].getX() + 1;
		}
		return width;
	}

	// RETURNS HEIGHT OF AN ARRAY OF COORDINATES
	public static int calculateHeight( Coord[] coords ) {
		int height = 0;
		for ( int i = 0; i < coords.length; i++ ) {
			if ( coords[i].getY() + 1 > height )
				height = coords[i].getY() + 1;
		}
		return height;
	}

	// CHECKS IF COORDINATES ARE WITHIN THE BOARD
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
