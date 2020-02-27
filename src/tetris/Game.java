package tetris;

import java.awt.*;
import javax.swing.*;

public class Game extends JPanel {

	// CONSTANTS
	private final double BASE_SPEED = 1000, FAST_SPEED = 0.05;
	private final double SPEED_RATE = 0.9;

	// VARIABLES
	private double gameSpeed = BASE_SPEED;
	private boolean gameOver = false;
	private int score = 0;

	// OBJECT REFERENCES
	private final Grid GRID;;

	// CONSTRUCTOR
	public Game() {
		GRID = new Grid( this );

		JPanel gamePanel = createGamePanel();

		add( gamePanel );

		revalidate();
		repaint();

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
		gameSpeed = calculateSpeed( false );
	}

	public double calculateSpeed( boolean accelerate ) {
		double speed = BASE_SPEED * Math.pow( SPEED_RATE, score );
		;
		if ( accelerate )
			speed *= FAST_SPEED;
		return speed;
	}

	// RUNS THE GAME
	public void play() {

		double lastTime = gameSpeed;

		setFocusable( true );
		requestFocus();
		addKeyListener( new GameInput(this) );

		while ( !gameOver ) {
			// CONTROLS CYCLE SPEED
			if ( System.currentTimeMillis() - lastTime >= gameSpeed ) {
				lastTime = System.currentTimeMillis();

				// frame.revalidate();

				// SPAWNS SHAPE WHEN NONE IS PRESENT
				GRID.spawnShape();
				GRID.gravity();
				GRID.clearLines();
				gameOver = GRID.isDead();
			}
		}
		System.out.println( "GAMEOVER" );
	}

	private JPanel createGamePanel() {

		JPanel panel = new JPanel();
		Cell[][] cells = GRID.getCells();

		panel.setPreferredSize( new Dimension( 210, 810 ) );
		panel.setLayout( new GridBagLayout() );

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets( 1, 1, 1, 1 );
		gbc.weightx = 1;
		gbc.weighty = 1;

		gbc.fill = GridBagConstraints.BOTH;
		for ( int y = Grid.HEIGHT - 1; y >= 0; y-- ) {
			gbc.gridy = Grid.HEIGHT - y;
			for ( int x = 0; x < Grid.WIDTH; x++ ) {
				gbc.gridx = x;
				panel.add( cells[x][y], gbc );
			}
		}
		return panel;
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
			gameSpeed = calculateSpeed( false );
		} else if ( input == GameInput.INPUT_TYPE.SPEEDUP ) {
			gameSpeed = calculateSpeed( true );
		}

		// QUIT
		if ( input == GameInput.INPUT_TYPE.QUIT ) {
			gameOver = true;
		}

	}

}
