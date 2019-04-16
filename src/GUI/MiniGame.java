package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Main.Constants;
import Main.Hero;
import Main.PSR;
import Main.Villain;
import Main.Type;
import Main.PSRGame;
import Main.SuperVillain;
import Main.GNGame;
import Main.Game;
import Main.DRGame;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.Action;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
/**
 * 
 * @author jbr185
 *
 */
public class MiniGame extends JFrame {
	
	private int numGame = 0;
	private Hero hero;
	private Game mainGame;
	private Villain villain;
	private String logText = "";
	private MainGame mainWindow;
	private GNGame GNgame;
	protected JFrame miniGameWindow;
	private JLabel heroName;
	private JLabel heroClass;
	private JLabel heroHealth;
	private JLabel heroDamage;
	private JLabel heroDefense;
	private JLabel villainName;
	private JLabel villainHealth;
	private JLabel villainDamage;
	private JLabel villainBounty;
	private JLabel villainTaunt;
	private JLabel villainChoice;
	private JLabel heroChoice;
	private JLabel GNfeedBack;
	private JLabel guesses;
	private JLabel lastGuess;
	private JLabel heroRoll;
	private JLabel villainRoll;
	private JPanel StartScreen;
	private JPanel PSRGame;
	private JPanel GNGame;
	private JPanel DRGame;
	private JButton btnPaper;
	private JButton btnScissors;
	private JButton btnRock;
	private JButton btnGuess;
	private JButton btnRollDice;
	private JTextArea textArea;
	private final Action exit = new Exit();
	private final Action villainFight = new VillainFight();
	private final Action next = new Next();
	private final Action paper = new Paper();
	private final Action scissors = new Scissors();
	private final Action rock = new Rock();
	private JTextField textField;
	private final Action guess = new Guess();
	private final Action rollDice = new RollDice();

