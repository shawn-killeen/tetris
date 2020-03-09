package tetris;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.*;
import javax.swing.border.BevelBorder;

// Manages game instances and menus

public class Main {

	private static ArrayList<Integer> scores = new ArrayList<Integer>();

	public static void main( String[] args ) {

		JFrame frame = new JFrame( "Tetris" );
		frame.setResizable( false );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		JPanel globalPanel = new JPanel();
		globalPanel.setLayout( new GridBagLayout() );
		globalPanel.setBackground( Color.BLACK );

		JPanel gamePanel = new JPanel();
		;
		gamePanel.setLayout( new BorderLayout() );
		gamePanel.setPreferredSize( new Dimension( 300, 800 ) );
		gamePanel.setBackground( Color.DARK_GRAY );
		gamePanel.setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, Color.DARK_GRAY.brighter(),
				Color.DARK_GRAY.darker() ) );

		JPanel previewPanel = new JPanel();
		previewPanel.setPreferredSize( new Dimension( 200, 200 ) );
		previewPanel.setBackground( Color.DARK_GRAY );
		previewPanel.setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, Color.DARK_GRAY.brighter(),
				Color.DARK_GRAY.darker() ) );
		
		JPanel previewContainerPanel = new JPanel();
		previewContainerPanel.setPreferredSize( new Dimension( 190, 190 ) );
		previewContainerPanel.setBackground( Color.DARK_GRAY );
		previewContainerPanel.setBorder( BorderFactory.createLineBorder(Color.DARK_GRAY.darker(), 2));
		
		previewPanel.add( previewContainerPanel );

		JPanel textPanel = new JPanel();
		textPanel.setPreferredSize( new Dimension( 200, 590 ) );
		textPanel.setBackground( Color.DARK_GRAY );
		textPanel.setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, Color.DARK_GRAY.brighter(),
				Color.DARK_GRAY.darker() ) );
		
		JPanel scoreHeaderPanel = new JPanel();
		scoreHeaderPanel.setPreferredSize( new Dimension( 190, 50 ) );
		scoreHeaderPanel.setBackground( Color.DARK_GRAY );
		scoreHeaderPanel.setBorder( BorderFactory.createLineBorder(Color.DARK_GRAY.darker(), 2));
		
		JLabel scoreHeaderLabel = new JLabel("HIGHSCORE");
		scoreHeaderLabel.setForeground(Color.LIGHT_GRAY);
		scoreHeaderLabel.setFont(new Font("Arial", Font.BOLD, 20));
		
		scoreHeaderPanel.add(scoreHeaderLabel);
		
		JPanel scorePanel = new JPanel();
		scorePanel.setPreferredSize( new Dimension( 190, 150) );
		scorePanel.setBackground( Color.DARK_GRAY );
		scorePanel.setBorder( BorderFactory.createLineBorder(Color.DARK_GRAY.darker(), 2));
		
		JLabel scoreLabel = new JLabel("00");
		scoreLabel.setForeground(Color.LIGHT_GRAY);
		scoreLabel.setFont(new Font("Arial", Font.BOLD, 100));
		
		scorePanel.add(scoreLabel);
		
		JPanel helpPanel = new JPanel();
		helpPanel.setPreferredSize( new Dimension( 190, 370 ) );
		helpPanel.setBackground( Color.DARK_GRAY );
		helpPanel.setBorder( BorderFactory.createLineBorder(Color.DARK_GRAY.darker(), 2));
		
		textPanel.add( scoreHeaderPanel );
		textPanel.add( scorePanel );
		textPanel.add( helpPanel );

		JButton quitButton = new JButton( "QUIT GAME" );
		quitButton.setPreferredSize( new Dimension( 510, 50 ) );
		quitButton.setBackground( Color.DARK_GRAY );
		quitButton.setForeground(Color.LIGHT_GRAY);
		quitButton.setFont(new Font("Arial", Font.BOLD, 20));
		quitButton.setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED, Color.DARK_GRAY.darker(),
				Color.DARK_GRAY.darker() ) );
		quitButton.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed( ActionEvent arg0 ) {
				frame.dispatchEvent( new WindowEvent( frame, WindowEvent.WINDOW_CLOSING ) );
			}
		} );

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets( 5, 5, 5, 5 );

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 2;
		globalPanel.add( gamePanel, gbc );
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		globalPanel.add( previewPanel, gbc );
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		globalPanel.add( textPanel, gbc );
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		globalPanel.add( quitButton, gbc );

		frame.add( globalPanel );

		frame.pack();
		frame.setLocationRelativeTo( null );
		frame.setVisible( true );

		while ( true ) {
			Game game = new Game();
			gamePanel.add( game, BorderLayout.CENTER );
			gamePanel.revalidate();
			scores.add( game.play() );
			gamePanel.remove( game );
			refreshScoreboard(scoreLabel);
		}

	}
	
	private static void refreshScoreboard(JLabel text) {
		ArrayList<Integer> scoresCopy = (ArrayList<Integer>)scores.clone();
		
		Collections.sort(scoresCopy);

		text.setText(scoresCopy.get(0).toString());
	}

}
