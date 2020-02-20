package tetris;

import javax.swing.*;

// Manages game instances and menus

public class Main {

	private static int gameCount = 0;
	private static Game[] games = new Game[2];

	public static void main( String[] args ) {

		int option = 1;
		do {
			if ( gameCount != games.length ) {
				option = JOptionPane.showConfirmDialog( null, "<html><h1>TETRIS</h1><p>Start new game?</p></html>" );
				if ( option == 0 ) {
					games[gameCount] = new Game();
					games[gameCount].start();
					gameCount++;
					JOptionPane.showMessageDialog( null, "<html><h1>GAME OVER!</h1></html>" );
				}
			} else {
				showScoreboard();
				games = new Game[games.length];
				gameCount = 0;
			}
		} while ( option == 0 );

		System.exit( 0 );
	}

	private static void showScoreboard() {
		String message = "";
		for ( int i = 0; i < games.length; i++ ) {
			if ( games[i].isGameOver() ) {
				message += String.format( "<p>Game %02d --------------- %03d points<p>", i, games[i].getScore() );
			}
		}
		message = "<html><h1>SCOREBOARD</h1>" + message + "</html>";
		JOptionPane.showMessageDialog( null, message );

	}
}
