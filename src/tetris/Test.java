package tetris;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Test {

	static int saisie = -1;
	static Object[] options = { "0", "1"};

	public static void main( String[] args ) {

		JButton[] bouttons = new JButton[options.length];
		JFrame inputFrame = new JFrame( "Calculatrice SuperToto" );
		JLabel texteCalcul = new JLabel( "Hello" );
		JPanel panel = new JPanel();
		JPanel panel2 = new JPanel();

		inputFrame.setLayout( new GridLayout( 2, 1 ) );
		inputFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		inputFrame.setSize( 250, 300 );

		panel2.add( texteCalcul );

		for ( int i = 0; i < options.length; i++ ) {
			JButton button = new JButton( options[i].toString() );
			button.putClientProperty( "index", i );
			bouttons[i] = button;
			panel.add( button );

			button.addActionListener( new ActionListener() {

				@Override
				public void actionPerformed( ActionEvent arg0 ) {

					saisie = ( (Integer) ( (JButton) arg0.getSource() ).getClientProperty( "index" ) );
				}
			} );
		}

		inputFrame.getContentPane().add( panel2 );
		inputFrame.getContentPane().add( panel );
		inputFrame.setVisible( true );

		double nombre1 = 0, nombre2 = 0, resultat = 0;
		int operateur = 10;
		boolean calculer = false, premierNombre = true;

		while ( true ) {
System.out.println( saisie );
			
		}
	}

}
