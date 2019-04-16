package Main;
import java.util.Random;
import java.util.ArrayList;
/**
 * @author jbr185
 */
public class Game {
	
	private City[] cities;
	private City currentCity;
	private Team team;
	private int turn;
	
	/**
	 * Generates the cities required for the game based off the game length and takes in a team
	 * or heroes for the game
	 * @param gameLength amount of cities to generate
	 * @param gameTeam the team of heroes to play the game
	 */
	public Game(int gameLength, Team gameTeam) {
		Constants.random = new Random();
		//generates cities
		cities = new City[gameLength];
		for (int i = 0; i < (gameLength - 1); i++) {
			cities[i] = new City(Constants.cityNames[i], i + 1, false);
		}
		cities[gameLength - 1] = new City(Constants.cityNames[gameLength - 1], gameLength, true);
		//initiates last variables
		team = gameTeam;
		turn = 1;
		visitCity(0);
	}
	/**
	 * creates a game object in a specified state
	 * @param gameCities the cities in the game
	 * @param gameCity the current city in which the team is in
	 * @param gameTeam the game's team
	 * @param currentTurn the current turn number
	 */
	public Game(City[] gameCities, City gameCity, Team gameTeam, int currentTurn) {
		Constants.random = new Random();
		cities = gameCities;
		currentCity = gameCity;
		team = gameTeam;
		turn = currentTurn;
	}
	/**
	 * increments the turn counter
	 */
	public void playTurn() {
		turn++;
	}
	/**
	 * increments the turn counter buy the amount inputed
	 * @param num
	 */
	public void playTurns(int num) {
		turn += num;
	}
	/**
	 * moves the current city to a new city based off the index
	 * @param cityIndex the index of the city in the list
	 */
	public void visitCity(int cityIndex) {
		currentCity = cities[cityIndex];
		if (team.includesType(Type.ROGUE)) {
			for (int i = 0; i < 4; i++) {
				currentCity.setDiscovered(i);
			}
		}
	}
	/**
	 * returns the maximum index of the cities that can be visited
	 * @return
	 */
	public int getPossibleCityIndices() {
		if (currentCity.isComplete()) {
			return currentCity.getLevel();
		} else {
			return currentCity.getLevel() - 1;
		}
	}
	/**
	 * takes in a hero and a item index and returns true if the item has been used, false otherwise
	 * hospitalizes the hero if they are healing
	 * @param hero the hero that the affect of the item will be applied to
	 * @param index the index of the item in the inventory
	 * @return
	 */
	public boolean useItem(Hero hero, Item item) {
		if (item.canUse(hero) && !hero.isDisabled()) {
			if (item instanceof ItemHealing) {
				hero.hospitalize(currentCity, turn);
			}
			item.use(hero);
			team.removeItem(item);
			return true;
		} else {
			return false;
		}
	}
	/**
	 * takes the item, adds it to the team inventory, removes it form the current cities shop,
	 * reduces the teams money by the price * modifier
	 * @param item the item to be added
	 * @param modifier the modifier it the items price
	 */
	public void buyItem(Item item) {
		double modifier = 1.0;
		ArrayList<Item> inventory = currentCity.getShopInventory();
		inventory.remove(item);
		currentCity.setShopInventory(inventory);
		team.decreaseMoney(item.getPrice(modifier));
		team.getItems().add(item);
	}
	/**
	 * removes the item from the team inventory and adds the price modified by the sell price modifier
	 * to the team's money
	 * @param item item to be removed
	 */
	public void sellItem(Item item) {
		ArrayList<Item> inventory = currentCity.getShopInventory();
		inventory.add(item);
		currentCity.setShopInventory(inventory);
		team.removeItem(item);
		team.increaseMoney(item.getPrice(0.5));
	}
	/**
	 * chooses a random event and applies it, return the type
	 * @return
	 */
	public RandomEvent randomEvent() {
		int num = Constants.random.nextInt(Constants.randomEventChance * 2);
		RandomEvent event;
		switch (num) {
			case 0:
				addRandomItem();
				event = RandomEvent.values()[num];
				break;
			case 1:
				removeRandomItem();
				event = RandomEvent.values()[num];
				break;
			default:
				event = RandomEvent.values()[2];
				break;
		}
		return event;
	}
	/**
	 * adds a random item to the team's inventory
	 */
	public void addRandomItem() {
		int num = Constants.random.nextInt(3);
		switch (num) {
			case 0:
				team.getItems().add(new ItemHealing(1));
				break;
			case 1:
				team.getItems().add(new ItemPowerup());
				break;
			case 2:
				team.getItems().add(new ItemMap(currentCity));
				break;
		}
	} 
	/**
	 * removes a random item for the team, if they have any
	 */
	public void removeRandomItem() {
		if (team.getItems().size() > 0) {
			team.getItems().remove(Constants.random.nextInt(team.getItems().size()));
		}
	}
	
	public int getTurn() {
		return turn;
	}
	
	public City getCity() {
		return currentCity;
	}
	
	public City[] getCities() {
		return cities;
	}
	
	public Team getTeam() {
		return team;
	}
	/**
	 * returns a the toString of all the cities in the game
	 * @return
	 */
	public String getCityOverview() {
		String string = "Cities in game are: ";
		for (City city : cities) {
			string = string + "\n" + city;
		}
		return string;
	}
	
	@Override
	public String toString() {
		return "Turn number " + turn + "\nYour Team is in a local inn of the city of " + currentCity.getName() + "\n\n" + team;
	}
}
