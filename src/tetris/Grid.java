package tetris;

public class Grid {

	// Represente le terrain de jeu

	private int height, width;
	private Cell[][] cells;

	public Grid( int width, int height ) {
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
				cells[x][y] = new Cell( x, y, Cell.EMPTY );
			}
		}
		return cells;
	}

	public String printGrid() {

		String game = "";
		for ( int y = this.height - 1; y >= 0; y-- ) {
			for ( int x = 0; x < this.width; x++ ) {

				game += cells[x][y].getState();
			}
			game += "<br/>";
		}

		game = "<html>" + game + "</html>";
		return game;
	}

	public Coord[] findActiveCells() {
		Cell[][] cells = this.getCells();
		Coord[] activeCells = new Coord[0];
		for ( int x = 0; x < cells.length; x++ ) {
			for ( int y = 0; y < cells[x].length; y++ ) {
				if ( cells[x][y].getState() == Cell.ACTIVE ) {
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
		for ( Coord coord : coords ) {
			coord.setX( coord.getX() + deltaX );
			coord.setY( coord.getY() + deltaY );
			if ( !Coord.isInLimits( coord, this.width, this.height ) ) {
				valid = false;
			}
		}
		if ( valid ) {
			// CLEAR ACTIVE
			for ( Coord coord : activeCoords ) {
				this.getCells()[coord.getX()][coord.getY()].setState( Cell.EMPTY );

			}

			// NEW ACTIVE
			for ( int i = 0; i < coords.length; i++ ) {
				Coord coord = coords[i];

				if ( this.getCells()[coord.getX()][coord.getY()].getState() == Cell.EMPTY ) {
					this.getCells()[coord.getX()][coord.getY()].setState( Cell.ACTIVE );
				}
			}
		}
	}

	public void rotateCells( Coord[] coords ) {
		coords = coords.clone();
		int width = Coord.calculateWidth( coords );
		int height = Coord.calculateHeight( coords );
		Coord  bottomLeft  = new Coord( 200, 200 );
		Coord[] relativeCoords = coords.clone();
		

		for ( Coord coord : coords ) {
			if ( coord.getX() < bottomLeft.getX() && coord.getY() < bottomLeft.getY() )
				bottomLeft = new Coord(coord.getX(), coord.getY());
		}
		
		// marche
		for( int i = 0; i < relativeCoords.length; i++) {
			relativeCoords[i].setX( coords[i].getX() - bottomLeft.getX());
			relativeCoords[i].setY( coords[i].getY() - bottomLeft.getY());
			System.out.println( relativeCoords[i].getY());
		}
		
		System.out.println( relativeCoords[0].getY());
		
		for ( int i = 0; i < coords.length; i++ ) {
			System.out.println( relativeCoords[i].getY());
			coords[i].setX(  bottomLeft.getX() + relativeCoords[i].getX() );
			coords[i].setY( bottomLeft.getY() + relativeCoords[i].getY() ); // la ligne affecte relativeCOords
			System.out.println( relativeCoords[i].getY());
			System.out.println( i + " bottomleft: " + bottomLeft.getX() + " " + bottomLeft.getY() );
			System.out.println( i + " relativeCoords: " + relativeCoords[i].getX() + " " + relativeCoords[i].getY() );
			System.out.println( i + " new coord: " + coords[i].getX() + " " + coords[i].getY() );
		}
		
		
		moveCells( coords, 0, 0 );
	}

}
