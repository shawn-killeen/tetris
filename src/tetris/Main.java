package tetris;

import javax.swing.*;

// Manages game instances and menus

public class Main {
	
	//TODO Remove JOptionPane and use JFrame
	//TODO Speed up with score
	//TODO Use CSS and HTML for cells + Color
	//TODO Spice-up UI

	private static int gameCount = 0;
	private static Game[] games = new Game[10];

	public static void main( String[] args ) {

		int option = 1;
		
		do {
			// GAMES ARE NOT OVER
			if ( gameCount < games.length ) {
				option = JOptionPane.showConfirmDialog( null, "<html><h1>TETRIS</h1><p>Start new game?</p></html>" );
				if ( option == 0 ) {
					gameCount++;
					games[gameCount-1] = new Game();
					games[gameCount-1].start();
					JOptionPane.showMessageDialog( null, "<html><h1>GAME OVER!</h1></html>" );
				}
			}
			// GAMES ARE OVER
			else {
				showScoreboard();
			}
		} while ( option == 0 );

		// EXITING GAME	
		showScoreboard();
		System.exit( 0 );
	}

	private static void showScoreboard() {
		String message = "";

		// SHOWS SCOREBOARD IF A GAME WAS PLAYED
		if ( gameCount > 0 ) {
			for ( int i = 0; i < gameCount; i++ ) {
				if ( games[i].isGameOver() ) {
					message += String.format( "<p>Game %02d --------------- %03d points<p>", i, games[i].getScore() );
				}
			}
			// PRINT
			message = "<html><h1>SCOREBOARD</h1>" + message + "</html>";
			JOptionPane.showMessageDialog( null, message );
		}

		// RESET COUNTER
		games = new Game[games.length];
		gameCount = 0;

	}
}
