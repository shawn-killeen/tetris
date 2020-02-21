package tetris;


import javax.swing.*;

public class Game {

	// CONSTANTS
	private final double BASE_SPEED = 1000, FAST_SPEED = 0.05;
	private final double SPEED_RATE = 0.9;
	
	// VARIABLES
	private double gameSpeed = BASE_SPEED;
	private boolean gameOver = false;
	private int score = 0;

	// OBJECT REFERENCES
	private final Grid GRID;;
	
	// SWING
	private JLabel gameContent = new JLabel("");

	// CONSTRUCTOR
	public Game() {
		GRID = new Grid( this);
	}

	public String getGameContent() {
		return GRID.printGrid();
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
		gameSpeed = calculateSpeed(false);
	}
	
	public double calculateSpeed(boolean accelerate) {
		double speed = BASE_SPEED * Math.pow(SPEED_RATE, score);;
		if(accelerate)
		speed *= FAST_SPEED;
		return speed;
	}

	// STARTS THE GAME
	public void start(JFrame frame) {
		gameContent = new JLabel("");
		JPanel panel = new JPanel();
		panel.add( gameContent );
		frame.add( panel );
		frame.setSize( 300, 750 );
		frame.setLocationRelativeTo( null );
		
		frame.addKeyListener( new GameInput(this) );
		frame.setVisible( false );
		frame.setVisible( true );
		play();
		
		gameOver = true;
		frame.remove(panel);
		
		frame.revalidate();
		
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
			gameSpeed = calculateSpeed(false);
		} else if ( input == GameInput.INPUT_TYPE.SPEEDUP ) {
			gameSpeed = calculateSpeed(true);
		}
		
		// QUIT
		if ( input == GameInput.INPUT_TYPE.QUIT ) {
			gameOver = true;
			System.out.println( "INPUTTTTT" );
		}

	}

}
