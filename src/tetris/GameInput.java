package tetris;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameInput extends KeyAdapter {

	private Game game;

	public GameInput( Game game ) {
		this.game = game;
	}

	@Override
	public void keyPressed( KeyEvent event ) {

		//System.out.println( event.getKeyCode() );

		if ( event.getKeyCode() == KeyEvent.VK_ESCAPE ) {
			this.game.stop();
		}
		
		if ( event.getKeyCode() == KeyEvent.VK_LEFT ) {
			this.game.move(-1);
		}
		
		if ( event.getKeyCode() == KeyEvent.VK_RIGHT ) {
			this.game.move(1);
		}
		
		if ( event.getKeyCode() == KeyEvent.VK_UP ) {
			this.game.move(0);
		}
	}
}
