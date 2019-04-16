package GUI;

import java.awt.EventQueue;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;

import Main.City;
import Main.Constants;
import Main.Game;
import Main.Hero;
import Main.Item;
import Main.ItemHealing;
import Main.ItemMap;
import Main.ItemPowerup;
import Main.Location;
import Main.RandomEvent;
import Main.SuperVillain;
import Main.Team;
import Main.Type;
import Main.Villain;

import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import java.awt.CardLayout;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
/**
 * 
 * @author jbr185
 *
 */
public class MainGame {
	
	private Game game;
	private String logText = "";
	private ArrayList<Item> newShopItems = new ArrayList<Item>();
	private ArrayList<Item> newPlayerItems = new ArrayList<Item>();
	private int shopCost = 0;
	protected JFrame mainWindow;
	private JPanel menu;
	private JPanel main;
	private JPanel CitySelect;
	private JPanel LairPanel;
	private JPanel BasePanel;
	private JPanel ShopPanel;
	private JPanel PowerupPanel;
	private JPanel HospitalPanel;
	private JTable tblTeam;
	private JTable tblInventory;
	private JLabel lblCityName;
	private JLabel lblMoneyamount;
	private JLabel turnNumber;
	private JLabel lblVillainname;
	private JLabel lblVillainhealth;
	private JLabel lblVillainattack;
	private JLabel lblCostprice;
	private JLabel lblAmountcp;
	private JComboBox<ItemMap> cbxMaps;
	private JComboBox<Hero> cbxHeroH;
	private JComboBox<Hero> cbxHeroP;
	private JComboBox<Hero> cbxHeroL;
	private JComboBox<ItemHealing> cbxHealingItem;
	private JComboBox<ItemPowerup> cbxPowerupItem;
	private JTextArea txtLog;
	private JList<Item> ShopPlayer;
	private JList<Item> ShopStock;
	private JButton btnCity1;
	private JButton btnCity2;
	private JButton btnCity3;
	private JButton btnCity4;
	private JButton btnCity5;
	private JButton btnCity6;
	//private final JButton[] cityButtons = {btnCity1, btnCity2, btnCity3, btnCity4, btnCity5, btnCity6};
	private final Action mainMenu = new MainMenu();
	private final Action north = new TravelNorth();
	private final Action west = new TravelWest();
	private final Action east = new TravelEast();
	private final Action south = new TravelSouth();
	private final Action exitGame = new Quit();
	private final Action resume = new Resume();
	private final Action fightVillain = new FightVillain();
	private final Action returnToBase = new Return();
	private final Action travelCity = new TravelCity();
	private final Action useMap = new UseMap();
	private final Action buySell = new BuySell();
	private final Action barter = new Barter();
	private final Action applyPowerup = new ApplyPowerup();
	private final Action applyHealing = new ApplyHealing();
	private final Action dehospitalize = new Unhospitalize();
	private final Action city1 = new City1();
	private final Action city2 = new City2();
	private final Action city3 = new City3();
	private final Action city4 = new City4();
	private final Action city5 = new City5();
	private final Action city6 = new City6();
	private final Action cancel = new Cancel();
	private final Action[] directions = {north, west, east, south};
	private final Action[] cityActions = {city1, city2, city3, city4, city5, city6};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGame window = new MainGame();
					window.mainWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Generates a game for testing purpose
	 */
	public MainGame() {
		Team team = new Team();
		team.addHero(new Hero("Test",  Type.WIZARD));
		team.addHero(new Hero("Test",  Type.ROGUE));
		game = new Game(6, team);
		game.visitCity(4);
		initialize();
		updateAll();
		log("This is a test game");
	}
	/**
	 * Create the application.
	 */
	public MainGame(Game game) {
		this.game = game;
		initialize();
		updateAll();
		log("You have a great quest ahead of you and your team");
	}
	/**
	 * Updates all of the UI labels and etc.
	 */
	public void updateAll() {
		updateTurn();
		updateCompass();
		updateTeam();
		updateInventory();
		updateBase();
		updateLair();
		updateHospital();
		updateShop();
		updatePowerup();
		updateCities();
	}
	/**
	 * Updates the turn counter
	 */
	private void updateTurn() {
		turnNumber.setText(new Integer(game.getTurn()).toString());
	}
	/**
	 * Updates the "compass" (travel buttons) with the directions or the locations
	 * depending on whether they have been discovered
	 */
	private void updateCompass() {
		String[] temp = {"north", "west", "east", "south"};
		City city = game.getCity();
		lblCityName.setText(city.getName() + " (" + game.getCity().getLevel() + ")");
		String string = "Travel to the ";
		for (int i = 0; i < 4; i++) {
			if (city.getDiscovered()[i]) {
				directions[i].putValue(Action.SHORT_DESCRIPTION, string + city.getLocation(i).toString().toLowerCase());
				directions[i].putValue(Action.NAME, city.getLocation(i).toString().substring(0, 1) + city.getLocation(i).toString().toLowerCase().substring(1));
			} else {
				directions[i].putValue(Action.SHORT_DESCRIPTION, string + temp[i]);
				directions[i].putValue(Action.NAME, temp[i]);
			}
		}
	}
	/**
	 * Updates the team table with the stats and locations of all members, and whether 
	 * they are dead
	 */
	private void updateTeam() {
		ArrayList<Hero> heroes = game.getTeam().getHeroes();
		int i = 1;
		for (Hero hero : heroes) {
			tblTeam.setValueAt(hero.getName(), 0, i);
			tblTeam.setValueAt(hero.getType(), 1, i);
			tblTeam.setValueAt(hero.getHealth() + "/" + hero.getMaxHealth(), 2, i);
			tblTeam.setValueAt(hero.getDamage(), 3, i);
			tblTeam.setValueAt(hero.getDefense(), 4, i);
			String aValue;
			if (hero.isDead()) {
				aValue = "DEAD";
			} else if (hero.isDisabled()) {
				aValue = hero.getHospitalCity().getName() + " " + (Math.max(0, hero.getRecoveryRate() - game.getTurn() - hero.getHealTurn()));
			} else {
				aValue = "Party";
			}
			tblTeam.setValueAt(aValue, 5, i);
			Math.min(heroes.size(), i++);
		}
	}
	/**
	 * Updates the inventory table with the items the team has
	 */
	private void updateInventory() {
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 7; y++) {
				tblInventory.setValueAt(null, y, x);
			}
		}
		lblMoneyamount.setText(new Integer(game.getTeam().getMoney()).toString() + " gold");
		ArrayList<Item> inventory = game.getTeam().getItems();
		int i = 0;
		for (Item item : inventory) {
			tblInventory.setValueAt(item.toString(), i / 2, i % 2);
			i++;
		}
	}
	/**
	 * Updates the base screen with the maps the team has
	 */
	private void updateBase() {
		ArrayList<ItemMap> maps = new ArrayList<ItemMap>();
		for (Item item : game.getTeam().getItems()) {
			if (item instanceof ItemMap) {
				maps.add((ItemMap) item);
			}
		}
		cbxMaps.setModel(new DefaultComboBoxModel(maps.toArray()));
	}
	/**
	 * Updates the lair screen with the status of the villain and the heroes
	 */
	private void updateLair() {
		Villain villain = game.getCity().getVillain();
		lblVillainname.setText(villain.getName());
		lblVillainhealth.setText(villain.getHealth() + "/" + villain.getMaxHealth());
		lblVillainattack.setText("" + villain.getDamage());
		cbxHeroL.setModel(new DefaultComboBoxModel(game.getTeam().getHeroes().toArray()));
	}
	/**
	 * Updates the hospital screen with the current healing items owned buy the team
	 */
	private void updateHospital() {
		ArrayList<ItemHealing> heal = new ArrayList<ItemHealing>();
		for (Item item : game.getTeam().getItems()) {
			if (item instanceof ItemHealing) {
				heal.add((ItemHealing) item);
			}
		}
		cbxHealingItem.setModel(new DefaultComboBoxModel(heal.toArray()));
		cbxHeroH.setModel(new DefaultComboBoxModel(game.getTeam().getHeroes().toArray()));
	}
	/**
	 * Resets the shop screen to the current inventories of both player and shop
	 */
	private void updateShop() {
		shopCost = 0;
		newPlayerItems = (ArrayList<Item>) game.getTeam().getItems().clone();
		DefaultListModel<Item> playerItems = new DefaultListModel<Item>();
		newPlayerItems.forEach((Item a) -> playerItems.addElement(a));
		newShopItems = (ArrayList<Item>) game.getCity().getShopInventory().clone();
		DefaultListModel<Item> shopItems = new DefaultListModel<Item>();
		newShopItems.forEach((Item a) -> shopItems.addElement(a));
		ShopPlayer.setModel(playerItems);
		ShopStock.setModel(shopItems);
		lblCostprice.setText("Cost");
		lblAmountcp.setText("0 Gold");
	}
	/**
	 * Updates the shop based of the items the player has selected to sell and buy
	 */
	private void updateBarter() {
		DefaultListModel<Item> playerItems = new DefaultListModel<Item>();
		DefaultListModel<Item> shopItems = new DefaultListModel<Item>();
		for (Item item : ShopStock.getSelectedValuesList()) {
			shopCost += item.getPrice();
			newPlayerItems.add(item);
			newShopItems.remove(item);
		}
		for (Item item : ShopPlayer.getSelectedValuesList()) {
			shopCost -= item.getPrice();
			newShopItems.add(item);
			newPlayerItems.remove(item);
		}
		newPlayerItems.forEach((Item a) -> playerItems.addElement(a));
		newShopItems.forEach((Item a) -> shopItems.addElement(a));
		ShopPlayer.setModel(playerItems);
		ShopStock.setModel(shopItems);
		if (shopCost >= 0) {
			lblCostprice.setText("Cost");
			lblAmountcp.setText(Math.abs(shopCost) + " =>");
		} else {
			lblCostprice.setText("Value");
			lblAmountcp.setText("<= " + Math.abs(shopCost));
		}
	}
	/**
	 * Updates the power up den with the currently owned powerups
	 */
	private void updatePowerup() {
		ArrayList<ItemPowerup> powerup = new ArrayList<ItemPowerup>();
		for (Item item : game.getTeam().getItems()) {
			if (item instanceof ItemPowerup) {
				powerup.add((ItemPowerup) item);
			}
		}
		cbxPowerupItem.setModel(new DefaultComboBoxModel(powerup.toArray()));
		cbxHeroP.setModel(new DefaultComboBoxModel(game.getTeam().getHeroes().toArray()));
	}
	/**
	 * Updates the travel cities screen with the current cities able to be traveled to
	 */
	private void updateCities() {
		if (game.getPossibleCityIndices() >= 0) {
			btnCity1.setEnabled(true);
			btnCity1.setOpaque(true);
		}
		if (game.getPossibleCityIndices() >= 1) {
			btnCity2.setEnabled(true);
			btnCity2.setOpaque(true);
		}
		if (game.getPossibleCityIndices() >= 2) {
			btnCity3.setEnabled(true);
			btnCity3.setOpaque(true);
		}
		if (game.getPossibleCityIndices() >= 3) {
			btnCity4.setEnabled(true);
			btnCity4.setOpaque(true);
		}
		if (game.getCities().length > 4) {
			if (game.getPossibleCityIndices() >= 4) {
				btnCity5.setEnabled(true);
				btnCity5.setOpaque(true);
			}
			if (game.getPossibleCityIndices() >= 5) {
				btnCity6.setEnabled(true);
				btnCity6.setOpaque(true);
			}
		}
		for (int i = 0; i < 6; i++) {
			if (i < game.getCities().length) {
				cityActions[i].putValue(Action.NAME, game.getCities()[i].getName());
			}
			//this wouldn't work for some reason
			/*if (i <= game.getPossibleCityIndices()) {
				cityButtons[i].setEnabled(true);
				cityButtons[i].setOpaque(true);
			}*/
		}
	}
	/**
	 * adds the inputed text to the log String and displays it as a new line on the log
	 * @param text
	 */
	private void log(String text) {
		//Clears some of the string if it gets too long
		if (logText.length() > 1000) {
			logText = logText.substring(400);
		}
		logText = logText + "--  " + text + "\n";
		txtLog.setText(logText);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainWindow = new JFrame();
		mainWindow.setTitle("Heroes and Villains");
		mainWindow.setResizable(false);
		mainWindow.setBounds(100, 100, 1000, 650);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.getContentPane().setLayout(null);
		
		main = new JPanel();
		main.setBounds(0, 0, 994, 621);
		mainWindow.getContentPane().add(main);
		main.setLayout(null);
		
		HospitalPanel = new JPanel();
		HospitalPanel.setLayout(null);
		HospitalPanel.setBounds(10, 321, 543, 289);
		HospitalPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Hospital", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
		main.add(HospitalPanel);
		HospitalPanel.setVisible(false);
		HospitalPanel.setEnabled(false);
		
		cbxHeroH = new JComboBox();
		cbxHeroH.setBounds(218, 58, 187, 20);
		HospitalPanel.add(cbxHeroH);
		
		cbxHealingItem = new JComboBox();
		cbxHealingItem.setBounds(218, 117, 187, 20);
		HospitalPanel.add(cbxHealingItem);
		
		JLabel lblHero = new JLabel("Hero:");
		lblHero.setBounds(140, 61, 68, 14);
		HospitalPanel.add(lblHero);
		
		JLabel lblNewLabel_1 = new JLabel("Healing Item:");
		lblNewLabel_1.setBounds(107, 120, 101, 14);
		HospitalPanel.add(lblNewLabel_1);
		
		JButton btnHeal = new JButton("Hospitalize");
		btnHeal.setAction(applyHealing);
		btnHeal.setBounds(198, 173, 147, 23);
		HospitalPanel.add(btnHeal);
		
		JButton btnUnheal = new JButton("DeHospitalize");
		btnUnheal.setAction(dehospitalize);
		btnUnheal.setBounds(198, 207, 147, 23);
		HospitalPanel.add(btnUnheal);
		
		JButton btnReturn = new JButton("Return");
		btnReturn.setAction(returnToBase);
		btnReturn.setBounds(227, 241, 89, 23);
		HospitalPanel.add(btnReturn);
		
		LairPanel = new JPanel();
		LairPanel.setBounds(10, 321, 543, 289);
		main.add(LairPanel);
		LairPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Villain Lair", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
		LairPanel.setLayout(null);
		LairPanel.setVisible(false);
		LairPanel.setEnabled(false);
		
		JButton btnGoBack = new JButton("Run");
		btnGoBack.setAction(returnToBase);
		btnGoBack.setBounds(315, 240, 117, 25);
		LairPanel.add(btnGoBack);
		
		JButton btnFight = new JButton("Fight");
		btnFight.setAction(fightVillain);
		btnFight.setBounds(110, 240, 117, 25);
		LairPanel.add(btnFight);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(179, 55, 185, 88);
		LairPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblVillain = new JLabel("Name:");
		lblVillain.setBounds(10, 11, 46, 14);
		panel.add(lblVillain);
		
		lblVillainname = new JLabel("");
		lblVillainname.setBounds(54, 11, 92, 14);
		panel.add(lblVillainname);
		
		JLabel lblHealth = new JLabel("Health:");
		lblHealth.setBounds(10, 36, 46, 14);
		panel.add(lblHealth);
		
		lblVillainhealth = new JLabel("villainHealth");
		lblVillainhealth.setBounds(54, 36, 71, 14);
		panel.add(lblVillainhealth);
		
		JLabel lblAttack = new JLabel("Attack:");
		lblAttack.setBounds(10, 61, 46, 14);
		panel.add(lblAttack);
		
		lblVillainattack = new JLabel("villainAttack");
		lblVillainattack.setBounds(54, 61, 71, 14);
		panel.add(lblVillainattack);
		
		cbxHeroL = new JComboBox();
		cbxHeroL.setBounds(230, 184, 202, 20);
		LairPanel.add(cbxHeroL);
		
		JLabel lblHero_1 = new JLabel("Hero:");
		lblHero_1.setBounds(146, 187, 46, 14);
		LairPanel.add(lblHero_1);
		
		lblCityName = new JLabel("");
		lblCityName.setBounds(373, 8, 248, 37);
		main.add(lblCityName);
		lblCityName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblCityName.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblMoney = new JLabel("Money:");
		lblMoney.setBounds(46, 214, 52, 15);
		main.add(lblMoney);
		
		lblMoneyamount = new JLabel("");
		lblMoneyamount.setBounds(91, 214, 92, 14);
		main.add(lblMoneyamount);
		lblMoney.setLabelFor(lblMoneyamount);

		JButton btnNorth = new JButton("North");
		btnNorth.setBounds(442, 72, 110, 80);
		main.add(btnNorth);
		btnNorth.setAction(north);
		
		JButton btnSouth = new JButton("South");
		btnSouth.setBounds(442, 234, 110, 80);
		main.add(btnSouth);
		btnSouth.setAction(south);
		
		JButton btnWest = new JButton("West");
		btnWest.setBounds(350, 153, 110, 80);
		main.add(btnWest);
		btnWest.setAction(west);
		
		JButton btnEast = new JButton("East");
		btnEast.setBounds(533, 153, 110, 80);
		main.add(btnEast);
		btnEast.setAction(east);
		
		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.setBounds(6, 6, 100, 23);
		main.add(btnMainMenu);
		btnMainMenu.setAction(mainMenu);
		
		JPanel Team = new JPanel();
		Team.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Team", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		Team.setBounds(641, 74, 343, 124);
		main.add(Team);
		Team.setLayout(null);
		
		tblTeam = new JTable();
		tblTeam.setBorder(new LineBorder(Color.GRAY));
		tblTeam.setBounds(10, 17, 323, 96);
		Team.add(tblTeam);
		tblTeam.setModel(new DefaultTableModel(
			new Object[][] {
				{" Hero   ", null, null, null},
				{" Class   ", null, null, null},
				{" Health   ", null, null, null},
				{" Attack ", null, null, null},
				{" Defense", null, null, null},
				{" Location ", null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tblTeam.getColumnModel().getColumn(0).setResizable(false);
		tblTeam.getColumnModel().getColumn(0).setPreferredWidth(60);
		tblTeam.getColumnModel().getColumn(0).setMaxWidth(75);
		tblTeam.getColumnModel().getColumn(1).setResizable(false);
		tblTeam.getColumnModel().getColumn(1).setPreferredWidth(50);
		tblTeam.getColumnModel().getColumn(2).setResizable(false);
		tblTeam.getColumnModel().getColumn(2).setPreferredWidth(50);
		tblTeam.getColumnModel().getColumn(3).setResizable(false);
		tblTeam.getColumnModel().getColumn(3).setPreferredWidth(50);
		
		turnNumber = new JLabel("");
		turnNumber.setBounds(154, 10, 46, 14);
		main.add(turnNumber);
		
		JLabel lblTurn = new JLabel("Turn:");
		lblTurn.setBounds(116, 10, 45, 14);
		main.add(lblTurn);
		
		JPanel Log = new JPanel();
		Log.setBounds(563, 321, 421, 289);
		main.add(Log);
		Log.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Log", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		Log.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 21, 401, 257);
		Log.add(scrollPane);
		
		txtLog = new JTextArea();
		scrollPane.setViewportView(txtLog);
		txtLog.setWrapStyleWord(true);
		txtLog.setEditable(false);
		txtLog.setLineWrap(true);
		
		JPanel Inventory = new JPanel();
		Inventory.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Inventory", TitledBorder.TRAILING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		Inventory.setBounds(9, 74, 340, 138);
		main.add(Inventory);
		Inventory.setLayout(null);
		
		tblInventory = new JTable();
		tblInventory.setBorder(new LineBorder(Color.GRAY));
		tblInventory.setBounds(10, 16, 320, 112);
		Inventory.add(tblInventory);
		tblInventory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblInventory.setRowSelectionAllowed(false);
		tblInventory.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"Item", "Effect"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		
		menu = new JPanel();
		menu.setBounds(0, 0, 994, 621);
		mainWindow.getContentPane().add(menu);
		menu.setLayout(null);
		menu.setVisible(false);
		menu.setEnabled(false);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Main Menu", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(350, 85, 293, 451);
		menu.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnNewButton = new JButton("Save Game");
		btnNewButton.setBounds(86, 31, 120, 40);
		panel_1.add(btnNewButton);
		
		JButton btnLoadGame = new JButton("Load Game");
		btnLoadGame.setBounds(86, 82, 120, 40);
		panel_1.add(btnLoadGame);
		
		JButton btnSettings = new JButton("Settings");
		btnSettings.setBounds(86, 133, 120, 40);
		panel_1.add(btnSettings);
		
		JButton btnExitGame = new JButton("Exit Game");
		btnExitGame.setAction(exitGame);
		btnExitGame.setBounds(86, 382, 120, 40);
		panel_1.add(btnExitGame);
		
		JButton btnResume = new JButton("Resume");
		btnResume.setAction(resume);
		btnResume.setBounds(86, 331, 120, 40);
		panel_1.add(btnResume);
		
		ShopPanel = new JPanel();
		ShopPanel.setBounds(10, 321, 543, 289);
		ShopPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Shop", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
		main.add(ShopPanel);
		ShopPanel.setLayout(null);
		ShopPanel.setVisible(false);
		ShopPanel.setEnabled(false);
		
		JScrollPane scrollPanePlayer = new JScrollPane();
		scrollPanePlayer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanePlayer.setBounds(10, 24, 213, 206);
		ShopPanel.add(scrollPanePlayer);
		
		ShopPlayer = new JList();
		scrollPanePlayer.setViewportView(ShopPlayer);
		ShopPlayer.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		JScrollPane scrollPaneShop = new JScrollPane();
		scrollPaneShop.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneShop.setBounds(324, 24, 209, 206);
		ShopPanel.add(scrollPaneShop);
		
		ShopStock = new JList();
		scrollPaneShop.setViewportView(ShopStock);
		ShopStock.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		JButton btnAccept = new JButton("Accept");
		btnAccept.setAction(buySell);
		btnAccept.setBounds(166, 241, 89, 23);
		ShopPanel.add(btnAccept);
		
		JButton btnReturnS = new JButton("Return");
		btnReturnS.setAction(returnToBase);
		btnReturnS.setBounds(291, 241, 89, 23);
		ShopPanel.add(btnReturnS);
		
		lblCostprice = new JLabel("Cost/Price");
		lblCostprice.setHorizontalAlignment(SwingConstants.CENTER);
		lblCostprice.setBounds(233, 42, 76, 14);
		ShopPanel.add(lblCostprice);
		
		lblAmountcp = new JLabel("AmountC/P");
		lblAmountcp.setHorizontalAlignment(SwingConstants.CENTER);
		lblAmountcp.setBounds(221, 67, 101, 14);
		ShopPanel.add(lblAmountcp);
		
		JButton btnSell = new JButton("Offer");
		btnSell.setAction(barter);
		btnSell.setBounds(234, 131, 75, 23);
		ShopPanel.add(btnSell);
		
		PowerupPanel = new JPanel();
		PowerupPanel.setBounds(10, 321, 543, 289);
		PowerupPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Powerup Den", TitledBorder.CENTER, TitledBorder.TOP, null, Color.BLACK));
		main.add(PowerupPanel);
		PowerupPanel.setLayout(null);
		PowerupPanel.setVisible(false);
		PowerupPanel.setEnabled(false);
		
		cbxHeroP = new JComboBox();
		cbxHeroP.setBounds(210, 59, 183, 20);
		PowerupPanel.add(cbxHeroP);
		
		cbxPowerupItem = new JComboBox();
		cbxPowerupItem.setBounds(210, 127, 183, 20);
		PowerupPanel.add(cbxPowerupItem);
		
		JLabel lblHero2 = new JLabel("Hero:");
		lblHero2.setBounds(132, 62, 68, 14);
		PowerupPanel.add(lblHero2);
		
		JLabel lblPowerupItem = new JLabel("Powerup:");
		lblPowerupItem.setBounds(116, 130, 84, 14);
		PowerupPanel.add(lblPowerupItem);
		
		JButton btnUseItem = new JButton("Use Item");
		btnUseItem.setAction(applyPowerup);
		btnUseItem.setBounds(211, 205, 121, 23);
		PowerupPanel.add(btnUseItem);
		
		JButton btnReturnP = new JButton("Return");
		btnReturnP.setAction(returnToBase);
		btnReturnP.setBounds(227, 239, 89, 23);
		PowerupPanel.add(btnReturnP);
		
		BasePanel = new JPanel();
		BasePanel.setLayout(null);
		BasePanel.setBounds(10, 321, 543, 289);
		BasePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Inn", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		main.add(BasePanel);
		
		JButton btnUseMap = new JButton("Use Map");
		btnUseMap.setAction(useMap);
		btnUseMap.setBounds(219, 239, 104, 25);
		BasePanel.add(btnUseMap);
		
		JButton btnTravelCity = new JButton("Travel to another city");
		btnTravelCity.setAction(travelCity);
		btnTravelCity.setBounds(174, 70, 194, 79);
		BasePanel.add(btnTravelCity);
		
		cbxMaps = new JComboBox();
		cbxMaps.setBounds(180, 208, 182, 20);
		BasePanel.add(cbxMaps);
		
		JButton btnInn = new JButton("Inn");
		btnInn.setAction(returnToBase);
		btnInn.setBounds(462, 153, 69, 80);
		main.add(btnInn);
		
		CitySelect = new JPanel();
		CitySelect.setBounds(0, 0, 994, 621);
		mainWindow.getContentPane().add(CitySelect);
		CitySelect.setLayout(null);
		CitySelect.setVisible(false);
		CitySelect.setEnabled(false);
		
		JPanel cityPanel = new JPanel();
		cityPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		cityPanel.setBounds(238, 145, 518, 331);
		CitySelect.add(cityPanel);
		cityPanel.setLayout(null);
		
		btnCity1 = new JButton("City1");
		btnCity1.setBounds(31, 61, 145, 90);
		cityPanel.add(btnCity1);
		btnCity1.setAction(city1);
		btnCity1.setEnabled(false);
		btnCity1.setOpaque(false);
		
		btnCity2 = new JButton("City2");
		btnCity2.setBounds(186, 61, 145, 90);
		cityPanel.add(btnCity2);
		btnCity2.setAction(city2);
		btnCity2.setEnabled(false);
		btnCity2.setOpaque(false);
		
		btnCity3 = new JButton("City3");
		btnCity3.setBounds(341, 61, 145, 90);
		cityPanel.add(btnCity3);
		btnCity3.setAction(city3);
		btnCity3.setEnabled(false);
		btnCity3.setOpaque(false);
		
		btnCity4 = new JButton("City4");
		btnCity4.setBounds(31, 173, 145, 90);
		cityPanel.add(btnCity4);
		btnCity4.setAction(city4);
		btnCity4.setEnabled(false);
		btnCity4.setOpaque(false);
		
		btnCity5 = new JButton("City5");
		btnCity5.setBounds(186, 173, 145, 90);
		cityPanel.add(btnCity5);
		btnCity5.setAction(city5);
		btnCity5.setEnabled(false);
		btnCity5.setOpaque(false);
		
		btnCity6 = new JButton("City6");
		btnCity6.setBounds(341, 173, 145, 90);
		cityPanel.add(btnCity6);
		btnCity6.setAction(city6);
		btnCity6.setEnabled(false);
		btnCity6.setOpaque(false);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(214, 283, 89, 23);
		cityPanel.add(btnCancel);
		btnCancel.setAction(cancel);
		
		JLabel lblTravelToWhich = new JLabel("Travel to which city?");
		lblTravelToWhich.setBounds(175, 11, 167, 37);
		cityPanel.add(lblTravelToWhich);
		lblTravelToWhich.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTravelToWhich.setHorizontalAlignment(SwingConstants.CENTER);
		
	}
	/**
	 * Enables the mainmenu panel and disables the main panel
	 */
	private class MainMenu extends AbstractAction {
		public MainMenu() {
			putValue(NAME, "Main Menu");
			putValue(SHORT_DESCRIPTION, "Open the Main Menu");
		}
		public void actionPerformed(ActionEvent e) {
			main.setVisible(false);
			main.setEnabled(false);
			menu.setVisible(true);
			menu.setEnabled(true);
		}
	}
	/**
	 * sets north as discovered and runs travel with parameter 0
	 */
	private class TravelNorth extends AbstractAction {
		public TravelNorth() {
			putValue(NAME, "North");
			putValue(SHORT_DESCRIPTION, "Travel to the north");
		}
		public void actionPerformed(ActionEvent e) {
			game.getCity().setDiscovered(0);
			travel(0);
		}
	}
	/**
	 * sets west as discovered and runs travel with parameter 1
	 */
	private class TravelWest extends AbstractAction {
		public TravelWest() {
			putValue(NAME, "West");
			putValue(SHORT_DESCRIPTION, "Travel to the west");
		}
		public void actionPerformed(ActionEvent e) {
			game.getCity().setDiscovered(1);
			travel(1);
		}
	}
	/**
	 * sets east as discovered and runs travel with parameter 2
	 */
	private class TravelEast extends AbstractAction {
		public TravelEast() {
			putValue(NAME, "East");
			putValue(SHORT_DESCRIPTION, "Travel to the east");
		}
		public void actionPerformed(ActionEvent e) {
			game.getCity().setDiscovered(2);
			travel(2);
		}
	}
	/**
	 * sets south as discovered and runs travel with parameter 3
	 */
	private class TravelSouth extends AbstractAction {
		public TravelSouth() {
			putValue(NAME, "South");
			putValue(SHORT_DESCRIPTION, "Travel to the south");
		}
		public void actionPerformed(ActionEvent e) {
			game.getCity().setDiscovered(3);
			travel(3);
		}
	}
	/**
	 * prompts the user if they are sure and quits the game if agreed
	 */
	private class Quit extends AbstractAction {
		public Quit() {
			putValue(NAME, "Exit Game");
			putValue(SHORT_DESCRIPTION, "Quit to desktop");
		}
		public void actionPerformed(ActionEvent e) {
			int i = JOptionPane.showConfirmDialog(mainWindow, "Are you sure you want to quit?", "", JOptionPane.OK_CANCEL_OPTION);
			if (i == 0) {
				System.exit(0);
			}
		}
	}
	/**
	 * disbles the mainmenu panel and enables the mainpanel
	 */
	private class Resume extends AbstractAction {
		public Resume() {
			putValue(NAME, "Resume");
		}
		public void actionPerformed(ActionEvent e) {
			menu.setVisible(false);
			menu.setEnabled(false);
			main.setVisible(true);
			main.setEnabled(true);
			updateAll();
		}
	}
	/**
	 * opens a new minigame window and disables maingame window, prompting the user if something is invalid
	 */
	private class FightVillain extends AbstractAction {
		public FightVillain() {
			putValue(NAME, "Fight");
			putValue(SHORT_DESCRIPTION, "Engage the Villain in combat!");
		}
		public void actionPerformed(ActionEvent e) {
			Villain villain = game.getCity().getVillain();
			Hero hero = (Hero) cbxHeroL.getSelectedItem();
			if (villain.getDefeated()) {
				JOptionPane.showMessageDialog(mainWindow, "You have already killed " + villain.getName());
			} else if (hero.isDisabled()){
				JOptionPane.showMessageDialog(mainWindow, "That hero is not available");
			} else {
				log("You engage the evil " + villain.getName() + "!");
				if (villain instanceof SuperVillain) {
					JOptionPane.showMessageDialog(mainWindow, "This is the lair of the leader of this operation, I hope you are ready");
				}
				MiniGame window = new MiniGame(villain, hero, MainGame.this, game);
				window.miniGameWindow.setVisible(true);
				mainWindow.setEnabled(false);
				game.playTurn();
				updateAll();
			}
		}
	}
	/**
	 * disables all panels and enables the inn panel
	 */
	private class Return extends AbstractAction {
		public Return() {
			putValue(NAME, "Inn");
			putValue(SHORT_DESCRIPTION, "Return to Home Base");
		}
		public void actionPerformed(ActionEvent e) {
			BasePanel.setVisible(true);
			BasePanel.setEnabled(true);
			LairPanel.setVisible(false);
			LairPanel.setEnabled(false);
			ShopPanel.setVisible(false);
			ShopPanel.setEnabled(false);
			HospitalPanel.setVisible(false);
			HospitalPanel.setEnabled(false);
			PowerupPanel.setVisible(false);
			PowerupPanel.setEnabled(false);
			randomEvent();
			updateAll();
			log("You return to the Inn");
		}
	}
	/**
	 * rolls a random event and reports if one occurred 
	 */
	private void randomEvent() {
		RandomEvent event = game.randomEvent();
		switch (event) {
			case ITEMGIVEN:
				updateInventory();
				JOptionPane.showMessageDialog(mainWindow, "Whilst returning to the inn, you note a group of townfolk waiting outside the inn, they have come to give you a small gift");
				log("Whilst returning to the inn, you note a group of townfolk waiting outside the inn, they have come to give you a small gift");
				break;
			case ITEMTAKEN:
				updateInventory();
				JOptionPane.showMessageDialog(mainWindow, "Someone brushed past you, later you note something is missing");
				log("Someone brushed past you, later you note something is missing");
				break;
			default:
				break;
		}
	}
	/**
	 * gets the location from the direction in the city and enables the panel associated
	 * with it disabling the rest
	 * @param direction
	 */
	private void travel(int direction) {
		Location location = game.getCity().getLocation(direction);
		randomEvent();
		String string = "You arrive ";
		switch (location) {
			case LAIR:
				BasePanel.setVisible(false);
				BasePanel.setEnabled(false);
				LairPanel.setVisible(true);
				LairPanel.setEnabled(true);
				ShopPanel.setVisible(false);
				ShopPanel.setEnabled(false);
				HospitalPanel.setVisible(false);
				HospitalPanel.setEnabled(false);
				PowerupPanel.setVisible(false);
				PowerupPanel.setEnabled(false);
				log(string + "in a shady district");
				break;
			case HOSPITAL:
				BasePanel.setVisible(false);
				BasePanel.setEnabled(false);
				LairPanel.setVisible(false);
				LairPanel.setEnabled(false);
				ShopPanel.setVisible(false);
				ShopPanel.setEnabled(false);
				HospitalPanel.setVisible(true);
				HospitalPanel.setEnabled(true);
				PowerupPanel.setVisible(false);
				PowerupPanel.setEnabled(false);
				log(string + "near a local Hospital");
				break;
			case SHOP:
				BasePanel.setVisible(false);
				BasePanel.setEnabled(false);
				LairPanel.setVisible(false);
				LairPanel.setEnabled(false);
				ShopPanel.setVisible(true);
				ShopPanel.setEnabled(true);
				HospitalPanel.setVisible(false);
				HospitalPanel.setEnabled(false);
				PowerupPanel.setVisible(false);
				PowerupPanel.setEnabled(false);
				log(string + "at a local Shop");
				break;
			case POWERUPDEN:
				BasePanel.setVisible(false);
				BasePanel.setEnabled(false);
				LairPanel.setVisible(false);
				LairPanel.setEnabled(false);
				ShopPanel.setVisible(false);
				ShopPanel.setEnabled(false);
				HospitalPanel.setVisible(false);
				HospitalPanel.setEnabled(false);
				PowerupPanel.setVisible(true);
				PowerupPanel.setEnabled(true);
				log(string + "at the Power-up Den");
				break;
		}
		updateAll();
	}
	/**
	 * Enables the travel city panel and disables the main
	 */
	private class TravelCity extends AbstractAction {
		public TravelCity() {
			putValue(NAME, "Travel to another city");
		}
		public void actionPerformed(ActionEvent e) {
			main.setVisible(false);
			main.setEnabled(false);
			CitySelect.setVisible(true);
			CitySelect.setEnabled(true);
			updateAll();
		}
	}
	/**
	 * uses the map currently selected in the map combobox and fully discovers that city
	 */
	private class UseMap extends AbstractAction {
		public UseMap() {
			putValue(NAME, "Use Map");
			putValue(SHORT_DESCRIPTION, "Use the seleced map reveal the cities main locations");
		}
		public void actionPerformed(ActionEvent e) {
			if (cbxMaps.getSelectedItem() instanceof ItemMap) {
				ItemMap map = (ItemMap) cbxMaps.getSelectedItem();
				if (game.useItem(game.getTeam().getHeroes().get(0), map)) {
					updateAll();
					log("Your team takes note of the main locations of " + map.getCityName());
				} else {
					log("You have no need for this map, you already know where everything is in " + map.getCityName());
				}
			} else {
				JOptionPane.showMessageDialog(mainWindow, "Please select a map");
			}
		}
	}
	/**
	 * sets the inventories of the shop and player to those created by bartering and changes
	 * the player money depending of the cost value of that transaction
	 */
	private class BuySell extends AbstractAction {
		public BuySell() {
			putValue(NAME, "Accept");
			putValue(SHORT_DESCRIPTION, "Accept the shop owner's offer");
		}
		public void actionPerformed(ActionEvent e) {
			if (shopCost <= game.getTeam().getMoney()) {
				game.getTeam().setInventory(newPlayerItems);
				game.getCity().setShopInventory(newShopItems);
				game.getTeam().setMoney(game.getTeam().getMoney() - shopCost);
				game.playTurn();
				updateAll();
			} else {
				JOptionPane.showMessageDialog(mainWindow, "You do not have enough money for this offer!");
			}
		}
	}
	/**
	 * updates the shop screen as per the update barter function
	 */
	private class Barter extends AbstractAction {
		public Barter() {
			putValue(NAME, "Offer");
			putValue(SHORT_DESCRIPTION, "Offer this trade for consideration");
		}
		public void actionPerformed(ActionEvent e) {
			updateBarter();
			log("The owner makes offer");
		}
	}
	/**
	 * uses the selected powerup on the selected hero giving feedback if unable
	 */
	private class ApplyPowerup extends AbstractAction {
		public ApplyPowerup() {
			putValue(NAME, "Use Powerup");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			if (cbxPowerupItem.getSelectedItem() instanceof ItemPowerup) {
				ItemPowerup powerup = (ItemPowerup) cbxPowerupItem.getSelectedItem();
				Hero hero = (Hero) cbxHeroP.getSelectedItem();
				if (game.useItem(hero, powerup)) {
					game.playTurn();
					updateAll();
					log("You have successfully applied " + powerup.getName() + " to " + hero.getName());
				} else if (hero.isDisabled()) {
					JOptionPane.showMessageDialog(mainWindow, "That hero is not available");
				} else {
					JOptionPane.showMessageDialog(mainWindow, "That hero is already at max for that attribute");
				}
			} else {
				JOptionPane.showMessageDialog(mainWindow, "Please select a powerup");
			}
			
		}
	}
	/**
	 * uses the selected healing item on the selected hero giving feedback if unable and 
	 * hospitalizing them
	 */
	private class ApplyHealing extends AbstractAction {
		public ApplyHealing() {
			putValue(NAME, "Use Healing");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			if (cbxHealingItem.getSelectedItem() instanceof ItemHealing) {
				ItemHealing item = (ItemHealing) cbxHealingItem.getSelectedItem();
				Hero hero = (Hero) cbxHeroH.getSelectedItem();
				System.out.println(hero + "" + item);
				if (game.useItem(hero, item)) {
					game.playTurn();
					updateAll();
					log("You have placed " + hero.getName() + " in the hospistal to rest, he will have to rest for at least " + hero.getRecoveryRate() + " turns");
				} else if (hero.isDisabled()) {
					JOptionPane.showMessageDialog(mainWindow, "That hero is not available");
				} else {
					JOptionPane.showMessageDialog(mainWindow, "That hero is already at max health");
				}
			} else {
				JOptionPane.showMessageDialog(mainWindow, "Please select a healing item");
			}
		}
	}
	/**
	 * removes the selected hero from the hospital if they are finished healing
	 * are in the current visited city, giving feed back otherwise
	 */
	private class Unhospitalize extends AbstractAction {
		public Unhospitalize() {
			putValue(NAME, "Recover Hero");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			Hero hero = (Hero) cbxHeroH.getSelectedItem();
			if (hero.unHospitalize(game.getCity(), game.getTurn())) {
				game.playTurn();
				updateAll();
				log("You have successfully recovered " + hero.getName() + " from the hospital");
			} else if (hero.isDisabled()) {
				if (hero.getHospitalCity() == game.getCity()) {
					JOptionPane.showMessageDialog(mainWindow, "That hero is not in this city's hospital, return to " + hero.getHospitalCity().getName() + " to recover him");
				} else {
					JOptionPane.showMessageDialog(mainWindow, "That hero is still resting");
				}
			} else {
				JOptionPane.showMessageDialog(mainWindow, "That hero isn't recovering");
			}
		}
	}
	/**
	 * Travels to the city of index 0
	 */
	private class City1 extends AbstractAction {
		public City1() {
			putValue(NAME, "City1");
		}
		public void actionPerformed(ActionEvent e) {
			CitySelect.setVisible(false);
			CitySelect.setEnabled(false);
			main.setVisible(true);
			main.setEnabled(true);
			game.playTurn();
			game.visitCity(0);
			updateAll();
			log("Your team travels to the city of " + game.getCity().getName());
		}
	}
	/**
	 * Travels to the city of index 1
	 */
	private class City2 extends AbstractAction {
		public City2() {
			putValue(NAME, "City2");
		}
		public void actionPerformed(ActionEvent e) {
			CitySelect.setVisible(false);
			CitySelect.setEnabled(false);
			main.setVisible(true);
			main.setEnabled(true);
			game.playTurn();
			game.visitCity(1);
			updateAll();
			log("Your team travels to the city of " + game.getCity().getName());
		}
	}
	/**
	 * Travels to the city of index 2
	 */
	private class City3 extends AbstractAction {
		public City3() {
			putValue(NAME, "City3");
		}
		public void actionPerformed(ActionEvent e) {
			CitySelect.setVisible(false);
			CitySelect.setEnabled(false);
			main.setVisible(true);
			main.setEnabled(true);
			game.playTurn();
			game.visitCity(2);
			updateAll();
			log("Your team travels to the city of " + game.getCity().getName());
		}
	}
	/**
	 * Travels to the city of index 3
	 */
	private class City4 extends AbstractAction {
		public City4() {
			putValue(NAME, "City4");
		}
		public void actionPerformed(ActionEvent e) {
			CitySelect.setVisible(false);
			CitySelect.setEnabled(false);
			main.setVisible(true);
			main.setEnabled(true);
			game.playTurn();
			game.visitCity(3);
			updateAll();
			log("Your team travels to the city of " + game.getCity().getName());
			if (game.getCities().length == 4) {
				log("This is the capital, the leader hides here");
				JOptionPane.showMessageDialog(mainWindow, "This is the capital, the leader hides here, be sure to be ready for this fight");
			}
		}
	}
	/**
	 * Travels to the city of index 4
	 */
	private class City5 extends AbstractAction {
		public City5() {
			putValue(NAME, "");
		}
		public void actionPerformed(ActionEvent e) {
			CitySelect.setVisible(false);
			CitySelect.setEnabled(false);
			main.setVisible(true);
			main.setEnabled(true);
			game.playTurn();
			game.visitCity(4);
			updateAll();
			log("Your team travels to the city of " + game.getCity().getName());
			if (game.getCities().length == 5) {
				log("This is the capital, the leader hides here");
				JOptionPane.showMessageDialog(mainWindow, "This is the capital, the leader hides here, be sure to be ready for this fight");
			}
		}
	}
	/**
	 * Travels to the city of index 5
	 */
	private class City6 extends AbstractAction {
		public City6() {
			putValue(NAME, "");
		}
		public void actionPerformed(ActionEvent e) {
			CitySelect.setVisible(false);
			CitySelect.setEnabled(false);
			main.setVisible(true);
			main.setEnabled(true);
			game.playTurn();
			game.visitCity(5);
			updateAll();
			log("Your team travels to the city of " + game.getCity().getName());
			log("This is the capital, the leader hides here");
			JOptionPane.showMessageDialog(mainWindow, "This is the capital, the leader hides here, be sure to be ready for this fight");
		}
	}
	/**
	 * cancels the travel and returns to the main panel
	 */
	private class Cancel extends AbstractAction {
		public Cancel() {
			putValue(NAME, "Cancel");
		}
		public void actionPerformed(ActionEvent e) {
			CitySelect.setVisible(false);
			CitySelect.setEnabled(false);
			main.setVisible(true);
			main.setEnabled(true);
			updateAll();
		}
	}
}
