package Main;
import java.util.ArrayList;
/**
 * 
 * @author jbr185
 * this is the class for the command line version of  the game, I wouldn't look at it, its bad
 * and not meant to be marked
 */
public class CommandLine {
	
	private Game game;
	
	public CommandLine() {
		PrintIO.printPopup("Welcome to the Game");
		int cities = generateCities();
		Team team = generateTeam();
		game = new Game(cities, team);
		gameLoop();
	}
	
	private int generateCities() {
		PrintIO.print("Please input the amount of cities you want to play with between 4 and 6: ");
		return PrintIO.inputIntRange(4, 6);
	}
	
	private Team generateTeam() {
		Team team = new Team();
		PrintIO.print("Type the amount of heroes you want to play with from 1 to 3: ");
		int heroes = PrintIO.inputIntRange(1, 3);
		for (int i = 1; i <= heroes; i++) {
			team.addHero(inputHero(i));
		}
		return team;
	}
	
	private Hero inputHero(int number) {
		do {
			PrintIO.printPopup("Hero " + number);
			PrintIO.print("Name: ");
			String name = PrintIO.inputString();
			PrintIO.print("Class: ");
			for (int i = 0; i < Type.values().length; i++) {
				PrintIO.print(" " + i + ":" + Type.values()[i]);
			}
			int input = PrintIO.inputIntRange(0, Type.values().length - 1);
			Type type = Type.values()[input];
			PrintIO.print("Your Hero is " + name + ", class: " + type + ", is this what you want?");
			PrintIO.printList("Yes-no".split("-"));
			input = PrintIO.inputIntRange(0, 1);
			if (input == 0) {
				return new Hero(name, type);
			}
		} while (true);
	}
	
	private void gameLoop() {
		do {
			if (game.getTeam().isDead()) {
				PrintIO.printPopup("Your team has unfortunetly died");
				System.exit(0);
			}
			randomEvent();
			PrintIO.printPopup(game);
			PrintIO.print("What do you wish to do?");
			travel();
			game.playTurn();
		} while (true);
	}
	
	private void randomEvent() {
		RandomEvent event = game.randomEvent();
		switch (event) {
			case ITEMGIVEN:
				PrintIO.printPopup("a young child hands you a small bag and the runs away, the bag seems to conatin a gift");
				break;
			case ITEMTAKEN:
				PrintIO.printPopup("someone brushes past you, later you notice something has gone missing");
				break;
			case NONE:
				break;
		}
	}
	
	private void travel() {
		//List Directions and what they are if discovered
		String[] temp = {"North", "West", "East", "South"};
		for (int i = 0 ; i < temp.length ; i++) {
			String string = " " + i + ": travel " + temp[i] + " which is ";
			if (game.getCity().getDiscovered()[i]) {
				PrintIO.print(string + game.getCity().getLocation(i));
			} else {
				PrintIO.print(string + "unexplored");
			}
		}
		PrintIO.print(" " + temp.length + ": travel to another city");
		//takes direction and send you to function for that location
		int input = PrintIO.inputIntRange(0, 4);
		if (input == 4) {
			PrintIO.print("You prepare to travel to another city");
			visitCity();
		} else {
			PrintIO.print("You travel " + temp[input]);
			game.getCity().setDiscovered(input);
			Location location = game.getCity().getLocation(input);
			switch (location) {
				case SHOP:
					shop();
					return;
				case POWERUPDEN:
					powerupDen();
					return;
				case LAIR: 
					lair();
					return;
				case HOSPITAL:
					hospital();
					return;
			}
		}
	}
	
	private void visitCity() {
		PrintIO.print("Which city do you wish to travel to?");
		int index = game.getPossibleCityIndices();
		for (int i = 0; i <= index; i++) {
			if (game.getCities()[i] == game.getCity()) {
				PrintIO.print(" " + i + ": return to " + game.getCities()[i].getName());
			} else {
				PrintIO.print(" " + i + ": visit " + game.getCities()[i].getName());
			}
		}
		int input = PrintIO.inputIntRange(0, index);
		game.visitCity(input);
		PrintIO.printMessage("your team visits " + game.getCity().getName());
		return;
	}
	
