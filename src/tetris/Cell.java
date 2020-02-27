package tetris;

import java.awt.*;

import javax.swing.*;

public class Cell extends JPanel {

	// Represents a cell in a grid

	public static enum STATE {
		EMPTY, ACTIVE, FULL;
	}

	public static final Color COLOR_EMPTY = Color.LIGHT_GRAY;
	public static final Color COLOR_FULL = Color.BLUE;
	private final Coord COORDINATES;

	private STATE state;

	public Cell( int x, int y, STATE state ) {
		setState( state );
		setPreferredSize( new Dimension( 20, 20 ) );
		COORDINATES = new Coord( x, y );

	}

	public void setState( STATE state ) {
		this.state = state;
		switch ( state ) {
		case EMPTY:
			setBackground( COLOR_EMPTY );
			break;
		case FULL:
		case ACTIVE:
			setBackground( COLOR_FULL );
		}
	}

	public STATE getState() {

		return state;
	}

	public int getX() {
		return COORDINATES.getX();
	}

	public int getY() {
		return COORDINATES.getY();
	}
}
