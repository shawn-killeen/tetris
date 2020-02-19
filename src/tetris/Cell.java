package tetris;

public class Cell {

	// Represente une unité dans le jeu
	
	public static final char EMPTY = 11036;
	public static final char ACTIVE = 11035;
	public static final char FULL = 11035;
	private char state;
	private int x,y;

	public Cell(int x, int y, char state) {
		this.state = state;
		this.x = x;
		this.y = y;
	}

	public void setState(char state) {
		this.state = state;
	}
	
	public char getState() {

		return this.state;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
}
