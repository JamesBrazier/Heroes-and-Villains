package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;

import Main.Game;
import Main.Hero;
import Main.Team;
import Main.Type;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.border.LineBorder;
import java.awt.Color;
/**
 * 
 * @author jbr185
 *
 */
public class SetupScreen {
	
	protected JFrame setupScreen;
	private Team team = new Team();
	private JTable table;
	private final Action startGame = new SwingAction();
	private JComboBox cbxNumCities;
	private JTextField txtHero1Name;
	private JTextField txtHero2Name;
	private JTextField txtHero3Name;
	private JComboBox hero1Type;
	private JComboBox hero2Type;
	private JComboBox hero3Type;
	private JCheckBox ckbHero2;
	private JCheckBox ckbHero3;
	private final Action addHeroes = new SwingAction_1();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SetupScreen window = new SetupScreen();
					window.setupScreen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SetupScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setupScreen = new JFrame();
		setupScreen.setTitle("Heroes and Villains");
		setupScreen.setResizable(false);
		setupScreen.setBounds(-1, -15, 500, 400);
		setupScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setupScreen.getContentPane().setLayout(null);
		
		JLabel setupGame = new JLabel("Setup Game");
		setupGame.setFont(new Font("Tahoma", Font.PLAIN, 20));
		setupGame.setHorizontalAlignment(SwingConstants.CENTER);
		setupGame.setBounds(156, 11, 181, 27);
		setupScreen.getContentPane().add(setupGame);
		
		JButton btnStartGame = new JButton("Start Game");
		btnStartGame.setAction(startGame);
		btnStartGame.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnStartGame.setBounds(81, 312, 331, 48);
		setupScreen.getContentPane().add(btnStartGame);
		
		JLabel numberOfCities = new JLabel("Number of Cities:");
		numberOfCities.setBounds(158, 61, 114, 15);
		setupScreen.getContentPane().add(numberOfCities);
		
		cbxNumCities = new JComboBox();
		numberOfCities.setLabelFor(cbxNumCities);
		cbxNumCities.setModel(new DefaultComboBoxModel(new String[] {"4", "5", "6"}));
		cbxNumCities.setBounds(295, 57, 44, 22);
		setupScreen.getContentPane().add(cbxNumCities);
		
		table = new JTable();
		table.setBorder(new LineBorder(Color.LIGHT_GRAY));
		table.setFont(new Font("Dialog", Font.PLAIN, 11));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"Name", "Class", "Health", "Attack", "Defence"},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setMinWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(100);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setMinWidth(50);
		table.getColumnModel().getColumn(2).setMaxWidth(50);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setMinWidth(50);
		table.getColumnModel().getColumn(3).setMaxWidth(50);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setMinWidth(50);
		table.getColumnModel().getColumn(4).setMaxWidth(50);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setBounds(48, 237, 398, 64);
		setupScreen.getContentPane().add(table);
		
		JLabel hero1Name = new JLabel("Hero Name:");
		hero1Name.setBounds(91, 94, 70, 15);
		setupScreen.getContentPane().add(hero1Name);
		
		txtHero1Name = new JTextField();
		txtHero1Name.setColumns(10);
		txtHero1Name.setBounds(171, 91, 114, 19);
		setupScreen.getContentPane().add(txtHero1Name);
		
		hero1Type = new JComboBox();
		hero1Type.setModel(new DefaultComboBoxModel(Type.values()));
		hero1Type.setBounds(295, 90, 117, 22);
		setupScreen.getContentPane().add(hero1Type);
		
		JLabel hero2Name = new JLabel("Hero Name:");
		hero2Name.setBounds(91, 127, 70, 15);
		setupScreen.getContentPane().add(hero2Name);
		
		txtHero2Name = new JTextField();
		txtHero2Name.setColumns(10);
		txtHero2Name.setBounds(171, 124, 114, 19);
		setupScreen.getContentPane().add(txtHero2Name);
		
		hero2Type = new JComboBox();
		hero2Type.setModel(new DefaultComboBoxModel(Type.values()));
		hero2Type.setBounds(295, 123, 117, 22);
		setupScreen.getContentPane().add(hero2Type);
		
		JLabel hero3Name = new JLabel("Hero Name:");
		hero3Name.setBounds(91, 160, 70, 15);
		setupScreen.getContentPane().add(hero3Name);
		
		txtHero3Name = new JTextField();
		txtHero3Name.setColumns(10);
		txtHero3Name.setBounds(171, 157, 114, 19);
		setupScreen.getContentPane().add(txtHero3Name);
		
		hero3Type = new JComboBox();
		hero3Type.setModel(new DefaultComboBoxModel(Type.values()));
		hero3Type.setBounds(295, 156, 117, 22);
		setupScreen.getContentPane().add(hero3Type);
		
		JButton btnAddHeroes = new JButton("Add Heroes");
		btnAddHeroes.setAction(addHeroes);
		btnAddHeroes.setBounds(196, 197, 101, 23);
		setupScreen.getContentPane().add(btnAddHeroes);
		
		ckbHero2 = new JCheckBox("");
		ckbHero2.setToolTipText("Add this hero");
		ckbHero2.setBounds(64, 123, 21, 23);
		setupScreen.getContentPane().add(ckbHero2);
		
		ckbHero3 = new JCheckBox("");
		ckbHero3.setToolTipText("Add this hero");
		ckbHero3.setBounds(64, 155, 21, 23);
		setupScreen.getContentPane().add(ckbHero3);
	}
	/**
	 * starts the maingame window and game with the inputed heros stats
	 */
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Start Game");
		}
		public void actionPerformed(ActionEvent e) {
			if (team.getHeroes().size() > 0) {
				Game game = new Game(cbxNumCities.getSelectedIndex() + 4, team);
				MainGame window = new MainGame(game);
				window.mainWindow.setVisible(true);
				setupScreen.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(setupScreen, "You must have at least one hero in your team!");
			}
		}
	}
	/**
	 * adds the inputed values for the heros to the team and the hero table
	 */
	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "Add Heroes");
			putValue(SHORT_DESCRIPTION, "Add the Heros to your team");
		}
		public void actionPerformed(ActionEvent e) {
			ArrayList<Hero> heroes = new ArrayList<Hero>();
			JTextField[] text = {txtHero1Name, txtHero2Name, txtHero3Name};
			JComboBox[] type = {hero1Type, hero2Type, hero3Type};
			boolean[] condition = {true, ckbHero2.isSelected(), ckbHero3.isSelected()};
			for (int i = 0; i < 3; i++) {
				if (condition[i]) {
					heroes.add(new Hero(text[i].getText(), (Type) type[i].getSelectedItem()));
				}
			}
			team.setHeroes(heroes);
			updateTeam();
		}
	}
	/**
	 * updates the team table with the hero stats
	 */
	private void updateTeam() {
		for (int x = 0; x < 5; x++) {
			for (int y = 1; y < 4; y++) {
				table.setValueAt(null, y, x);
			}
		}
		ArrayList<Hero> heroes = team.getHeroes();
		int i = 1;
		for (Hero hero : heroes) {
			table.setValueAt(hero.getName(), i, 0);
			table.setValueAt(hero.getType(), i, 1);
			table.setValueAt(hero.getMaxHealth(), i, 2);
			table.setValueAt(hero.getDamage(), i, 3);
			table.setValueAt(hero.getDefense(), i, 4);
			i++;
		}
	}
}