package tetris;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Manages game instances and menus

public class Main {


	// TODO Remove JOptionPane and use JFrame
	// TODO Speed up with score
	// TODO Use CSS and HTML for cells + Color
	// TODO Spice-up UI
	
	// TODO Fix key listener
	static volatile int input = -1;
	private static int gameCount = 0;
	private static Game[] games = new Game[10];

	public static void main( String[] args ) {

		JFrame frame = new JFrame( "Tetris" );
		frame.setResizable( false );

		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		showMenu( frame );

	}

	private static void showMenu( JFrame frame ) {
		final String MENU = "<html><h1 style=\"text-align:center;\">TETRIS</h1></html>";
		
		JLabel label = new JLabel( MENU );
		JPanel panel = new JPanel();
		JButton playButton = new JButton( "PLAY" ), quitButton = new JButton( "QUIT" );


		frame.setSize( 450, 300 );
		frame.setLocationRelativeTo( null );

		// Explicitly set the size to what you want
		playButton.setPreferredSize( new Dimension( 150, 75 ) );
		quitButton.setPreferredSize( new Dimension( 150, 75 ) );
		
		playButton.putClientProperty( "index", 0 );
		playButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent arg0 ) {
				input = ( (Integer) ( (JButton) arg0.getSource() ).getClientProperty( "index" ) );
			}
		} );
		quitButton.putClientProperty( "index", 1 );
		quitButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent arg0 ) {
				input = ( (Integer) ( (JButton) arg0.getSource() ).getClientProperty( "index" ) );
			}
		} );

		// Content pane default layout is BorderLayout which does NOT
		// Respect the preferred sizes. Set it to FlowLayout which does
		frame.add( panel );
		panel.setLayout( new GridLayout( 3, 1, 20, 20 ) );
		panel.setBorder( BorderFactory.createEmptyBorder( 20, 50, 20, 50 ) );
		panel.add( label );

		if ( gameCount < games.length )
			panel.add( playButton );

		panel.add( quitButton );

		// Finally display the frame
		frame.setVisible( true );

		while ( true ) {
			switch ( input ) {
			case 0:
				frame.remove(panel);
				
				gameCount++;
				games[gameCount - 1] = new Game();
				games[gameCount - 1].start(frame);
				
				JOptionPane.showMessageDialog( null, "<html><h1>GAME OVER!</h1></html>" );
				input = -1;
				break;
			case 1:
				input = -1;
				System.exit( 0 );

				break;
			}
		}
	}
	
	private static void showScoreboard( JFrame frame ) {
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
