import java.awt.Color;
import java.util.ArrayList;

public class Grid {

	// OBJECT REFERENCES
	private Game myGame;
	
	public static enum STATE {
		EMPTY, ACTIVE, FULL;
	}

	// CONSTANTS
	public static final Color COLOR_EMPTY = Color.DARK_GRAY;
	public static final Color COLOR_FULL = Color.BLACK;
	public static final int WIDTH = 10, HEIGHT = 24;
	private  STATE[][] states = new STATE[WIDTH][HEIGHT];
	private  Color[][] colors = new Color[WIDTH][HEIGHT];

	// CONTRUCTOR
	public Grid( Game game ) {
		myGame = game;
		generateField();
	}


	// CREATES GRID
	private void generateField() {
		for ( int x = 0; x < states.length; x++ ) {
			for ( int y = states[x].length - 1; y >= 0; y-- ) {
				states[x][y] = STATE.EMPTY;
				colors[x][y] = COLOR_FULL;
			}
		}
	}
	
	public STATE[][] getStates(){
		return states;
	}
	
	public Color[][] getColors(){
		return colors;
	}

	// RETURNS ALL ACTIVE CELLS ON FIELD
	public ArrayList<Point> findActivePoints() {

		// VARIABLES
		ArrayList<Point> activePoints = new ArrayList<Point>();

		// FOR EACH CELL
		for ( int x = 0; x < states.length; x++ ) {
			for ( int y = 0; y < states[x].length; y++ ) {

				// CHECK IF ACTIVE
				if ( states[x][y] == STATE.ACTIVE ) {
					// ADD CELL
					activePoints.add( new Point( x, y ) );
				}
			}
		}
		return activePoints;
	}

	public void spawnShape() {
		if ( findActivePoints().size() == 0 ) {

			// VARIABLES
			Tetromino tetromino = Tetromino.randomTetromino();
			int height = Point.calculateHeight( tetromino.getPoints() );
			int width = Point.calculateWidth( tetromino.getPoints() );
			int middle = ( WIDTH - width ) / 2;

			// PLACES CELLS ON GRID
			for ( int i = 0; i < tetromino.getPoints().length; i++ ) {
				int x = middle + tetromino.getPoints()[i].getX();
				int y = ( Grid.HEIGHT ) - ( height - tetromino.getPoints()[i].getY() );
				colors[x][y] = tetromino.getColor();
				states[x][y] = STATE.ACTIVE;
			}
		}
	}

	public void gravity() {

		// MAKES SURE A SHAPE EXISTS
		ArrayList<Point> activePoints = findActivePoints();
		if ( activePoints.size() > 0 ) {

			boolean isUnderneathClear = true;

			// CHECK IF AT LEAST ONE CELL IS TOUCHING FULL
			for ( int i = 0; i < activePoints.size(); i++ ) {
				Point activePoint = activePoints.get( i );
				// LINES THAT ARENT THE LAST
				if ( activePoint.getY() != 0 ) {

					// IF TOUCHES
					STATE pointUnder = states[activePoint.getX()][activePoint.getY() - 1];
					if ( pointUnder == STATE.FULL ) {
						isUnderneathClear = false;
					}

					// LAST LINE
				} else {
					isUnderneathClear = false;
				}
			}

			// LOWERS CELLS
			if ( isUnderneathClear ) {
				moveCells( activePoints, 0, -1 );
			}
			// GROUNDS CELLS
			else {
				for ( int i = 0; i < activePoints.size(); i++ ) {
					states[activePoints.get( i ).getX()][activePoints.get( i ).getY()] = STATE.FULL;
				}
			}
		}
	}