	private void shop() {
		PrintIO.printPopup("Your team arrives at a nearby shop");
		PrintIO.print("What do you wish to do?");
		PrintIO.printList("Buy Items-Sell Items-Return to Inn".split("-"));
		int input = PrintIO.inputIntRange(0, 2);
		switch (input) {
			case 0:
				PrintIO.print("You browse the shop's wares");
				buyItems();
				PrintIO.printLn();
				return;
			case 1:
				PrintIO.print("You offer some of your belongings to the shop owner");
				sellItems();
				PrintIO.printLn();
				return;
			case 2:
				PrintIO.printLn();
				return;
		}
	}
	
	private void buyItems() {
		ArrayList<Item> inventory = game.getCity().getShopInventory();
		//checks for Rogue
		double modifier = 1.0;
		if (game.getTeam().includesType(Type.ROGUE)) {
			modifier = Constants.lowerCostModifier;
		}
		do {
			//Lists what can be purchased 
			PrintIO.printPopup(game.getTeam());
			for (int i = 0; i < inventory.size(); i++) {
				Item item = inventory.get(i);
				PrintIO.print(" " + i + ": buy " + item.getName() + " for " + item.getPrice(modifier));
			}
			PrintIO.print(" " + inventory.size() + ": leave the shop");
			//buys the item
			int itemIndex = PrintIO.inputIntRange(0, inventory.size());
			if (itemIndex == inventory.size()) {
				return;
			}
			Item item = inventory.get(itemIndex);
			if (game.getTeam().canSpend(item.getPrice(modifier))) {
				game.buyItem(item);
				PrintIO.print("you purchase the " + item.getName());
			} else {
				PrintIO.printMessage("you do not have enough gold");
			}
		} while (true);
	}
	
	private void sellItems() {
		ArrayList<Item> inventory = game.getTeam().getItems();
		do {
			//Lists what can be sold
			PrintIO.printPopup(game.getTeam());
			for (int i = 0; i < inventory.size(); i++) {
				Item item = inventory.get(i);
				PrintIO.print(" " + i + ": sell " + item.getName() + " for " + (item.getPrice(0.5)));
			}
			PrintIO.print(" " + inventory.size() + ": leave the shop");
			//sells the item
			int itemIndex = PrintIO.inputIntRange(0, inventory.size());
			if (itemIndex == inventory.size()) {
				return;
			}
			Item item = inventory.get(itemIndex);
			game.sellItem(item);
			PrintIO.printMessage("you sell the " + item.getName());
			PrintIO.printLn();
		} while (true);
	}
	
	private void powerupDen() {
		PrintIO.printPopup("Your Team arrives at a local enchanter");
		PrintIO.print("What do you wish do to?");
		PrintIO.printList("Apply Powerups-Return to Inn".split("-"));
		int input = PrintIO.inputIntRange(0, 1);
		switch (input) {
			case 0:
				PrintIO.print("Your Team approaches the enchanter, who offers to apply any of your powerups");
				usePowerups();
				PrintIO.printLn();
				return;
			case 1:
				PrintIO.printLn();
				return;
		}
	}
	
	private void usePowerups() {
		ArrayList<Item> inventory = game.getTeam().getItems();
		do {
			//lists powerups in player inventory
			PrintIO.printPopup(game.getTeam());
			int num = 0;
			ArrayList<Integer> tempList = new ArrayList<Integer>();
			for (int index = 0; index < inventory.size(); index++) {
				if (inventory.get(index) instanceof ItemPowerup) {
					PrintIO.print(" " + num + ": use " + inventory.get(index).getName());
					tempList.add(index);
					num++;
				}
			}
			PrintIO.print(" " + num + ": leave the enchanter");
			//applies them
			int input = PrintIO.inputIntRange(0, num);
			PrintIO.printLn();
			if (input == num) {
				return;
			} else {
				Hero hero = selectHero();
				Item item = inventory.get(tempList.get(input));
				if (game.useItem(hero, item)) {
					PrintIO.printMessage(item.getName() + " successfully applied to " + hero.getName());
				} else {
					PrintIO.printMessage(hero.getName() + " is already at max for that stat");
				}
			}
		} while (true);
	}
	
