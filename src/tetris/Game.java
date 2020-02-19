package tetris;

import javax.swing.*;

public class Game {

	private boolean stopped;

	final static int WIDTH = 10, HEIGHT = 24;
	Grid grid;
	JLabel monTexte;
	JFrame frame;
	JPanel panel;
	GameInput gameInput;
	Cell activeCell;

	public Game() {
		gameInput = new GameInput( this );
		this.grid = new Grid( WIDTH, HEIGHT );
		monTexte = new JLabel();
		frame = new JFrame();
		frame.addKeyListener( gameInput );
		panel = new JPanel();
		panel.add( monTexte );
		frame.add( panel );
		frame.setSize( 400, 800 );
		stopped = false;
	}

	public void start() {
		System.out.println( "Starting game." );
		frame.setVisible( true );
		gameTicker();
	}

	private void gameTicker() {
		final double SPEED = 100;
		double lastTime = SPEED;

		while ( !this.stopped ) {
			if ( System.currentTimeMillis() - lastTime >= SPEED ) {
				lastTime = System.currentTimeMillis();

				if ( activeCell == null ) {
					int random = (int) ( Math.random() * WIDTH );
					activeCell = this.grid.getCells()[random][HEIGHT - 1];
					activeCell.setState( Cell.FULL );
				}

				gravity();
				monTexte.setText( grid.printGrid() );
			}
		}
	}

	private void gravity() {
		if ( activeCell != null ) {
			// IF NO CELL UNDERNEATH
			if ( activeCell.getY() != 0 ) {
				Cell cellUnder = grid.getCells()[activeCell.getX()][activeCell.getY() - 1];
				// IF CELL UNDERNEATH IS GROUNDED
				if ( cellUnder.getState() == Cell.FULL ) {
					activeCell.setState( Cell.FULL );
					activeCell = null;
				}
				// IF CELL UNDERNEATH IS EMPTY
				if ( cellUnder.getState() == Cell.EMPTY ) {
					activeCell = grid.moveCell( activeCell, 0, -1 );
				}
			} else {
				activeCell.setState( Cell.FULL );
				activeCell = null;
			}

		}
	}

	public void stop() {
		System.out.println( "Stopping game." );
		this.stopped = true;
		frame.setVisible( false );
	}

	public void move( int direction ) {
		if ( activeCell != null ) {
			if ( direction == -1 ) {
				activeCell = grid.moveCell( activeCell, -1, 0 );
			}
			if ( direction == 1 ) {
				activeCell = grid.moveCell( activeCell, 1, 0 );
			}
		}
	}

}