	// MOVE A GROUP OF CELLS
	public void moveCells( ArrayList<Point> points, int deltaX, int deltaY ) {

		// VARIABLES
		ArrayList<Point> activePoints = this.findActivePoints();
		boolean valid = true;

		// CALCULATE NEW COORDS & CHECK VALID
		for ( int i = 0; i < points.size(); i++ ) {
			points.set( i, new Point( points.get( i ).getX() + deltaX, points.get( i ).getY() + deltaY ) );
			if ( !Point.isInLimits( points.get( i ), WIDTH, HEIGHT ) ) {
				valid = false;
			} else if ( states[points.get( i ).getX()][points.get( i ).getY()]
					 == STATE.FULL ) {
				valid = false;
			}
		}
		// CHECK NEW POSITION WAS OCCUPIED
		if ( valid ) {
			Color goodColor = Color.BLACK;
			// CLEAR ACTIVE
			for ( Point point : activePoints ) {
				states[point.getX()][point.getY()]= STATE.EMPTY;
				goodColor = colors[point.getX()][point.getY()];
				
			}

			// NEW ACTIVE
			for ( int i = 0; i < points.size(); i++ ) {
				Point point = points.get( i );

				if ( states[point.getX()][point.getY()] == STATE.EMPTY ) {
					states[point.getX()][point.getY()]= STATE.ACTIVE ;
					colors[point.getX()][point.getY()] = goodColor;
				}
			}
		}
	}

	// ROTATE A GROUP OF CELLS
	public void rotateCells( ArrayList<Point> points ) {

		// VARIABLES
		Point bottomLeft = new Point( 200, 200 );
		Point[] relative;

		// BOTTOM-LEFT COORDINATES
		for ( int i = 0; i < points.size(); i++ ) {
			if ( points.get( i ).getX() < bottomLeft.getX() ) {
				bottomLeft = new Point( points.get( i ).getX(), bottomLeft.getY() );
			}
			if ( points.get( i ).getY() < bottomLeft.getY() ) {
				bottomLeft = new Point( bottomLeft.getX(), points.get( i ).getY() );
			}
		}

		// RELATIVE COORDINATES
		relative = new Point[points.size()];
		for ( int i = 0; i < relative.length; i++ ) {
			relative[i] = new Point( points.get( i ).getX() - bottomLeft.getX(),
					points.get( i ).getY() - bottomLeft.getY() );
		}

		// FIND WIDTH
		int shapeWidth = Point.calculateWidth( relative );

		// ROTATE AND FLIP COORDINATES
		for ( int i = 0; i < points.size(); i++ ) {
			Point[] temp = relative;
			relative[i] = new Point( temp[i].getY(), ( shapeWidth - 1 ) - temp[i].getX() );
		}

		// GLOBAL COORDINATES
		for ( int i = 0; i < points.size(); i++ ) {
			points.set( i,
					new Point( bottomLeft.getX() + relative[i].getX(), bottomLeft.getY() + relative[i].getY() ) );
		}

		moveCells( points, 0, 0 );
	}

	// UPDATES LINES
	public void clearLines() {

		// CHECKS IF LINES ARE FULL
		for ( int y = 0; y < HEIGHT; y++ ) {
			boolean isLineFull = true;
			for ( int x = 0; x < WIDTH; x++ ) {
				if ( states[x][y] !=STATE.FULL ) {
					isLineFull = false;
				}
			}
			// CLEARS THE FULL LINE
			if ( isLineFull ) {

				myGame.addScore( 1 );
				for ( int x = 0; x < WIDTH; x++ ) {
					states[x][y] = STATE.EMPTY ;
				}
				// DROPS THE LINES
				for ( int i = y; i < HEIGHT - 1; i++ ) {
					for ( int x = 0; x < WIDTH; x++ ) {
						states[x][i] = states[x][i + 1];
						states[x][i + 1] = STATE.EMPTY ;
					}
				}
			}
		}

	}

	// CHECK IF GAME IS OVER
	public boolean isDead() {
		boolean dead = false;
		for ( int i = 0; i < WIDTH; i++ ) {
			if ( states[i][HEIGHT - 1] == STATE.FULL ) {
				dead = true;
			}
		}
		return dead;
	}

}
