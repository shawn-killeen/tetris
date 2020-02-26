package tetris;

import java.io.IOException;
import java.util.ArrayList;

// Manages game instances and menus

public class Main {
	// TODO Spice-up UI
	// TODO scoreboard

	static volatile int input = -1;
	private static ArrayList<Game> games = new ArrayList<Game>();

	public static void main( String[] args ) throws IOException {

		WindowManager windowManager = new WindowManager();
		WindowManager.MENU_ACTION action = WindowManager.MENU_ACTION.MENU;
		while ( true ) {
			switch ( action ) {
			case PLAY:
				Game game = new Game(windowManager.getFrame());
				games.add( game );
				action = windowManager.showGame( game );
				break;
			case SCOREBOARD:
				action = windowManager.showScoreboard( games );
				break;
			case QUIT:
				System.exit( 0 );
				break;
			case MENU:
			default:
				action = windowManager.showMenu();
				break;
			}
		}
	}


}
