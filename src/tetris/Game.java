package tetris;

import javax.swing.*;

public class Game {

	// CONSTANTS
	private final double SLOW_SPEED = 1000, FAST_SPEED = 50;

	// VARIABLES
	private boolean gameOver = false;
	private int score = 0;
	private double gameSpeed = SLOW_SPEED;

	// OBJECT REFERENCES
	private final Grid GRID;
	private final GameInput GAMEINPUT;

	// SWING COMPONENTS
	JLabel gameContent;
	JFrame gameFrame;
	JPanel gamePanel;

	// CONSTRUCTOR
	public Game() {
		GAMEINPUT = new GameInput( this );
		GRID = new Grid( this);
		gameContent = new JLabel();
		gameFrame = new JFrame();
		gameFrame.addKeyListener( GAMEINPUT );
		gamePanel = new JPanel();
		gamePanel.add( gameContent );
		gameFrame.add( gamePanel );
		gameFrame.setSize( 300, 750 );
	}

	// RETURN GAME STATE
	public boolean isGameOver() {
		return gameOver;
	}

	// RETURN GAME SCORE
	public int getScore() {
		return score;
	}

	// ADDS POINTS TO THE SCORE
	public void addScore( int points ) {
		score += points;
	}

	// STARTS THE GAME
	public void start() {
		gameFrame.setVisible( true );
		play();
	}

	// STOPS THE GAME
	public void stop() {
		gameOver = true;
		gameFrame.setVisible( false );
	}

	// RUNS THE GAME
	private void play() {
		double lastTime = gameSpeed;

		while ( !gameOver ) {
			// CONTROLS CYCLE SPEED
			if ( System.currentTimeMillis() - lastTime >= gameSpeed ) {
				lastTime = System.currentTimeMillis();

				// SPAWNS SHAPE WHEN NONE IS PRESENT
				if ( GRID.findActiveCells().length == 0 ) {

					// VARIABLES
					Coord[] shape = Coord.randomShape();
					int height = Coord.calculateHeight( shape );
					int width = Coord.calculateWidth( shape );
					int middle = ( Grid.WIDTH - width ) / 2;

					// PLACES CELLS ON GRID
					for ( int i = 0; i < shape.length; i++ ) {
						Cell temp = GRID.getCells()[middle + shape[i].getX()][( Grid.HEIGHT )
								- ( height - shape[i].getY() )];

						temp.setState( Cell.STATE.ACTIVE );
					}
				}

				// RUN METHODS
				gravity();
				GRID.clearLines();
				gameOver = GRID.isDead();
			}

			// UPDATES DISPLAY
			gameContent.setText( GRID.printGrid() );
		}
		// STOPS GAME WHEN OVER
		stop();
	}

	private void gravity() {

		// MAKES SURE A SHAPE EXISTS
		Coord[] activeCells = GRID.findActiveCells();
		if ( activeCells.length > 0 ) {

			boolean isUnderneathClear = true;

			// CHECK IF AT LEAST ONE CELL IS TOUCHING FULL
			for ( int i = 0; i < activeCells.length; i++ ) {

				// LINES THAT ARENT THE LAST
				if ( activeCells[i].getY() != 0 ) {

					// IF TOUCHES
					Cell cellUnder = GRID.getCells()[activeCells[i].getX()][activeCells[i].getY() - 1];
					if ( cellUnder.getState() == Cell.STATE.FULL ) {
						isUnderneathClear = false;
					}

					// LAST LINE
				} else {
					isUnderneathClear = false;
				}
			}

			// LOWERS CELLS
			if ( isUnderneathClear ) {
				GRID.moveCells( activeCells, 0, -1 );
			}
			// GROUNDS CELLS
			else {
				for ( int i = 0; i < activeCells.length; i++ ) {
					GRID.getCells()[activeCells[i].getX()][activeCells[i].getY()].setState( Cell.STATE.FULL );

				}
			}
		}
	}

	public void input( GameInput.INPUT_TYPE input ) {

		Coord[] activeCells = GRID.findActiveCells();

		// MOVEMENT INPUT
		if ( activeCells.length > 0 ) {
			if ( input == GameInput.INPUT_TYPE.LEFT ) {
				GRID.moveCells( activeCells, -1, 0 );
			} else if ( input == GameInput.INPUT_TYPE.RIGHT ) {
				GRID.moveCells( activeCells, 1, 0 );
			} else if ( input == GameInput.INPUT_TYPE.TURN ) {
				GRID.rotateCells( activeCells );
			}
		}	
		
		// SPEED INPUT
		if ( input == GameInput.INPUT_TYPE.SLOWDOWN ) {
			gameSpeed = SLOW_SPEED;
		} else if ( input == GameInput.INPUT_TYPE.SPEEDUP ) {
			gameSpeed = FAST_SPEED;
		}
		
		// QUIT
		if ( input == GameInput.INPUT_TYPE.QUIT ) {
			stop();
		}

	}

}
