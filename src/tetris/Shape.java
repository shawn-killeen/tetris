package tetris;

import java.awt.Color;

public final class Shape {

	private final static Coord[] SHAPE_T_COORDS = { new Coord( 0, 0 ), new Coord( 1, 0 ), new Coord( 2, 0 ),
			new Coord( 1, 1 ) };
	public final static Shape SHAPE_T = new Shape( SHAPE_T_COORDS, Color.BLUE ); // T-Block

	private final static Coord[] SHAPE_O_COORDS = { new Coord( 0, 0 ), new Coord( 0, 1 ), new Coord( 1, 0 ),
			new Coord( 1, 1 ) };
	public final static Shape SHAPE_O = new Shape( SHAPE_O_COORDS, Color.YELLOW ); // O-Block

	private final static Coord[] SHAPE_S_COORDS ={ new Coord( 0, 0 ), new Coord( 1, 0 ), new Coord( 1, 1 ),
			new Coord( 2, 1 ) };
	public final static Shape SHAPE_S =  new Shape( SHAPE_S_COORDS, Color.GREEN );// S-Block
	
	private final static Coord[] SHAPE_Z_COORDS = { new Coord( 0, 1 ), new Coord( 1, 1 ), new Coord( 1, 0 ),
			new Coord( 2, 0 ) };
	public final static Shape SHAPE_Z = new Shape( SHAPE_Z_COORDS, Color.MAGENTA ); // Z-Block
	
	private final static Coord[] SHAPE_L_COORDS = { new Coord( 0, 0 ), new Coord( 1, 0 ), new Coord( 2, 0 ),
			new Coord( 2, 1 ) };
	public final static Shape SHAPE_L = new Shape( SHAPE_L_COORDS, Color.RED ); // L-Block
	
	private final static Coord[] SHAPE_J_COORDS = { new Coord( 0, 1 ), new Coord( 0, 0 ), new Coord( 1, 0 ),
			new Coord( 2, 0 ) };
	public final static Shape SHAPE_J = new Shape( SHAPE_J_COORDS, Color.ORANGE ); // J-Block
	
	private final static Coord[] SHAPE_I_COORDS = { new Coord( 0, 0 ), new Coord( 1, 0 ), new Coord( 2, 0 ),
			new Coord( 3, 0 ) };
	public final static Shape SHAPE_I =  new Shape( SHAPE_I_COORDS, Color.PINK );// I-Block

	public final static Shape[] SHAPES = { SHAPE_T, SHAPE_O, SHAPE_S, SHAPE_Z, SHAPE_L, SHAPE_J, SHAPE_I };

	private Coord[] coords;
	private Color color;

	public Shape( Coord[] coords, Color color ) {
		this.coords = coords;
		this.color = color;
	}

	public Coord[] getCoords() {
		return coords;
	}

	public Color getColor() {
		return color;
	}

	// RETURNS A RANDOM SHAPE
	public static Shape randomShape() {
		return SHAPES[(int) ( Math.random() * SHAPES.length )];
	}

}