	private void hospital() {
		PrintIO.printPopup("Your Team arrives at the town hospital");
		PrintIO.print("What do you wish do to?");
		PrintIO.printList("Heal Heroes-Return to Inn".split("-"));
		int input = PrintIO.inputIntRange(0, 1);
		switch (input) {
			case 0:
				useHealing();
				PrintIO.printLn();
				return;
			case 1:
				PrintIO.printLn();
				return;
		}
	}
	
	private void useHealing() {
		ArrayList<Item> inventory = game.getTeam().getItems();
		do {
			//Lists healing items owned by player
			PrintIO.printPopup(game.getTeam());
			Hero hero = selectHero();
			if (hero.unHospitalize(game.getCity(), game.getTurn())) {
				PrintIO.printMessage("you have recovered " + hero.getName() + " from the hospital");
			} else if (hero.getHospitalCity() != null) {
				PrintIO.printMessage(hero.getName() + " is in another hospital");
			} else {
				int num = 0;
				ArrayList<Integer> tempList = new ArrayList<Integer>();
				for (int index = 0; index < inventory.size(); index++) {
					if (inventory.get(index) instanceof ItemHealing) {
						PrintIO.print(" " + num + ": use " + inventory.get(index).getName());
						tempList.add(index);
						num++;
					}
				}
				PrintIO.print(" " + num + ": leave the hospital");
				//applies them
				int input = PrintIO.inputIntRange(0, num);
				PrintIO.printLn();
				if (input == num) {
					return;
				} else {
					Item item = inventory.get(tempList.get(input));
					if (game.useItem(hero, item)) {
						PrintIO.printMessage(item.getName() + " successfully applied to " + hero.getName());
						PrintIO.printMessage(hero.getName() + " will be healing for the next " + hero.getRecoveryRate() + " turns");
						hero.hospitalize(game.getCity(), game.getTurn());
					} else {
						PrintIO.printMessage(hero.getName() + " is already at full health");
					}
				}
			}
		} while (true);
	}
	

	private void lair() {
		Villain villain = game.getCity().getVillain();
		PrintIO.printPopup("Your Team arrives in a shady district");
		PrintIO.print("It is likely that the villain " + villain.getName() + " hides somewhere around here, do you wish to fight them now?");
		PrintIO.printList("Engage Villain-Return to Inn".split("-"));
		int input = PrintIO.inputIntRange(0, 1);
		switch (input) {
		case 0:
			System.out.println("Your Team stumbles across " + villain.getName() + "'s lair");
			fightVillain();
			PrintIO.printLn();
			return;
		case 1:
			PrintIO.printLn();
			return;
		}
	}
	
	private void fightVillain() {
		Villain villain = game.getCity().getVillain();
		PrintIO.print(villain.getName() + " offers to play a game ");
		MiniGameType[] games = villain.miniGames();
		Hero hero = selectHero();
		int i = 0;
		do {
			PrintIO.printPopup(villain);
			PrintIO.printPopup(hero);
			switch (games[i]) {
				case PSRGAME:
					PrintIO.print("\"how about paper, scissors, rock?\"");
					playPSR(hero, villain);
					break;
				case GNGAME:
					PrintIO.print("\"how about you guess the number i'm thinking of?\"");
					playGN(hero, villain);
					break;
				case DRGAME:
					PrintIO.print("\"how about we roll some dice?\"");
					playDR(hero, villain);
					break;
			}
			i = i++ % 3;
			if (game.getTeam().isDead()) {
				PrintIO.printMessage(hero.getName() + " has been defeated!");
				PrintIO.print("\"HAHAHAHAHA, get fucking rekt!\"");
				return;
			} else if (hero.isDisabled()) {
				PrintIO.printMessage(hero.getName() + " has been defeated!");
				hero = selectHero();
			} else if (villain.getDefeated()) {
				PrintIO.printMessage("you have defeated " + villain.getName() + "! you may now travel to the next city!");
				game.getTeam().increaseMoney(villain.getBounty());
				return;
			}
		} while (true);
	}
	
