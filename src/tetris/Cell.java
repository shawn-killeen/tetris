package tetris;

import java.awt.*;

import javax.swing.*;

public class Cell extends JPanel {

	// Represents a cell in a grid

	public static enum STATE{
		EMPTY, ACTIVE, FULL;
	}

	private static final Color COLOR_EMPTY = Color.LIGHT_GRAY;
	private Color colorFull = Color.BLACK;
	private final Coord COORDINATES;

	private STATE state;
	
	public Cell( int x, int y, STATE state, Color fullColor){
		setStateColor(state, fullColor);
		COORDINATES = new Coord( x, y );
		
	}
	
	public Cell( int x, int y, STATE state){
		setStateColor(state, colorFull);
		COORDINATES = new Coord( x, y );
		
	}

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(20, 20);
    }
	
	public void setStateColor(STATE state, Color color) {
		setState(state);
		setColor(color);
	}
	
	public void setColor(Color color) {
		this.colorFull = color;
		updateColor();
	}
	
	public Color getColor() {
		return colorFull;
	}
	
	public void setState(STATE state) {
		this.state = state;
		updateColor();
	}
	
	public void updateColor() {
		switch ( state ) {
		case EMPTY:
			setBackground(COLOR_EMPTY);
			break;
		case FULL:
			setBackground(colorFull);
			break;
		case ACTIVE:
			setBackground(colorFull);
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
