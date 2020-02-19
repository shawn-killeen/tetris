package tetris;


public class Cell {

	// Represente une unité dans le jeu
	
	public static final char EMPTY = 11036;
	public static final char ACTIVE = 9641;
	public static final char FULL = 11035;
	private char state;
	private Coord coord;

	public Cell(int x, int y, char state) {
		this.state = state;
		coord = new Coord(x, y);
	}

	public void setState(char state) {
		this.state = state;
	}
	
	public char getState() {

		return this.state;
	}
	
	public int getX() {
		return this.coord.getX();
	}
	
	public int getY() {
		return this.coord.getY();
	}
}
