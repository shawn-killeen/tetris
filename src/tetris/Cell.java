package tetris;

import java.awt.*;

import javax.swing.*;

public class Cell extends JPanel {

	// Represents a cell in a grid

	public static enum STATE {
		EMPTY, ACTIVE, FULL;
	}

	public static final Color COLOR_EMPTY = Color.LIGHT_GRAY;
	public static final Color COLOR_FULL = Color.BLACK;
	public Color fullColor;
	private final Coord COORDINATES;

	private STATE state;

	public Cell( int x, int y) {
		this.fullColor = COLOR_FULL;
		this.state = STATE.EMPTY;
		setBackground( COLOR_EMPTY );
		setPreferredSize( new Dimension( 20, 20 ) );
		COORDINATES = new Coord( x, y );

	}
	
	
	public void setColorWhenFull(Color color) {
		fullColor = color;
	}
	
	public Color getColorWhenFull() {
		return fullColor;
	}
	
	public void setState( STATE state ) {
		this.state = state;
	}
	
	public Color getCorrectColor() {
		Color color = COLOR_EMPTY;
		
		if(state != STATE.EMPTY) {
			color = fullColor;
		}
		
		return color;
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
