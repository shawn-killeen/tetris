package tetris;

public class Grid {

	// Represente le terrain de jeu
	
	private int height, width;
	private Cell[][] cells;

	public Grid(int width,  int height) {
		this.height = height;
		this.width = width;
		this.cells = generateField();
	}
	
	public Cell[][] getCells(){
		return cells;
	}
	private Cell[][] generateField() {
		Cell[][] cells = new Cell[this.width][this.height];
		for ( int x = 0; x < this.width; x++ ) {
			for ( int y = this.height-1; y >= 0; y-- ) {
				cells[x][y] = new Cell(x, y, Cell.EMPTY);
			}
		}
		return cells;
	}

	public String printGrid() {


		String game = "";
		for ( int y = this.height-1; y >= 0; y-- ) {
			for ( int x = 0; x < this.width; x++ ) {

				game += cells[x][y].getState();
			}
			game += "<br/>";
		}

		game = "<html>" + game + "</html>";
		return game;
	}
	
	public Cell moveCell(Cell cell, int deltaX, int deltaY) {
		Cell result = cell;
		int newX = cell.getX()+deltaX, newY = cell.getY()+deltaY;
		if(newX >= 0 && newX <= this.width-1) {
			if(newY >= 0 && newY <= this.height-1) {
				Cell newCell = this.getCells()[newX][newY];
				if(newCell.getState() == Cell.EMPTY) {
					cell.setState( Cell.EMPTY );
					newCell.setState( Cell.FULL );
					result = newCell;
				}
			}
		}
		return result;

	}

}