	private void playPSR(Hero hero, Villain villain) {
		PSRGame miniGame = new PSRGame(hero, villain);
		PrintIO.print("Paper, Scissors or Rock?");
		for (int i = 0; i < PSR.values().length; i++) {
			PrintIO.print(" " + i + ": " + PSR.values()[i].toString());
		}
		PSR psrChoice = PSR.values()[PrintIO.inputIntRange(0, PSR.values().length)];
		miniGame.setHeroPSR(psrChoice);
		miniGame.runGame();
		miniGame.resolveGameEffects();
		PrintIO.print(villain.getName() + ": " + miniGame.getVillainPSR().toString());
		switch (miniGame.getLastRound()) {
		case WIN:
			PrintIO.print("\"you win it seems\"");
			PrintIO.printMessage("you have dealt " + hero.getDamage() + " damage to " + villain.getName());
			break;
		case DRAW:
			PrintIO.print("\"ah, a draw\"");
			break;
		case LOSE:
			PrintIO.print("\"Haha, I win\"");
			PrintIO.printMessage(hero.getName() + " has been hit for " + villain.getDamage() + " by " + villain.getName());
			break;
		}
		return;
	}
	
	private void playGN(Hero hero, Villain villain) {
		GNGame miniGame = new GNGame(hero, villain);
		PrintIO.print(villain.getName() + ": \"guess a number between 1 and " + Constants.GTNHighestPossibleNumber + "\"");
		PrintIO.print("\"You have " + miniGame.getMaxGuesses() + " Guesses\"");
		int i = miniGame.getMaxGuesses();
		while (miniGame.getNumGuesses() < miniGame.getMaxGuesses() && !miniGame.isCorrect()) {
			int choice = PrintIO.inputIntRange(0, Constants.GTNHighestPossibleNumber);
			miniGame.newGuess(choice);
			i--;
			if (miniGame.isHigher()) {
				PrintIO.print("\"Lower, you have " + i + " guesses remaining\"");
			} else if (miniGame.isLower()) {
				PrintIO.print("\"Higher, you have " + i + " guesses remaining\"");
			}
		}
		miniGame.runGame();
		miniGame.resolveGameEffects();
		switch (miniGame.getLastRound()) {
		case WIN:
			PrintIO.print("\"you guessed right\"");
			PrintIO.printMessage("you have dealt " + hero.getDamage() + " damage to " + villain.getName());
			break;
		case DRAW:
			break;
		case LOSE:
			PrintIO.print("\"WRONG!, hahahaha!\"");
			PrintIO.printMessage(hero.getName() + " has been hit for " + villain.getDamage() + " by " + villain.getName());
			break;
		}
		return;	
	}
	
	private void playDR(Hero hero, Villain villain) {
		DRGame miniGame = new DRGame(hero, villain);
		PrintIO.print(villain.getName() + ": \"right, roll your dice\"");
		PrintIO.pause();
		miniGame.runGame();
		miniGame.resolveGameEffects();
		PrintIO.print(hero.getName() + ": rolled a " + miniGame.getHeroDiceRoll());
		PrintIO.print(villain.getName() + ": rolled a " + miniGame.getVillainDiceRoll());
		switch (miniGame.getLastRound()) {
		case WIN:
			PrintIO.print("\"you win it seems\"");
			PrintIO.printMessage("you have dealt " + hero.getDamage() + " damage to " + villain.getName());
			break;
		case DRAW:
			PrintIO.print("\"ah, a draw\"");
			break;
		case LOSE:
			PrintIO.print("\"Haha, I win\"");
			PrintIO.printMessage(hero.getName() + " has been hit for " + villain.getDamage() + " by " + villain.getName());
			break;
		}
		return;	
	}
	
	private Hero selectHero() {
		do {
			PrintIO.print("Select a hero");
			for (int i = 0; i < game.getTeam().getHeroes().size(); i++) {
				Hero hero = game.getTeam().getHeroes().get(i);
				String string = " " + i + ": " + hero.getName();
				if (hero.isDead()) {
					PrintIO.print(string + " is dead");
				} else if (hero.isDisabled()) {
					PrintIO.print(string + " is in Hospital in " + hero.getHospitalCity().getName());
				} else {
					PrintIO.print(string);
				}
			}
			int input = PrintIO.inputIntRange(0, game.getTeam().getHeroes().size() - 1);
			if (game.getTeam().getHeroes().get(input).isDisabled()) {
				PrintIO.printMessage("That hero isn't available");
			} else {
				return game.getTeam().getHeroes().get(input);
			}
		} while (true);
	}
	
	public static void main(String[] args) {
		new CommandLine();
	}

}
