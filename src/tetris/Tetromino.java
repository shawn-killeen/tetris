package tetris;

import java.awt.Color;

public final class Tetromino {

	private final static Coord[] TETROMINO_T_COORDS = { new Coord( 0, 0 ), new Coord( 1, 0 ), new Coord( 2, 0 ),
			new Coord( 1, 1 ) };
	public final static Tetromino TETROMINO_T = new Tetromino( TETROMINO_T_COORDS, new Color( 173, 77, 156 ) ); // T-Block

	private final static Coord[] TETROMINO_O_COORDS = { new Coord( 0, 0 ), new Coord( 0, 1 ), new Coord( 1, 0 ),
			new Coord( 1, 1 ) };
	public final static Tetromino TETROMINO_O = new Tetromino( TETROMINO_O_COORDS, new Color( 247, 211, 8 ) ); // O-Block

	private final static Coord[] TETROMINO_S_COORDS = { new Coord( 0, 0 ), new Coord( 1, 0 ), new Coord( 1, 1 ),
			new Coord( 2, 1 ) };
	public final static Tetromino TETROMINO_S = new Tetromino( TETROMINO_S_COORDS, new Color( 66, 182, 66 ) );// S-Block

	private final static Coord[] TETROMINO_Z_COORDS = { new Coord( 0, 1 ), new Coord( 1, 1 ), new Coord( 1, 0 ),
			new Coord( 2, 0 ) };
	public final static Tetromino TETROMINO_Z = new Tetromino( TETROMINO_Z_COORDS, new Color( 239, 32, 41 ) ); // Z-Block

	private final static Coord[] TETROMINO_L_COORDS = { new Coord( 0, 0 ), new Coord( 1, 0 ), new Coord( 2, 0 ),
			new Coord( 2, 1 ) };
	public final static Tetromino TETROMINO_L = new Tetromino( TETROMINO_L_COORDS, new Color( 240, 160, 0 ) ); // L-Block

	private final static Coord[] TETROMINO_J_COORDS = { new Coord( 0, 1 ), new Coord( 0, 0 ), new Coord( 1, 0 ),
			new Coord( 2, 0 ) };
	public final static Tetromino TETROMINO_J = new Tetromino( TETROMINO_J_COORDS, new Color( 90, 101, 173 ) ); // J-Block

	private final static Coord[] TETROMINO_I_COORDS = { new Coord( 0, 0 ), new Coord( 1, 0 ), new Coord( 2, 0 ),
			new Coord( 3, 0 ) };
	public final static Tetromino TETROMINO_I = new Tetromino( TETROMINO_I_COORDS, new Color( 239,121, 33 ) );// I-Block

	public final static Tetromino[] TETROMINOES = { TETROMINO_T, TETROMINO_O, TETROMINO_S, TETROMINO_Z, TETROMINO_L, TETROMINO_J, TETROMINO_I };

	private Coord[] coords;
	private Color color;

	public Tetromino( Coord[] coords, Color color ) {
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
	public static Tetromino randomTetromino() {
		return TETROMINOES[(int) ( Math.random() * TETROMINOES.length )];
	}

}
