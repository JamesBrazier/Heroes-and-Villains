package Main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
/**
 * @author jbr185
 */
public class City {
	
	private String name;
	private Location[] locations = Location.values();
	private boolean[] discovered = {false, false, false, false};
	private ArrayList<Item> shopInventory = new ArrayList<Item>();
	private Villain villain;
	private int level;
	
	/**
	 * Generates a new city object based of the inputed level
	 * @param cityName the name of the city
	 * @param cityLevel the level of the city
	 * @param finalCity whether the city should have a super villain generated instead
	 */
	public City(String cityName, int cityLevel, boolean finalCity) {
		name = cityName;
		level = cityLevel;
		Collections.shuffle(Arrays.asList(locations));
		if (finalCity) {
			villain = new SuperVillain();
		} else {
			villain = new Villain(level);
		}
		shopInventory.add(new ItemHealing(1));
		for (int i = level; i > 0; i--) {
			shopInventory.add(new ItemHealing(i));
			shopInventory.add(new ItemPowerup());
		}
		shopInventory.add(new ItemMap(this));
		shopInventory.sort((a, b) -> a.getPrice() - b.getPrice());
	}
	/**
	 * Generates a city in a specific state based of the inputed values
	 * @param cityName name of the city
	 * @param cityLocations the list of the positions of the locations in the city
	 * @param cityDiscovered a list of which locations that are discovered
	 * @param cityShopInventory the list of items in the shop
	 * @param cityVillain the city's local villain
	 */
	public City(String cityName, Location[] cityLocations, boolean[] cityDiscovered, ArrayList<Item> cityShopInventory, Villain cityVillain, int cityLevel) {
		name = cityName;
		locations = cityLocations;
		discovered = cityDiscovered;
		shopInventory = cityShopInventory;
		villain = cityVillain;
		level = cityLevel;
	}
	/**
	 * gets the location based of the inputed direction, 0: north, 1: east, 2: west, 3: south
	 * @param direction the index value for the list
	 * @return the location enum value
	 */
	public Location getLocation(int direction) {
		return locations[direction];
	}
	/**
	 * @return returns true if the villain is defeated
	 */
	public boolean isComplete() {
		if (villain.getDefeated()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean[] getDiscovered() {
		return discovered;
	}
	
	public String getName() {
		return name;
	}
	
	public int getLevel() {
		return level;
	}
	
	public Villain getVillain() {
		return villain;
	}
	
	public ArrayList<Item> getShopInventory() {
		return shopInventory;
	}
	
	public void setShopInventory(ArrayList<Item> newInventory) {
		shopInventory = newInventory;
	}
	
	public void setDiscovered(int index) {
		discovered[index] = true;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	@Override
	public String toString() {
		String string = "The city of " + name + " with locations:\n";
		String[] compass = {"North", "West", "East", "South"};
		for (int i = 0; i < 4; i++) {
			string = string + compass[i] + ": " + locations[i] + " which is ";
			if (discovered[i]) {
				string = string + locations[i] + "\n";
			} else {
				string = string + "unexplored\n";
			}
		}
		string = string + "With the local shop stocking: " + shopInventory + "\nwe are here for " + villain + "\n";
		return string;
	}
}
