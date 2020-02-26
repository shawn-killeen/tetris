package tetris;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// Manages game input

public class GameInput extends KeyAdapter {

	public static enum INPUT_TYPE {
		LEFT, RIGHT, TURN, SPEEDUP, SLOWDOWN, QUIT
	}

	private Game game;

	public GameInput( Game game ) {
		this.game = game;
	}

	@Override
	public void keyPressed( KeyEvent event ) {

		//
		if ( event.getKeyCode() == KeyEvent.VK_ESCAPE ) {
			game.input( INPUT_TYPE.QUIT );
		}

		if ( event.getKeyCode() == KeyEvent.VK_LEFT ) {
			game.input( INPUT_TYPE.LEFT );
		}

		if ( event.getKeyCode() == KeyEvent.VK_RIGHT ) {
			game.input( INPUT_TYPE.RIGHT );
		}

		if ( event.getKeyCode() == KeyEvent.VK_UP ) {
			game.input( INPUT_TYPE.TURN );
		}
		if ( event.getKeyCode() == KeyEvent.VK_DOWN ) {
			game.input( INPUT_TYPE.SPEEDUP );
		}
	}

	@Override
	public void keyReleased( KeyEvent event ) {

		if ( event.getKeyCode() == KeyEvent.VK_DOWN ) {
			this.game.input( INPUT_TYPE.SLOWDOWN );
		}
	}
}
