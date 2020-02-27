package tetris;

import java.io.IOException;
import java.util.ArrayList;

// Manages game instances and menus

public class Main {
	// TODO Spice-up UI
	// TODO scoreboard

	private static ArrayList<Integer> scores = new ArrayList<Integer>();

	public static void main( String[] args ) throws IOException {

		WindowManager windowManager = new WindowManager();
		WindowManager.MENU_ACTION action = WindowManager.MENU_ACTION.MENU;
		while ( true ) {
			switch ( action ) {
			case PLAY:
				action = windowManager.showGame();
				break;
			case SCOREBOARD:
				action = windowManager.showScoreboard( scores );
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
