package tetris;

import java.awt.Color;

public class Grid {

	// OBJECT REFERENCES
	private final Game GAME;

	//CONSTANTS
	public static final int  WIDTH = 10, HEIGHT = 24;
	private Cell[][] CELLS;

	// CONTRUCTOR
	public Grid( Game game) {
		GAME = game;
		CELLS = generateField();
	}

	// RETURNS CELLS
	public Cell[][] getCells() {
		return CELLS;
	}

	// CREATES GRID
	private Cell[][] generateField() {
		Cell[][] cells = new Cell[WIDTH][HEIGHT];
		for ( int x = 0; x < WIDTH; x++ ) {
			for ( int y = HEIGHT - 1; y >= 0; y-- ) {
				cells[x][y] = new Cell( x, y, Cell.STATE.EMPTY);
			}
		}
		return cells;
	}

	// RETURNS ALL ACTIVE CELLS ON FIELD
	public Coord[] findActiveCells() {
		
		// VARIABLES
		Cell[][] cells = this.getCells();
		Coord[] activeCells = new Coord[0];
		
		// FOR EACH CELL
		for ( int x = 0; x < cells.length; x++ ) {
			for ( int y = 0; y < cells[x].length; y++ ) {
				
				// CHECK IF ACTIVE
				if ( cells[x][y].getState() == Cell.STATE.ACTIVE ) {
					
					// MAKES ARRAY FIT THE NUMBER OF CELLS
					Coord[] temp = activeCells.clone();
					activeCells = new Coord[activeCells.length + 1];
					System.arraycopy( temp, 0, activeCells, 0, temp.length );
					// ADD CELL
					activeCells[activeCells.length - 1] = new Coord( x, y );
				}
			}
		}
		return activeCells;
	}
	
	public void spawnShape() {
		if (findActiveCells().length == 0 ) {

			// VARIABLES
			Shape shape = Shape.randomShape();
			int height = Coord.calculateHeight( shape.getCoords() );
			int width = Coord.calculateWidth( shape.getCoords() );
			int middle = ( WIDTH - width ) / 2;

			// PLACES CELLS ON GRID
			for ( int i = 0; i < shape.getCoords().length; i++ ) {
				Cell temp = getCells()[middle + shape.getCoords()[i].getX()][( Grid.HEIGHT )
						- ( height - shape.getCoords()[i].getY() )];

				temp.setState( Cell.STATE.ACTIVE);
			}
		}
	}
	
	public void gravity() {

		// MAKES SURE A SHAPE EXISTS
		Coord[] activeCells = findActiveCells();
		if ( activeCells.length > 0 ) {

			boolean isUnderneathClear = true;

			// CHECK IF AT LEAST ONE CELL IS TOUCHING FULL
			for ( int i = 0; i < activeCells.length; i++ ) {

				// LINES THAT ARENT THE LAST
				if ( activeCells[i].getY() != 0 ) {

					// IF TOUCHES
					Cell cellUnder = getCells()[activeCells[i].getX()][activeCells[i].getY() - 1];
					if ( cellUnder.getState() == Cell.STATE.FULL ) {
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
				for ( int i = 0; i < activeCells.length; i++ ) {
					Cell temp = getCells()[activeCells[i].getX()][activeCells[i].getY()];
					temp.setState( Cell.STATE.FULL);

				}
			}
		}
	}

	// MOVE A GROUP OF CELLS
	public void moveCells( Coord[] coords, int deltaX, int deltaY ) {
		
		// VARIABLES
		Coord[] activeCoords = this.findActiveCells();
		boolean valid = true;

		// CALCULATE NEW COORDS & CHECK VALID
		for ( int i = 0; i < coords.length; i++ ) {
			coords[i] = new Coord( coords[i].getX() + deltaX, coords[i].getY() + deltaY );
			// coord.setY( coord.getY() + deltaY );
			if ( !Coord.isInLimits( coords[i], WIDTH, HEIGHT ) ) {
				valid = false;
			} else if ( this.getCells()[coords[i].getX()][coords[i].getY()].getState() == Cell.STATE.FULL ) {
				valid = false;
			}
		}
		// CHECK NEW POSITION WAS OCCUPIED
		if ( valid ) {
			// CLEAR ACTIVE
			for ( Coord coord : activeCoords ) {
				Cell temp = getCells()[coord.getX()][coord.getY()];
				temp.setState( Cell.STATE.EMPTY);

			}

			// NEW ACTIVE
			for ( int i = 0; i < coords.length; i++ ) {
				Coord coord = coords[i];

				if ( getCells()[coord.getX()][coord.getY()].getState() == Cell.STATE.EMPTY ) {
					Cell temp = getCells()[coord.getX()][coord.getY()];
					temp.setState( Cell.STATE.ACTIVE );
				}
			}
		}
	}

	// ROTATE A GROUP OF CELLS
	public void rotateCells( Coord[] coords ) {
		
		// VARIABLES
		Coord bottomLeft = new Coord( 200, 200 );
		Coord[] relative;

		// BOTTOM-LEFT COORDINATES
		for ( int i = 0; i < coords.length; i++ ) {
			if ( coords[i].getX() < bottomLeft.getX() ) {
				bottomLeft = new Coord( coords[i].getX(), bottomLeft.getY() );
			}
			if ( coords[i].getY() < bottomLeft.getY() ) {
				bottomLeft = new Coord( bottomLeft.getX(), coords[i].getY() );
			}
		}

		// RELATIVE COORDINATES
		relative = new Coord[coords.length];
		for ( int i = 0; i < relative.length; i++ ) {
			relative[i] = new Coord( coords[i].getX() - bottomLeft.getX(), coords[i].getY() - bottomLeft.getY() );
		}

		// FIND WIDTH
		int shapeWidth = Coord.calculateWidth( relative );

		// ROTATE AND FLIP COORDINATES
		for ( int i = 0; i < coords.length; i++ ) {
			Coord[] temp = relative;
			relative[i] = new Coord( temp[i].getY(), ( shapeWidth - 1 ) - temp[i].getX() );
		}

		// GLOBAL COORDINATES
		for ( int i = 0; i < coords.length; i++ ) {
			coords[i] = new Coord( bottomLeft.getX() + relative[i].getX(), bottomLeft.getY() + relative[i].getY() );
		}

		moveCells( coords, 0, 0 );
	}

	// UPDATES LINES
	public void clearLines() {
		
		// CHECKS IF LINES ARE FULL
		for ( int y = 0; y < HEIGHT; y++ ) {
			boolean isLineFull = true;
			for ( int x = 0; x < WIDTH; x++ ) {
				if ( this.getCells()[x][y].getState() != Cell.STATE.FULL ) {
					isLineFull = false;
				}
			}
			// CLEARS THE FULL LINE
			if ( isLineFull ) {
				
				GAME.addScore( 1 );
				for ( int x = 0; x < WIDTH; x++ ) {
					this.getCells()[x][y].setState( Cell.STATE.EMPTY);
				}
				// DROPS THE LINES
				for ( int i = y; i < HEIGHT - 1; i++ ) {
					for ( int x = 0; x < WIDTH; x++ ) {
						this.getCells()[x][i].setState( this.getCells()[x][i + 1].getState() );
						this.getCells()[x][i + 1].setState( Cell.STATE.EMPTY);
					}
				}
			}
		}

	}

	// CHECK IF GAME IS OVER
	public boolean isDead() {
		boolean dead = false;
		for ( int i = 0; i < WIDTH; i++ ) {
			if ( this.getCells()[i][HEIGHT - 1].getState() == Cell.STATE.FULL ) {
				dead = true;
			}
		}
		return dead;
	}

}
