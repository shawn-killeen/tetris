package tetris;

import javax.swing.*;

public class Game {

	private boolean gameOver;
	private int score = 0;

	final static int WIDTH = 10, HEIGHT = 24;
	final double SLOW_SPEED = 1000, FAST_SPEED = 50;
	double gameSpeed = SLOW_SPEED;
	Grid grid;
	JLabel monTexte;
	JFrame frame;
	JPanel panel;
	GameInput gameInput;

	public Game() {
		gameInput = new GameInput( this );
		grid = new Grid( this, WIDTH, HEIGHT );
		monTexte = new JLabel();
		frame = new JFrame();
		frame.addKeyListener( gameInput );
		panel = new JPanel();
		panel.add( monTexte );
		frame.add( panel );
		frame.setSize( 400, 800 );
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public int getScore() {
		return score;
	}

	public void addScore( int points ) {
		score += points;
	}

	public void start() {
		frame.setVisible( true );
		gameTicker();
	}

	public void stop() {
		gameOver = true;
		frame.setVisible( false );
	}

	private void gameTicker() {
		double lastTime = gameSpeed;

		while ( !gameOver ) {
			if ( System.currentTimeMillis() - lastTime >= gameSpeed ) {
				lastTime = System.currentTimeMillis();

				if ( grid.findActiveCells().length == 0 ) {
					Coord[] shape = Coord.randomShape();

					int height = Coord.calculateHeight( shape );
					int width = Coord.calculateWidth( shape );

					// int random = (int) ( Math.random() * ( WIDTH - width ) );
					int middle = ( WIDTH - width ) / 2;

					for ( int i = 0; i < shape.length; i++ ) {
						Cell temp = grid.getCells()[middle + shape[i].getX()][( HEIGHT )
								- ( height - shape[i].getY() )];

						temp.setState( Cell.STATE.ACTIVE );
					}
				}

				gravity();
				grid.clearLines();
				gameOver = grid.isDead();
			}
			monTexte.setText( grid.printGrid() );
		}
		stop();
	}

	private void gravity() {

		Coord[] activeCells = grid.findActiveCells();
		if ( activeCells.length > 0 ) {

			boolean isUnderneathClear = true;

			// CHECK
			for ( int i = 0; i < activeCells.length; i++ ) {

				if ( activeCells[i].getY() != 0 ) {
					Cell cellUnder = grid.getCells()[activeCells[i].getX()][activeCells[i].getY() - 1];

					if ( cellUnder.getState() == Cell.STATE.FULL ) {
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
					grid.getCells()[activeCells[i].getX()][activeCells[i].getY()].setState( Cell.STATE.FULL );
				}

			}

			if ( isUnderneathClear ) {
				grid.moveCells( activeCells, 0, -1 );

			}
		}
	}

	public void input( GameInput.INPUT_TYPE input ) {

		Coord[] activeCells = grid.findActiveCells();

		if ( activeCells.length > 0 ) {
			if ( input == GameInput.INPUT_TYPE.LEFT ) {
				grid.moveCells( activeCells, -1, 0 );
			} else if ( input == GameInput.INPUT_TYPE.RIGHT ) {
				grid.moveCells( activeCells, 1, 0 );
			} else if ( input == GameInput.INPUT_TYPE.TURN ) {
				grid.rotateCells( activeCells );
			}
		}
		if ( input == GameInput.INPUT_TYPE.SLOWDOWN ) {
			gameSpeed = SLOW_SPEED;
		} else if ( input == GameInput.INPUT_TYPE.SPEEDUP ) {
			gameSpeed = FAST_SPEED;
		}
		if(input == GameInput.INPUT_TYPE.QUIT) {
			stop();
		}

	}

}
