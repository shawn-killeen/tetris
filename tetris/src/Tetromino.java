import java.awt.Color;

public final class Tetromino {

	private final static Point[] TETROMINO_T_POINTS = { new Point( 0, 0 ), new Point( 1, 0 ), new Point( 2, 0 ),
			new Point( 1, 1 ) };
	public final static Tetromino TETROMINO_T = new Tetromino( TETROMINO_T_POINTS, new Color( 173, 77, 156 ) ); // T-Block

	private final static Point[] TETROMINO_O_POINTS = { new Point( 0, 0 ), new Point( 0, 1 ), new Point( 1, 0 ),
			new Point( 1, 1 ) };
	public final static Tetromino TETROMINO_O = new Tetromino( TETROMINO_O_POINTS, new Color( 247, 211, 8 ) ); // O-Block

	private final static Point[] TETROMINO_S_POINTS = { new Point( 0, 0 ), new Point( 1, 0 ), new Point( 1, 1 ),
			new Point( 2, 1 ) };
	public final static Tetromino TETROMINO_S = new Tetromino( TETROMINO_S_POINTS, new Color( 66, 182, 66 ) );// S-Block

	private final static Point[] TETROMINO_Z_POINTS = { new Point( 0, 1 ), new Point( 1, 1 ), new Point( 1, 0 ),
			new Point( 2, 0 ) };
	public final static Tetromino TETROMINO_Z = new Tetromino( TETROMINO_Z_POINTS, new Color( 239, 32, 41 ) ); // Z-Block

	private final static Point[] TETROMINO_L_POINTS = { new Point( 0, 0 ), new Point( 1, 0 ), new Point( 2, 0 ),
			new Point( 2, 1 ) };
	public final static Tetromino TETROMINO_L = new Tetromino( TETROMINO_L_POINTS, new Color( 240, 160, 0 ) ); // L-Block

	private final static Point[] TETROMINO_J_POINTS = { new Point( 0, 1 ), new Point( 0, 0 ), new Point( 1, 0 ),
			new Point( 2, 0 ) };
	public final static Tetromino TETROMINO_J = new Tetromino( TETROMINO_J_POINTS, new Color( 90, 101, 173 ) ); // J-Block

	private final static Point[] TETROMINO_I_POINTS = { new Point( 0, 0 ), new Point( 1, 0 ), new Point( 2, 0 ),
			new Point( 3, 0 ) };
	public final static Tetromino TETROMINO_I = new Tetromino( TETROMINO_I_POINTS, new Color( 239,121, 33 ) );// I-Block

	public final static Tetromino[] TETROMINOES = { TETROMINO_T, TETROMINO_O, TETROMINO_S, TETROMINO_Z, TETROMINO_L, TETROMINO_J, TETROMINO_I };

	private Point[] points;
	private Color color;

	public Tetromino( Point[] points, Color color ) {
		this.points = points;
		this.color = color;
	}

	public Point[] getPoints() {
		return points;
	}

	public Color getColor() {
		return color;
	}

	// RETURNS A RANDOM SHAPE
	public static Tetromino randomTetromino() {
		return TETROMINOES[(int) ( Math.random() * TETROMINOES.length )];
	}

}
