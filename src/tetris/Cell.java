package tetris;

import java.awt.*;

import javax.swing.*;

public class Cell extends JPanel {

	// Represents a cell in a grid

	public static enum STATE{
		EMPTY, ACTIVE, FULL;
	}

	private static final Color COLOR_EMPTY = Color.WHITE;
	private static final Color COLOR_ACTIVE = Color.DARK_GRAY;
	private static final Color COLOR_FULL = Color.BLACK;
	private final Coord COORDINATES;

	private STATE state;

	public Cell( int x, int y, STATE state){
		this.state = state;
		COORDINATES = new Coord( x, y );
		setBackground(COLOR_EMPTY);
	}

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(20, 20);
    }

	public void setState( STATE newState ) {
		state = newState;
		switch ( state ) {
		case EMPTY:
			setBackground(COLOR_EMPTY);
			break;
		case ACTIVE:
			setBackground(COLOR_ACTIVE);
			break;
		case FULL:
			setBackground(COLOR_FULL);
			break;
		default:
			setBackground(Color.BLUE);
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
