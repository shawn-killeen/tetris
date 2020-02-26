package tetris;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class WindowManager {

	private static JFrame frame;

	public static enum MENU_ACTION {
		MENU, PLAY, SCOREBOARD, QUIT
	}

	static volatile int input = -1;

	// Creates new frame
	public WindowManager() {
		frame = new JFrame( "Tetris" );
		frame.setResizable( false );
		frame.setLocationRelativeTo( null );

		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setVisible( true );
	}

	// Creates new button
	private static void addButtonListener( JButton button, int index ) {
		button.putClientProperty( "index", index );
		button.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent arg0 ) {
				input = ( (Integer) ( (JButton) arg0.getSource() ).getClientProperty( "index" ) );
			}
		} );
	}
	
	public JFrame getFrame() {
		return frame;
	}

	// Shows menu and return button input
	public MENU_ACTION showMenu() {

		MENU_ACTION menuAction = MENU_ACTION.MENU;

		JPanel panel = new JPanel();
		JButton playButton = new JButton( "PLAY" ), scoreboardButton = new JButton( "SCOREBOARD" ),
				quitButton = new JButton( "QUIT" );

		frame.setSize( 450, 300 );
		frame.setLocationRelativeTo( null );

		// Explicitly set the size to what you want
		playButton.setPreferredSize( new Dimension( 150, 75 ) );
		scoreboardButton.setPreferredSize( new Dimension( 150, 75 ) );
		quitButton.setPreferredSize( new Dimension( 150, 75 ) );

		addButtonListener( playButton, 0 );
		addButtonListener( scoreboardButton, 1 );
		addButtonListener( quitButton, 2 );

		frame.add( panel );
		panel.setLayout( new GridLayout( 3, 1, 20, 20 ) );
		panel.setBorder( BorderFactory.createEmptyBorder( 20, 50, 20, 50 ) );

		panel.add( playButton );
		panel.add( scoreboardButton );
		panel.add( quitButton );

		// Finally display the frame
		frame.revalidate();

		boolean hasRun = false;
		while ( !hasRun ) {
			switch ( input ) {
			case 0:
				hasRun = true;
				menuAction = MENU_ACTION.PLAY;
				break;
			case 1:
				hasRun = true;
				menuAction = MENU_ACTION.SCOREBOARD;
				break;
			case 2:
				hasRun = true;
				menuAction = MENU_ACTION.QUIT;
				break;
			}
		}
		input = -1;
		frame.remove( panel );
		return menuAction;
	}

	// Starts game
	public MENU_ACTION showGame( Game game ) throws IOException {
		game.start( frame );
		return MENU_ACTION.MENU;
	}

	// Shows scoreboard
	public MENU_ACTION showScoreboard(ArrayList<Game> games) {

		JLabel label = new JLabel( "" );
		JPanel panel = new JPanel();
		JButton button = new JButton( "MENU" );
		String message = "";

		frame.setSize( 450, 300 );
		frame.setLocationRelativeTo( null );

		button.setPreferredSize( new Dimension( 150, 75 ) );

		addButtonListener( button, 3 );

		frame.add( panel );
		panel.setLayout( new GridLayout( 3, 1, 20, 20 ) );
		panel.setBorder( BorderFactory.createEmptyBorder( 20, 50, 20, 50 ) );
		panel.add( label );

		panel.add( button );

		frame.revalidate();

		for ( int i = 0; i < games.size(); i++ ) {
			if ( games.get( i ).isGameOver() ) {
				message += String.format( "<p>Game %02d --------------- %03d points<p>", i,
						games.get( i ).getScore() );
			}
			// PRINT
			message = "<html><h1>SCOREBOARD</h1>" + message + "</html>";
			label.setText( message );
		}

		boolean hasRun = false;
		while ( !hasRun ) {
			if ( input == 3 ) {
				hasRun = true;
				frame.remove( panel );
				input = -1;
			}
		}
		return MENU_ACTION.MENU;
	}
}
