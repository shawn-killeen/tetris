package tetris;

public class Grid {

	// Represente le terrain de jeu
	private final Game GAME;

	private int height, width;
	private Cell[][] cells;

	public Grid( Game game, int width, int height ) {
		GAME = game;
		this.height = height;
		this.width = width;
		this.cells = generateField();
	}

	public Cell[][] getCells() {
		return cells;
	}

	private Cell[][] generateField() {
		Cell[][] cells = new Cell[this.width][this.height];
		for ( int x = 0; x < this.width; x++ ) {
			for ( int y = this.height - 1; y >= 0; y-- ) {
				cells[x][y] = new Cell( x, y, Cell.STATE.EMPTY );
			}
		}
		return cells;
	}

	public String printGrid() {

		String game = "";
		for ( int y = this.height - 1; y >= 0; y-- ) {
			for ( int x = 0; x < this.width; x++ ) {

				game += cells[x][y].getStateChar();
			}
			game += "<br/>";
		}

		game = String.format( "<html><h1>Score: %d</h1><h2>%s</h2></html>", GAME.getScore(), game );
		return game;
	}

	public Coord[] findActiveCells() {
		Cell[][] cells = this.getCells();
		Coord[] activeCells = new Coord[0];
		for ( int x = 0; x < cells.length; x++ ) {
			for ( int y = 0; y < cells[x].length; y++ ) {
				if ( cells[x][y].getState() == Cell.STATE.ACTIVE ) {
					Coord[] temp = activeCells.clone();
					activeCells = new Coord[activeCells.length + 1];
					System.arraycopy( temp, 0, activeCells, 0, temp.length );
					activeCells[activeCells.length - 1] = new Coord( x, y );
				}
			}
		}
		return activeCells;
	}

	public void moveCells( Coord[] coords, int deltaX, int deltaY ) {
		Coord[] activeCoords = this.findActiveCells();
		boolean valid = true;

		// CALCULATE NEW COORDS & CHECK VALID
		for ( int i = 0; i < coords.length; i++ ) {
			coords[i] = new Coord( coords[i].getX() + deltaX, coords[i].getY() + deltaY );
			// coord.setY( coord.getY() + deltaY );
			if ( !Coord.isInLimits( coords[i], this.width, this.height ) ) {
				valid = false;
			} else if ( this.getCells()[coords[i].getX()][coords[i].getY()].getState() == Cell.STATE.FULL ) {
				valid = false;
			}
		}
		if ( valid ) {
			// CLEAR ACTIVE
			for ( Coord coord : activeCoords ) {
				this.getCells()[coord.getX()][coord.getY()].setState( Cell.STATE.EMPTY );

			}

			// NEW ACTIVE
			for ( int i = 0; i < coords.length; i++ ) {
				Coord coord = coords[i];

				if ( this.getCells()[coord.getX()][coord.getY()].getState() == Cell.STATE.EMPTY ) {
					this.getCells()[coord.getX()][coord.getY()].setState( Cell.STATE.ACTIVE );
				}
			}
		}
	}

	public void rotateCells( Coord[] coords ) {
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

	public void clearLines() {
		// CLEAR LINES
		for ( int y = 0; y < height; y++ ) {
			boolean isLineFull = true;
			for ( int x = 0; x < width; x++ ) {
				if ( this.getCells()[x][y].getState() == Cell.STATE.EMPTY ) {
					isLineFull = false;
				}
			}
			if ( isLineFull ) {
				GAME.addScore( 1 );
				for ( int x = 0; x < width; x++ ) {
					this.getCells()[x][y].setState( Cell.STATE.EMPTY );
				}
				for ( int i = y; i < height - 1; i++ ) {
					for ( int x = 0; x < width; x++ ) {
						this.getCells()[x][i].setState( this.getCells()[x][i + 1].getState() );
						this.getCells()[x][i + 1].setState( Cell.STATE.EMPTY );
					}
				}
			}
		}

	}

	public boolean isDead() {
		boolean dead = false;
		for ( int i = 0; i < width; i++ ) {
			if ( this.getCells()[i][height - 1].getState() == Cell.STATE.FULL ) {
				dead = true;
				System.out.println( "Game Over" );
			}
		}
		return dead;
	}

}
