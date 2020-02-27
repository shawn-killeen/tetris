package tetris;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class WindowManager extends JFrame {

	public static enum MENU_ACTION {
		MENU, PLAY, SCOREBOARD, QUIT
	}

	static volatile int input = -1;

	// Creates new frame
	public WindowManager() {
		setResizable( false );
		setLocationRelativeTo( null );

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setVisible( true );
	}

	// Creates new button
	public static void addButtonListener( JButton button, int index ) {
		button.putClientProperty( "index", index );
		button.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent arg0 ) {
				input = ( (Integer) ( (JButton) arg0.getSource() ).getClientProperty( "index" ) );
			}
		} );
	}

	public void prepareFrame( int width, int height ) {
		getContentPane().removeAll();
		revalidate();
		setSize( width, height );
		setLocationRelativeTo( null );
	}

	// Shows menu and return button input
	public MENU_ACTION showMenu() {

		MENU_ACTION menuAction = MENU_ACTION.MENU;

		JPanel panel = new JPanel();
		JButton playButton = new JButton( "PLAY" ), scoreboardButton = new JButton( "SCOREBOARD" ),
				quitButton = new JButton( "QUIT" );

		prepareFrame( 450, 300 );

		// Explicitly set the size to what you want
		playButton.setPreferredSize( new Dimension( 150, 75 ) );
		scoreboardButton.setPreferredSize( new Dimension( 150, 75 ) );
		quitButton.setPreferredSize( new Dimension( 150, 75 ) );

		addButtonListener( playButton, 0 );
		addButtonListener( scoreboardButton, 1 );
		addButtonListener( quitButton, 2 );

		add( panel );
		panel.setLayout( new GridLayout( 3, 1, 20, 20 ) );
		panel.setBorder( BorderFactory.createEmptyBorder( 20, 50, 20, 50 ) );

		panel.add( playButton );
		panel.add( scoreboardButton );
		panel.add( quitButton );

		// Finally display the frame
		revalidate();

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
		remove( panel );
		return menuAction;
	}

	// Starts game
	public MENU_ACTION showGame() {

		prepareFrame( 250, 850 );
		Game game = new Game();
		add( game );
		revalidate();
		game.play();

		return MENU_ACTION.MENU;
	}

	// Shows scoreboard
	public MENU_ACTION showScoreboard( ArrayList<Integer> scores ) {

		JLabel label = new JLabel( "" );
		JPanel panel = new JPanel();
		JButton button = new JButton( "MENU" );
		String message = "";

		prepareFrame( 450, 300 );

		button.setPreferredSize( new Dimension( 150, 75 ) );

		addButtonListener( button, 3 );

		add( panel );
		panel.setLayout( new GridLayout( 3, 1, 20, 20 ) );
		panel.setBorder( BorderFactory.createEmptyBorder( 20, 50, 20, 50 ) );
		panel.add( label );

		panel.add( button );

		revalidate();

		for ( int i = 0; i < scores.size(); i++ ) {
			message += String.format( "<p>Game %02d : %03d points<p>", i, scores.get( i ) );
			// PRINT
			message = "<html><h1>SCOREBOARD</h1>" + message + "</html>";
			label.setText( message );
		}

		boolean hasRun = false;
		while ( !hasRun ) {
			if ( input == 3 ) {
				hasRun = true;
				remove( panel );
				input = -1;
			}
		}
		return MENU_ACTION.MENU;
	}
}
