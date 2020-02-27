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
	public static final int WIDTH = 10, HEIGHT = 24;
	private Cell[][] cells;
	private  STATE[][] states = new STATE[WIDTH][HEIGHT];

	// CONTRUCTOR
	public Grid( Game game ) {
		myGame = game;
		cells = generateField();
	}

	// RETURNS CELLS
	public Cell[][] getCells() {
		return cells;
	}

	// CREATES GRID
	private Cell[][] generateField() {
		Cell[][] cells = new Cell[WIDTH][HEIGHT];
		for ( int x = 0; x < WIDTH; x++ ) {
			for ( int y = HEIGHT - 1; y >= 0; y-- ) {
				cells[x][y] = new Cell( x, y );
			}
		}
		return cells;
	}

	// RETURNS ALL ACTIVE CELLS ON FIELD
	public ArrayList<Coord> findActiveCells() {

		// VARIABLES
		Cell[][] cells = this.getCells();
		ArrayList<Coord> activeCells = new ArrayList<Coord>();

		// FOR EACH CELL
		for ( int x = 0; x < cells.length; x++ ) {
			for ( int y = 0; y < cells[x].length; y++ ) {

				// CHECK IF ACTIVE
				if ( cells[x][y].getState() == Cell.STATE.ACTIVE ) {
					// ADD CELL
					activeCells.add( new Coord( x, y ) );
				}
			}
		}
		return activeCells;
	}

	public void spawnShape() {
		if ( findActiveCells().size() == 0 ) {

			// VARIABLES
			Shape shape = Shape.randomShape();
			int height = Coord.calculateHeight( shape.getCoords() );
			int width = Coord.calculateWidth( shape.getCoords() );
			int middle = ( WIDTH - width ) / 2;

			// PLACES CELLS ON GRID
			for ( int i = 0; i < shape.getCoords().length; i++ ) {
				int cellX = middle + shape.getCoords()[i].getX();
				int cellY = ( Grid.HEIGHT ) - ( height - shape.getCoords()[i].getY() );
				Cell temp = getCells()[cellX][cellY];
				temp.setColorWhenFull( shape.getColor() );
				temp.setState( Cell.STATE.ACTIVE );
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
					Cell cellUnder = getCells()[activeCell.getX()][activeCell.getY() - 1];
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
				for ( int i = 0; i < activeCells.size(); i++ ) {
					Cell cell = getCells()[activeCells.get( i ).getX()][activeCells.get( i ).getY()];
					cell.setState( Cell.STATE.FULL );

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
			} else if ( this.getCells()[coords.get( i ).getX()][coords.get( i ).getY()]
					.getState() == Cell.STATE.FULL ) {
				valid = false;
			}
		}
		// CHECK NEW POSITION WAS OCCUPIED
		if ( valid ) {
			Color goodColor = Cell.COLOR_FULL;
			// CLEAR ACTIVE
			for ( Coord coord : activeCoords ) {
				Cell temp = getCells()[coord.getX()][coord.getY()];
				temp.setState( Cell.STATE.EMPTY );
				goodColor = temp.getColorWhenFull();

			}

			// NEW ACTIVE
			for ( int i = 0; i < coords.size(); i++ ) {
				Coord coord = coords.get( i );

				if ( getCells()[coord.getX()][coord.getY()].getState() == Cell.STATE.EMPTY ) {
					Cell temp = getCells()[coord.getX()][coord.getY()];
					temp.setColorWhenFull( goodColor );
					temp.setState( Cell.STATE.ACTIVE );
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
				if ( this.getCells()[x][y].getState() != Cell.STATE.FULL ) {
					isLineFull = false;
				}
			}
			// CLEARS THE FULL LINE
			if ( isLineFull ) {

				myGame.addScore( 1 );
				for ( int x = 0; x < WIDTH; x++ ) {
					this.getCells()[x][y].setState( Cell.STATE.EMPTY );
				}
				// DROPS THE LINES
				for ( int i = y; i < HEIGHT - 1; i++ ) {
					for ( int x = 0; x < WIDTH; x++ ) {
						this.getCells()[x][i].setState( this.getCells()[x][i + 1].getState() );
						this.getCells()[x][i + 1].setState( Cell.STATE.EMPTY );
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
