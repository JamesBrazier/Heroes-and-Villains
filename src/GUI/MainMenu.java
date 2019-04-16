package GUI;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
/**
 * 
 * @author jbr185
 *
 */
public class MainMenu {

	private JFrame startMenu;
	private final Action exit = new ExitAction();
	private final Action startNewGame = new NewGameAction();
	private final Action loadGame = new LoadGameAction();
	private final Action settings = new SettingsAction();

	/**
	 * Launch the application. this is the start class for the game
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.startMenu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMenu() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		startMenu = new JFrame();
		startMenu.setTitle("Heroes and Villains");
		startMenu.setResizable(false);
		startMenu.setBounds(100, 100, 250, 450);
		startMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startMenu.getContentPane().setLayout(null);
		
		JLabel label = new JLabel("Main Menu");
		label.setBounds(53, 45, 138, 37);
		startMenu.getContentPane().add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnNewButton = new JButton("New Game");
		btnNewButton.setBounds(64, 93, 116, 37);
		startMenu.getContentPane().add(btnNewButton);
		btnNewButton.setAction(startNewGame);
		
		JButton button_3 = new JButton("Load Game");
		button_3.setBounds(64, 141, 116, 37);
		startMenu.getContentPane().add(button_3);
		button_3.setAction(loadGame);
		
		JButton button_4 = new JButton("Settings");
		button_4.setBounds(64, 189, 116, 37);
		startMenu.getContentPane().add(button_4);
		button_4.setAction(settings);
		
		JButton button_5 = new JButton("Exit");
		button_5.setBounds(64, 349, 116, 37);
		startMenu.getContentPane().add(button_5);
		button_5.setAction(exit);
		
		JLabel lblHeroesAndVillains = new JLabel("Heroes and Villains");
		lblHeroesAndVillains.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblHeroesAndVillains.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeroesAndVillains.setBounds(18, 0, 207, 51);
		startMenu.getContentPane().add(lblHeroesAndVillains);
	}
	/**
	 * Exits the game
	 */
	private class ExitAction extends AbstractAction {
		public ExitAction() {
			putValue(NAME, "Exit");
		}
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	/**
	 * Creates a new startup window and hides this one
	 */
	private class NewGameAction extends AbstractAction {
		public NewGameAction() {
			putValue(NAME, "New Game");
		}
		public void actionPerformed(ActionEvent e) {
			SetupScreen game = new SetupScreen();
			game.setupScreen.setVisible(true);
			startMenu.setVisible(false);
		}
	}
	/**
	 * would have opened a new window to load a old game if I had had time to
	 * finish it
	 */
	private class LoadGameAction extends AbstractAction {
		public LoadGameAction() {
			putValue(NAME, "Load Game");
		}
		public void actionPerformed(ActionEvent e) {
			//this also does stuff
		}
	}
	/**
	 * would have opened a new window to change settings if I had had time to
	 * finish it
	 */
	private class SettingsAction extends AbstractAction {
		public SettingsAction() {
			putValue(NAME, "Settings");
		}
		public void actionPerformed(ActionEvent e) {
			//this might do stuff, who knows
		}
	}
}
