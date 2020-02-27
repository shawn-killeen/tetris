package tetris;

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
	public ArrayList<Coord> findActiveCells() {

		// VARIABLES
		ArrayList<Coord> activeCoords = new ArrayList<Coord>();

		// FOR EACH CELL
		for ( int x = 0; x < states.length; x++ ) {
			for ( int y = 0; y < states[x].length; y++ ) {

				// CHECK IF ACTIVE
				if ( states[x][y] == STATE.ACTIVE ) {
					// ADD CELL
					activeCoords.add( new Coord( x, y ) );
				}
			}
		}
		return activeCoords;
	}

	public void spawnShape() {
		if ( findActiveCells().size() == 0 ) {

			// VARIABLES
			Tetromino tetromino = Tetromino.randomTetromino();
			int height = Coord.calculateHeight( tetromino.getCoords() );
			int width = Coord.calculateWidth( tetromino.getCoords() );
			int middle = ( WIDTH - width ) / 2;

			// PLACES CELLS ON GRID
			for ( int i = 0; i < tetromino.getCoords().length; i++ ) {
				int x = middle + tetromino.getCoords()[i].getX();
				int y = ( Grid.HEIGHT ) - ( height - tetromino.getCoords()[i].getY() );
				colors[x][y] = tetromino.getColor();
				states[x][y] = STATE.ACTIVE;
			}
		}
	}

	public void gravity() {

		// MAKES SURE A SHAPE EXISTS
		ArrayList<Coord> activeCells = findActiveCells();
		if ( activeCells.size() > 0 ) {

			boolean isUnderneathClear = true;

			// CHECK IF AT LEAST ONE CELL IS TOUCHING FULL
			for ( int i = 0; i < activeCells.size(); i++ ) {
				Coord activeCell = activeCells.get( i );
				// LINES THAT ARENT THE LAST
				if ( activeCell.getY() != 0 ) {

					// IF TOUCHES
					STATE cellUnder = states[activeCell.getX()][activeCell.getY() - 1];
					if ( cellUnder == STATE.FULL ) {
						isUnderneathClear = false;
					}

					// LAST LINE
				} else {
					isUnderneathClear = false;
				}
			}

			// LOWERS CELLS
			if ( isUnderneathClear ) {
				moveCells( activeCells, 0, -1 );
			}
			// GROUNDS CELLS
			else {
				for ( int i = 0; i < activeCells.size(); i++ ) {
					states[activeCells.get( i ).getX()][activeCells.get( i ).getY()] = STATE.FULL;
				}
			}
		}
	}

	// MOVE A GROUP OF CELLS
	public void moveCells( ArrayList<Coord> coords, int deltaX, int deltaY ) {

		// VARIABLES
		ArrayList<Coord> activeCoords = this.findActiveCells();
		boolean valid = true;

		// CALCULATE NEW COORDS & CHECK VALID
		for ( int i = 0; i < coords.size(); i++ ) {
			coords.set( i, new Coord( coords.get( i ).getX() + deltaX, coords.get( i ).getY() + deltaY ) );
			if ( !Coord.isInLimits( coords.get( i ), WIDTH, HEIGHT ) ) {
				valid = false;
			} else if ( states[coords.get( i ).getX()][coords.get( i ).getY()]
					 == STATE.FULL ) {
				valid = false;
			}
		}
		// CHECK NEW POSITION WAS OCCUPIED
		if ( valid ) {
			Color goodColor = Color.BLACK;
			// CLEAR ACTIVE
			for ( Coord coord : activeCoords ) {
				states[coord.getX()][coord.getY()]= STATE.EMPTY;
				goodColor = colors[coord.getX()][coord.getY()];
				
			}

			// NEW ACTIVE
			for ( int i = 0; i < coords.size(); i++ ) {
				Coord coord = coords.get( i );

				if ( states[coord.getX()][coord.getY()] == STATE.EMPTY ) {
					states[coord.getX()][coord.getY()]= STATE.ACTIVE ;
					colors[coord.getX()][coord.getY()] = goodColor;
				}
			}
		}
	}

	// ROTATE A GROUP OF CELLS
	public void rotateCells( ArrayList<Coord> coords ) {

		// VARIABLES
		Coord bottomLeft = new Coord( 200, 200 );
		Coord[] relative;

		// BOTTOM-LEFT COORDINATES
		for ( int i = 0; i < coords.size(); i++ ) {
			if ( coords.get( i ).getX() < bottomLeft.getX() ) {
				bottomLeft = new Coord( coords.get( i ).getX(), bottomLeft.getY() );
			}
			if ( coords.get( i ).getY() < bottomLeft.getY() ) {
				bottomLeft = new Coord( bottomLeft.getX(), coords.get( i ).getY() );
			}
		}

		// RELATIVE COORDINATES
		relative = new Coord[coords.size()];
		for ( int i = 0; i < relative.length; i++ ) {
			relative[i] = new Coord( coords.get( i ).getX() - bottomLeft.getX(),
					coords.get( i ).getY() - bottomLeft.getY() );
		}

		// FIND WIDTH
		int shapeWidth = Coord.calculateWidth( relative );

		// ROTATE AND FLIP COORDINATES
		for ( int i = 0; i < coords.size(); i++ ) {
			Coord[] temp = relative;
			relative[i] = new Coord( temp[i].getY(), ( shapeWidth - 1 ) - temp[i].getX() );
		}

		// GLOBAL COORDINATES
		for ( int i = 0; i < coords.size(); i++ ) {
			coords.set( i,
					new Coord( bottomLeft.getX() + relative[i].getX(), bottomLeft.getY() + relative[i].getY() ) );
		}

		moveCells( coords, 0, 0 );
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
