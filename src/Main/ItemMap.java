package Main;
/**
 * @author jbr185
 */
public class ItemMap extends Item {
	
	private City city;
	
	/**
	 * generates a map of the inputed city
	 * @param itemCity the city the map is of
	 */
	public ItemMap(City itemCity) {
		super("Map of " + itemCity.getName(), Constants.mapCost);
		city = itemCity;
	}
	/**
	 * generates a specific state of a map object
	 * @param itemName the name of the item
	 * @param itemPrice the cost of the item
	 * @param itemCity the city the map is of
	 */
	public ItemMap(String itemName, int itemPrice, City itemCity) {
		super(itemName, itemPrice);
		city = itemCity;
	}

	@Override
	/**
	 * sets the map's city to fully discovered
	 * @param hero the hero that uses the item
	 * @return returns false if the city is already explored
	 */
	public void use(Hero hero) {
		for (int i = 0; i < 4; i++) {
			city.setDiscovered(i);
		}
	}
	/**
	 * checks whether the city has already been discovered
	 */
	public boolean canUse(Hero hero) {
		for (int i = 0; i < 4; i++) {
			if (!city.getDiscovered()[i]) {
				return true;
			}
		}
		return false;
	}
	
	public String getCityName() {
		return city.getName();
	}
}
