package tetris;

public class Cell {

	// Represents a cell in a grid

	public static enum STATE {
		EMPTY, ACTIVE, FULL;
	}

	private static final char CHAR_EMPTY = 11036;
	private static final char CHAR_ACTIVE = 9641;
	private static final char CHAR_FULL = 11035;
	private final Coord COORDINATES;

	private STATE state;

	public Cell( int x, int y, STATE state ) {
		this.state = state;
		COORDINATES = new Coord( x, y );
	}

	public void setState( STATE newState ) {
		state = newState;
	}

	public STATE getState() {

		return state;
	}

	public char getStateChar() {
		char stateChar;
		switch ( state ) {
		case EMPTY:
			stateChar = CHAR_EMPTY;
			break;
		case ACTIVE:
			stateChar = CHAR_ACTIVE;
			break;
		case FULL:
			stateChar = CHAR_FULL;
			break;
		default:
			stateChar = CHAR_EMPTY;
		}
		return stateChar;
	}

	public int getX() {
		return COORDINATES.getX();
	}

	public int getY() {
		return COORDINATES.getY();
	}
}