	/**
	 * Launch the application.
	 *//*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Constants.random = new Random();
					Villain villain = new Villain(1);
					Hero hero = new Hero("Testy McTestFace", Main.Type.ROGUE);
					MiniGame window = new MiniGame(villain, hero, new JFrame());
					window.miniGameWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public MiniGame(Villain villain, Hero hero, MainGame mainWindow, Game game) {
		this.villain = villain;
		this.hero = hero;
		this.mainWindow = mainWindow;
		this.mainGame = game;
		initialize();
		updateAll();
		log(villain.getName() + ": \"" + villain.getTaunt() + "\"");
	}
	/**
	 * Updates all the UI labels etc.
	 */
	private void updateAll() {
		updatePlayers();
		villainTaunt.setText("\"" + villain.getTaunt() + "\"");
		updatePSR();
		updateGN();
		updateDR();
	}
	/**
	 * Updates the villain and hero panels with their stats
	 */
	private void updatePlayers() {
		heroName.setText(hero.getName());
		heroHealth.setText(hero.getHealth() + "/" + hero.getMaxHealth());
		heroClass.setText(hero.getType().toString().substring(0, 1) + hero.getType().toString().toLowerCase().substring(1));
		heroDamage.setText("" + hero.getDamage());
		heroDefense.setText("" + hero.getDefense());
		villainName.setText(villain.getName());
		villainHealth.setText(villain.getHealth() + "/" + villain.getMaxHealth());
		villainDamage.setText("" + villain.getDamage());
		villainBounty.setText(villain.getBounty() + " gold");
	}
	/**
	 * updates the paper scissors rock minigame panel
	 */
	private void updatePSR() {
		btnPaper.setEnabled(true);
		btnPaper.setOpaque(true);
		btnScissors.setEnabled(true);
		btnScissors.setOpaque(true);
		btnRock.setEnabled(true);
		btnRock.setOpaque(true);
		villainChoice.setText("");
		heroChoice.setText("");
	}
	/**
	 * Updates the guess number minigame panel
	 */
	private void updateGN() {
		GNgame = new GNGame(hero, villain);
		GNfeedBack.setText("\"Guess a number between 1 and " + Constants.GTNHighestPossibleNumber + "\"");
		int temp = Constants.GTNNumberTries;
		if (hero.getType() == Main.Type.WIZARD) {temp += Constants.goodAtGTNModifier;}
		guesses.setText("" + temp);
		lastGuess.setText("");
		btnGuess.setEnabled(true);
		btnGuess.setOpaque(true);
	}
	/**
	 * updates the Dice roll minigame panel
	 */
	private void updateDR() {
		btnRollDice.setEnabled(true);
		btnRollDice.setOpaque(true);
		villainRoll.setText("");
		heroRoll.setText("");
	}
	/**
	 * adds the inputed text to the log String and displays it as a new line on the log
	 * @param text
	 */
	private void log(String text) {
		if (logText.length() > 1000) {
			logText = logText.substring(400);
		}
		logText = logText + "--  " + text + "\n";
		textArea.setText(logText);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		miniGameWindow = new JFrame();
		miniGameWindow.setTitle("Heroes and Villains");
		miniGameWindow.setResizable(false);
		miniGameWindow.setBounds(100, 100, 950, 450);
		miniGameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		miniGameWindow.getContentPane().setLayout(null);
		
		JPanel main = new JPanel();
		main.setBounds(0, 0, 944, 421);
		miniGameWindow.getContentPane().add(main);
		main.setLayout(null);
		
		JPanel Hero = new JPanel();
		Hero.setBorder(new TitledBorder(null, "Hero", TitledBorder.TRAILING, TitledBorder.TOP, null, null));
		Hero.setBounds(10, 11, 174, 201);
		main.add(Hero);
		Hero.setLayout(null);
		
		heroName = new JLabel("<heroName>");
		heroName.setHorizontalAlignment(SwingConstants.CENTER);
		heroName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		heroName.setBounds(10, 14, 154, 32);
		Hero.add(heroName);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(21, 57, 132, 114);
		Hero.add(panel);
		panel.setLayout(null);
		
		JLabel lblClass = new JLabel("Class:");
		lblClass.setBounds(10, 11, 46, 14);
		panel.add(lblClass);
		
		JLabel lblHealth = new JLabel("Health:");
		lblHealth.setBounds(10, 36, 46, 14);
		panel.add(lblHealth);
		
		JLabel lblDamage = new JLabel("Damage:");
		lblDamage.setBounds(10, 61, 63, 14);
		panel.add(lblDamage);
		
		JLabel lblDefense = new JLabel("Defense:");
		lblDefense.setBounds(10, 86, 63, 14);
		panel.add(lblDefense);
		
		heroClass = new JLabel("<class>");
		heroClass.setHorizontalAlignment(SwingConstants.TRAILING);
		heroClass.setBounds(49, 11, 73, 14);
		panel.add(heroClass);
		
		heroHealth = new JLabel("<health>");
		heroHealth.setHorizontalAlignment(SwingConstants.TRAILING);
		heroHealth.setBounds(49, 36, 73, 14);
		panel.add(heroHealth);
		
		heroDamage = new JLabel("<damage>");
		heroDamage.setHorizontalAlignment(SwingConstants.TRAILING);
		heroDamage.setBounds(49, 61, 73, 14);
		panel.add(heroDamage);
		
		heroDefense = new JLabel("<defense>");
		heroDefense.setHorizontalAlignment(SwingConstants.TRAILING);
		heroDefense.setBounds(59, 86, 63, 14);
		panel.add(heroDefense);
		
		JPanel Villain = new JPanel();
		Villain.setBorder(new TitledBorder(null, "Villain", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		Villain.setBounds(706, 11, 174, 174);
		main.add(Villain);
		Villain.setLayout(null);
		
		villainName = new JLabel("<villainName>");
		villainName.setHorizontalAlignment(SwingConstants.CENTER);
		villainName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		villainName.setBounds(10, 14, 154, 32);
		Villain.add(villainName);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setBounds(21, 57, 132, 87);
		Villain.add(panel_1);
		
		JLabel label_5 = new JLabel("Health:");
		label_5.setBounds(10, 11, 46, 14);
		panel_1.add(label_5);
		
		JLabel label_6 = new JLabel("Damage:");
		label_6.setBounds(10, 36, 64, 14);
		panel_1.add(label_6);
		
		villainHealth = new JLabel("<health>");
		villainHealth.setHorizontalAlignment(SwingConstants.TRAILING);
		villainHealth.setBounds(49, 11, 73, 14);
		panel_1.add(villainHealth);
		
		villainDamage = new JLabel("<damage>");
		villainDamage.setHorizontalAlignment(SwingConstants.TRAILING);
		villainDamage.setBounds(49, 36, 73, 14);
		panel_1.add(villainDamage);
		
		JLabel lblBounty = new JLabel("Bounty:");
		lblBounty.setBounds(10, 61, 64, 14);
		panel_1.add(lblBounty);
		
		villainBounty = new JLabel("New label");
		villainBounty.setHorizontalAlignment(SwingConstants.TRAILING);
		villainBounty.setBounds(49, 61, 73, 14);
		panel_1.add(villainBounty);
		
		JPanel logPanel = new JPanel();
		logPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Log", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		logPanel.setBounds(660, 196, 274, 214);
		main.add(logPanel);
		logPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(6, 16, 258, 187);
		logPanel.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		JPanel nullPanel = new JPanel();
		nullPanel.setBounds(10, 223, 174, 187);
		main.add(nullPanel);
		nullPanel.setLayout(null);
		
		JButton btnRunFromFight = new JButton("Run From Fight");
		btnRunFromFight.setBounds(18, 70, 137, 46);
		nullPanel.add(btnRunFromFight);
		btnRunFromFight.setAction(exit);
		
		DRGame = new JPanel();
		DRGame.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Dice Rolling", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
		DRGame.setBounds(194, 11, 456, 399);
		main.add(DRGame);
		DRGame.setEnabled(false);
		DRGame.setLayout(null);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Your Roll", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(45, 57, 150, 150);
		DRGame.add(panel_5);
		panel_5.setLayout(null);
		
		heroRoll = new JLabel("<heroRoll>");
		heroRoll.setFont(new Font("Tahoma", Font.PLAIN, 20));
		heroRoll.setHorizontalAlignment(SwingConstants.CENTER);
		heroRoll.setBounds(10, 11, 130, 128);
		panel_5.add(heroRoll);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Villain Roll", TitledBorder.TRAILING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_6.setBounds(260, 57, 150, 150);
		DRGame.add(panel_6);
		panel_6.setLayout(null);
		
		villainRoll = new JLabel("<villainRoll>");
		villainRoll.setFont(new Font("Tahoma", Font.PLAIN, 20));
		villainRoll.setHorizontalAlignment(SwingConstants.CENTER);
		villainRoll.setBounds(10, 11, 130, 128);
		panel_6.add(villainRoll);
		
		JButton btnNextGame = new JButton("Next Game");
		btnNextGame.setAction(next);
		btnNextGame.setBounds(183, 345, 89, 23);
		DRGame.add(btnNextGame);
		
		btnRollDice = new JButton("Roll Dice");
		btnRollDice.setAction(rollDice);
		btnRollDice.setBounds(162, 292, 132, 42);
		DRGame.add(btnRollDice);
		
		JLabel lblletsRollSome = new JLabel("\"Let's roll some dice shall we?\"");
		lblletsRollSome.setHorizontalAlignment(SwingConstants.CENTER);
		lblletsRollSome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblletsRollSome.setBounds(90, 218, 275, 63);
		DRGame.add(lblletsRollSome);
		DRGame.setVisible(false);
		
		GNGame = new JPanel();
		GNGame.setBorder(new TitledBorder(null, "Guess Number", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GNGame.setBounds(194, 11, 456, 399);
		main.add(GNGame);
		GNGame.setLayout(null);
		GNGame.setEnabled(false);
		GNGame.setVisible(false);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Guess", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(243, 217, 128, 56);
		GNGame.add(panel_2);
		panel_2.setLayout(null);
		
		textField = new JTextField();
		textField.setToolTipText("Make a Guess");
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setBounds(6, 16, 116, 33);
		panel_2.add(textField);
		textField.setColumns(10);
		
		JButton btnNext = new JButton("Next");
		btnNext.setAction(next);
		btnNext.setBounds(183, 342, 89, 23);
		GNGame.add(btnNext);
		
		btnGuess = new JButton("Guess");
		btnGuess.setAction(guess);
		btnGuess.setBounds(183, 308, 89, 23);
		GNGame.add(btnGuess);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Last Guess", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		panel_3.setBounds(170, 25, 116, 116);
		GNGame.add(panel_3);
		panel_3.setLayout(null);
		
		lastGuess = new JLabel("<LastGuess>");
		lastGuess.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lastGuess.setHorizontalAlignment(SwingConstants.CENTER);
		lastGuess.setBounds(10, 11, 96, 94);
		panel_3.add(lastGuess);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Guesses Left", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBounds(93, 217, 116, 54);
		GNGame.add(panel_4);
		panel_4.setLayout(null);
		
		guesses = new JLabel("<Guesses>");
		guesses.setHorizontalAlignment(SwingConstants.CENTER);
		guesses.setBounds(10, 11, 96, 32);
		panel_4.add(guesses);
		
		GNfeedBack = new JLabel("<Higher/Lower>");
		GNfeedBack.setHorizontalAlignment(SwingConstants.CENTER);
		GNfeedBack.setBounds(103, 152, 250, 54);
		GNGame.add(GNfeedBack);
		
		PSRGame = new JPanel();
		PSRGame.setBorder(new TitledBorder(null, "Paper, Scissors, Rock", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		PSRGame.setBounds(194, 11, 456, 399);
		main.add(PSRGame);
		PSRGame.setLayout(null);
		PSRGame.setEnabled(false);
		PSRGame.setVisible(false);
		
		JButton btnPSRnext = new JButton("Next Game");
		btnPSRnext.setAction(next);
		btnPSRnext.setBounds(167, 365, 122, 23);
		PSRGame.add(btnPSRnext);
		
		btnScissors = new JButton("Scissors");
		btnScissors.setAction(scissors);
		btnScissors.setBounds(167, 331, 122, 23);
		PSRGame.add(btnScissors);
		
		btnRock = new JButton("Rock");
		btnRock.setAction(rock);
		btnRock.setBounds(299, 331, 122, 23);
		PSRGame.add(btnRock);
		
		btnPaper = new JButton("Paper");
		btnPaper.setAction(paper);
		btnPaper.setBounds(35, 331, 122, 23);
		PSRGame.add(btnPaper);
		
		JPanel heroPSR = new JPanel();
		heroPSR.setBorder(new LineBorder(Color.LIGHT_GRAY));
		heroPSR.setBounds(35, 54, 175, 175);
		PSRGame.add(heroPSR);
		heroPSR.setLayout(null);
		
		heroChoice = new JLabel("");
		heroChoice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		heroChoice.setHorizontalAlignment(SwingConstants.CENTER);
		heroChoice.setBounds(10, 11, 155, 153);
		heroPSR.add(heroChoice);
		
		JPanel villainPSR = new JPanel();
		villainPSR.setBorder(new LineBorder(Color.LIGHT_GRAY));
		villainPSR.setBounds(246, 54, 175, 175);
		PSRGame.add(villainPSR);
		villainPSR.setLayout(null);
		
		villainChoice = new JLabel("");
		villainChoice.setHorizontalAlignment(SwingConstants.CENTER);
		villainChoice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		villainChoice.setBounds(10, 11, 155, 153);
		villainPSR.add(villainChoice);
		
		JLabel lblpaperScissorsOr = new JLabel("\"Paper, scissors or rock?\"");
		lblpaperScissorsOr.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblpaperScissorsOr.setHorizontalAlignment(SwingConstants.CENTER);
		lblpaperScissorsOr.setBounds(105, 256, 246, 47);
		PSRGame.add(lblpaperScissorsOr);
		
		StartScreen = new JPanel();
		StartScreen.setBorder(new LineBorder(Color.LIGHT_GRAY));
		StartScreen.setBounds(194, 11, 456, 399);
		main.add(StartScreen);
		StartScreen.setLayout(null);
		
		villainTaunt = new JLabel("<villainTaunt>");
		villainTaunt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		villainTaunt.setHorizontalAlignment(SwingConstants.CENTER);
		villainTaunt.setBounds(10, 84, 436, 93);
		StartScreen.add(villainTaunt);
		
		JButton btnFight = new JButton("New button");
		btnFight.setAction(villainFight);
		btnFight.setBounds(154, 250, 147, 55);
		StartScreen.add(btnFight);
	}
	/**
	 * hides this window and reenables the main window
	 */
	private class Exit extends AbstractAction {
		public Exit() {
			putValue(NAME, "Retreat!");
		}
		public void actionPerformed(ActionEvent e) {
			miniGameWindow.setVisible(false);
			mainWindow.mainWindow.setVisible(true);
			mainWindow.mainWindow.setEnabled(true);
			mainWindow.updateAll();
		}
	}
	/**
	 * moves to the next minigame
	 */
	private class VillainFight extends AbstractAction {
		public VillainFight() {
			putValue(NAME, "Fight!");
		}
		public void actionPerformed(ActionEvent e) {
			StartScreen.setVisible(false);
			StartScreen.setEnabled(false);
			nextMiniGame();
		}
	}
	/**
	 * moves to the next minigame
	 */
	private class Next extends AbstractAction {
		public Next() {
			putValue(NAME, "Next");
			putValue(SHORT_DESCRIPTION, "Go to the next minigame");
		}
		public void actionPerformed(ActionEvent e) {
			nextMiniGame();
		}
	}
	/**
	 * enables the panel of the minigame based off the villain's list
	 */
	private void nextMiniGame() {
		switch (villain.miniGames()[numGame]) {
			case PSRGAME:
				PSRGame.setVisible(true);
				PSRGame.setEnabled(true);
				GNGame.setVisible(false);
				GNGame.setEnabled(false);
				DRGame.setVisible(false);
				DRGame.setEnabled(false);
				updatePSR();
				break;
			case GNGAME:
				PSRGame.setVisible(false);
				PSRGame.setEnabled(false);
				GNGame.setVisible(true);
				GNGame.setEnabled(true);
				DRGame.setVisible(false);
				DRGame.setEnabled(false);
				updateGN();
				break;
			case DRGAME:
				PSRGame.setVisible(false);
				PSRGame.setEnabled(false);
				GNGame.setVisible(false);
				GNGame.setEnabled(false);
				DRGame.setVisible(true);
				DRGame.setEnabled(true);
				updateDR();
				break;
		}
		numGame = (numGame + 1) % 3;
	}
	/*
	 * checks whether either the hero or the villain is dead, and if the villain is the final villain
	 * for the players team is all dead, ends the game
	 */
	private void checkDefeat() {
		if (villain.getDefeated()) {
			if (villain instanceof SuperVillain) {
				JOptionPane.showMessageDialog(miniGameWindow, "You have defeated " + villain.getName() + "! you have finally ended this great threat, congratulations!");
				System.exit(0);
			} else {
				JOptionPane.showMessageDialog(miniGameWindow, "You have defeated " + villain.getName() + "! you may now collect your bounty of " + villain.getBounty() + "gold, and travel to the next city!");
				exit.putValue(Action.NAME, "Return");
				mainGame.getTeam().setMoney(mainGame.getTeam().getMoney() + villain.getBounty());
				PSRGame.setVisible(false);
				PSRGame.setEnabled(false);
				GNGame.setVisible(false);
				GNGame.setEnabled(false);
				DRGame.setVisible(false);
				DRGame.setEnabled(false);
			}
		} else if (hero.isDead()) {
			if (mainGame.getTeam().isDead()) {
				JOptionPane.showMessageDialog(miniGameWindow, "Your team has died, Mr Evil's operation continues, you have lost the game");
				System.exit(0);
			} else {
				JOptionPane.showMessageDialog(miniGameWindow, hero.getName() + " has been defeated!");
				exit.putValue(Action.NAME, "Return");
				PSRGame.setVisible(false);
				PSRGame.setEnabled(false);
				GNGame.setVisible(false);
				GNGame.setEnabled(false);
				DRGame.setVisible(false);
				DRGame.setEnabled(false);
			}
		}
	}
	/**
	 * runs the paper scissors rock game with paper as the player choice
	 */
	private class Paper extends AbstractAction {
		public Paper() {
			putValue(NAME, "Paper");
			putValue(SHORT_DESCRIPTION, "Choose paper");
		}
		public void actionPerformed(ActionEvent e) {
			runPSR(PSR.PAPER);
		}
	}
	/**
	 * runs the paper scissors rock game with scissors as the player choice
	 */
	private class Scissors extends AbstractAction {
		public Scissors() {
			putValue(NAME, "Scissors");
			putValue(SHORT_DESCRIPTION, "Choose scissors");
		}
		public void actionPerformed(ActionEvent e) {
			runPSR(PSR.SCISSORS);
		}
	}
	/**
	 * runs the paper scissors rock game with rock as the player choice
	 */
	private class Rock extends AbstractAction {
		public Rock() {
			putValue(NAME, "Rock");
			putValue(SHORT_DESCRIPTION, "Choose rock");
		}
		public void actionPerformed(ActionEvent e) {
			runPSR(PSR.ROCK);
		}
	}
	/**
	 * runs the PSR game based of the inputed player's choice and resolves the effects
	 * @param choice
	 */
	private void runPSR(PSR choice) {
		PSRGame game = new PSRGame(hero, villain);
		game.setHeroPSR(choice);
		game.runGame();
		game.resolveGameEffects();
		updatePlayers();
		villainChoice.setText(game.getVillainPSR().toString());
		heroChoice.setText(game.getHeroPSR().toString());
		switch (game.getLastRound()) {
			case WIN:
				log("You have dealt " + hero.getDamage() + " damage to " + villain.getName());
				break;
			case LOSE:
				log(hero.getName() + " has been dealt " + villain.getDamage() + " damage by " + villain.getName() + ", " + hero.getDefense() + " blocked by hero defense");
				break;
			case DRAW:
				log("Its a draw!");
				break;
		}
		btnPaper.setEnabled(false);
		btnPaper.setOpaque(false);
		btnScissors.setEnabled(false);
		btnScissors.setOpaque(false);
		btnRock.setEnabled(false);
		btnRock.setOpaque(false);
		checkDefeat();
	}
	/**
	 * runs the guess number game based of the inputed guess and resolves the effects
	 * providing feedback if the inputed value is invalid
	 */
	private class Guess extends AbstractAction {
		public Guess() {
			putValue(NAME, "Guess");
		}
		public void actionPerformed(ActionEvent e) {
			try {
				if (Integer.parseInt(textField.getText()) == GNgame.getLastGuess()) {
					JOptionPane.showMessageDialog(miniGameWindow, "You can't guess the same number twice");
				}
				GNgame.setLastGuess(Integer.parseInt(textField.getText()));
				GNgame.runGame();
				GNgame.resolveGameEffects();
				lastGuess.setText(textField.getText());
				guesses.setText("" + (GNgame.getMaxGuesses() - GNgame.getNumGuesses()));
				switch (GNgame.getLastRound()) {
					case WIN:
						GNfeedBack.setText("\"Ahh, you are correct\"");
						log("You have dealt " + hero.getDamage() + " damage to " + villain.getName());
						updatePlayers();
						btnGuess.setEnabled(false);
						btnGuess.setOpaque(false);
						break;
					case LOSE:
						log(hero.getName() + " has been dealt " + villain.getDamage() + " damage by " + villain.getName() + ", " + hero.getDefense() + " blocked by hero defense");
						updatePlayers();
						btnGuess.setEnabled(false);
						btnGuess.setOpaque(false);
						break;
					case DRAW:
						if (GNgame.isLower()) {
							GNfeedBack.setText("\"Higher\"");
						} else {
							GNfeedBack.setText("\"Lower\"");
						} break;
				}
				checkDefeat();
			} catch(NumberFormatException exception) {
				JOptionPane.showMessageDialog(miniGameWindow, "Please type a number to guess");
			}
		}
	}
	/**
	 * rolls the random dice numbers of both player and villain and resolves the effects
	 */
	private class RollDice extends AbstractAction {
		public RollDice() {
			putValue(NAME, "Roll Dice");
		}
		public void actionPerformed(ActionEvent e) {
			DRGame game = new DRGame(hero, villain);
			game.runGame();
			game.resolveGameEffects();
			updatePlayers();
			heroRoll.setText("" + game.getHeroDiceRoll());
			villainRoll.setText("" + game.getVillainDiceRoll());
			btnRollDice.setEnabled(false);
			btnRollDice.setOpaque(false);
			switch (game.getLastRound()) {
			case WIN:
				log("You have dealt " + hero.getDamage() + " damage to " + villain.getName());
				break;
			case LOSE:
				log(hero.getName() + " has been dealt " + villain.getDamage() + " damage by " + villain.getName() + ", " + hero.getDefense() + " blocked by hero defense");
				break;
			case DRAW:
				log("Its a draw!");
				break;
			}
			checkDefeat();
		}
	}
}

