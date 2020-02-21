package tetris;

import java.util.ArrayList;

// Manages game instances and menus

public class Main {

	// TODO move game display to WindowManager
	// TODO Speed up with score
	// TODO Use CSS and HTML for cells + Color
	// TODO Spice-up UI

	static volatile int input = -1;
	// private static int gameCount = 0;
	// private static Game[] games = new Game[128];
	private static ArrayList<Game> games = new ArrayList<Game>();

	public static void main( String[] args ) {

		WindowManager windowManager = new WindowManager();
		WindowManager.MENU_ACTION action = WindowManager.MENU_ACTION.MENU;
		while ( true ) {
			switch ( action ) {
			case PLAY:
				Game game = new Game();
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
