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

				if ( grid.findActiveCells().length == 0 ) {
					//Coord[] shape = Shape.randomShape();
					Coord[] shape = {new Coord(0,0)};

					int height = Coord.calculateHeight( shape );
					int width = Coord.calculateWidth( shape );

					int random = (int) ( Math.random() * ( WIDTH - width ) );

					for ( int i = 0; i < shape.length; i++ ) {
						Cell temp = grid.getCells()[random + shape[i].getX()][( HEIGHT )
								- ( height - shape[i].getY() )];
						temp.setState( Cell.ACTIVE );
					}
				}

				//gravity();
				monTexte.setText( grid.printGrid() );
			}
		}
	}

	private void gravity() {

		Coord[] activeCells = grid.findActiveCells();
		if ( activeCells.length > 0 ) {

			boolean isUnderneathClear = true;

			// CHECK
			for ( int i = 0; i < activeCells.length; i++ ) {

				if ( activeCells[i].getY() != 0 ) {
					Cell cellUnder = grid.getCells()[activeCells[i].getX()][activeCells[i].getY() - 1];

					if ( cellUnder.getState() == Cell.FULL ) {
						isUnderneathClear = false;
					}

				} else {
					isUnderneathClear = false;
				}
			}

			// APPLY
			// IF CLEAR
			for ( int i = 0; i < activeCells.length; i++ ) {
				// IF CELL UNDERNEATH IS GROUNDED
				if ( !isUnderneathClear ) {
					grid.getCells()[activeCells[i].getX()][activeCells[i].getY()].setState( Cell.FULL );
				}

			}

			if ( isUnderneathClear ) {
				grid.moveCells( activeCells, 0, -1 );

			}
		}
	}

	public void stop() {
		System.out.println( "Stopping game." );
		this.stopped = true;
		frame.setVisible( false );
		System.exit( 0 );
	}

	public void move( int direction ) {

		Coord[] activeCells = grid.findActiveCells();
		if ( activeCells.length > 0 ) {
			if ( direction == -1 ) {
				grid.moveCells( activeCells, -1, 0 );
			} else if ( direction == 1 ) {
				grid.moveCells( activeCells, 1, 0 );
			}
			else if(direction == 0) {
				grid.rotateCells( activeCells );
			}
		}

	}

}
