import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class Game extends JPanel {

	// CONSTANTS
	private final double BASE_SPEED = 1000, FAST_SPEED = 0.05;
	private final double SPEED_RATE = 0.9;

	// VARIABLES
	private double gameSpeed = BASE_SPEED;
	private boolean gameOver = false;
	private int score = 0;

	// OBJECT REFERENCES
	private final Grid GRID;
	JPanel[][] cells = new JPanel[Grid.WIDTH][Grid.HEIGHT];

	// CONSTRUCTOR
	public Game() {
		GRID = new Grid(this);
		setBackground(Grid.COLOR_EMPTY);
		createGamePanel();
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
	public void addScore(int points) {
		score += points;
		gameSpeed = calculateSpeed(false);
	}

	public double calculateSpeed(boolean accelerate) {
		double speed = BASE_SPEED * Math.pow(SPEED_RATE, score);
		;
		if (accelerate)
			speed *= FAST_SPEED;
		return speed;
	}

	// RUNS THE GAME
	public int play() {

		double lastTime = gameSpeed;

		setFocusable(true);
		requestFocus();
		addKeyListener(new GameInput(this));

		while (!gameOver) {
			refreshGamePanel();
			// CONTROLS CYCLE SPEED
			if (System.currentTimeMillis() - lastTime >= gameSpeed) {
				lastTime = System.currentTimeMillis();

				// SPAWNS SHAPE WHEN NONE IS PRESENT
				GRID.spawnShape();
				GRID.gravity();
				GRID.clearLines();
				gameOver = GRID.isDead();
			}
		}

		return score;
	}

	private void refreshGamePanel() {

		Grid.STATE[][] states = GRID.getStates();
		Color[][] colors = GRID.getColors();

		for (int y = Grid.HEIGHT - 1; y >= 0; y--) {
			for (int x = 0; x < Grid.WIDTH; x++) {

				if (states[x][y] == Grid.STATE.EMPTY) {
					if (cells[x][y].getBackground() != Grid.COLOR_EMPTY) {
						cells[x][y].setBackground(Grid.COLOR_EMPTY);
						cells[x][y].setBorder(BorderFactory.createLineBorder(Grid.COLOR_EMPTY.darker(), 1));
					}
				}

				if (states[x][y] == Grid.STATE.FULL || states[x][y] == Grid.STATE.ACTIVE) {
					cells[x][y].setBackground(colors[x][y]);
					cells[x][y].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
							colors[x][y].brighter().brighter(), colors[x][y].darker().darker()));
				}
			}
		}
	}

	private void createGamePanel() {

		Grid.STATE[][] states = GRID.getStates();

		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 0, 0);
		gbc.weightx = 1;
		gbc.weighty = 1;

		gbc.fill = GridBagConstraints.BOTH;
		for (int y = Grid.HEIGHT - 1; y >= 0; y--) {
			gbc.gridy = Grid.HEIGHT - y;
			for (int x = 0; x < Grid.WIDTH; x++) {
				gbc.gridx = x;
				cells[x][y] = new JPanel();

				cells[x][y].setPreferredSize(new Dimension(20, 20));

				add(cells[x][y], gbc);
			}
		}
	}

	public void input(GameInput.INPUT_TYPE input) {

		ArrayList<Point> activePoints = GRID.findActivePoints();

		// MOVEMENT INPUT
		if (activePoints.size() > 0) {
			if (input == GameInput.INPUT_TYPE.LEFT) {
				GRID.moveCells(activePoints, -1, 0);
			} else if (input == GameInput.INPUT_TYPE.RIGHT) {
				GRID.moveCells(activePoints, 1, 0);
			} else if (input == GameInput.INPUT_TYPE.TURN) {
				GRID.rotateCells(activePoints);
			}
		}

		// SPEED INPUT
		if (input == GameInput.INPUT_TYPE.SLOWDOWN) {
			gameSpeed = calculateSpeed(false);
		} else if (input == GameInput.INPUT_TYPE.SPEEDUP) {
			gameSpeed = calculateSpeed(true);
		}

		// QUIT
		if (input == GameInput.INPUT_TYPE.QUIT) {
			gameOver = true;
		}

	}

}
