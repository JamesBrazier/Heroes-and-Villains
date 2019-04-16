package Main;
import java.util.ArrayList;
import java.util.Collection;
/**
 * @author tjc107
 */
public class Team {
	private ArrayList<Hero> heroes = new ArrayList<Hero>();
	private ArrayList<Item> items = new ArrayList<Item>();
	private int money;
	
	/**
	 *Constructor that generates a new team object.
	 */
	public Team() {
		money = Constants.startingMoney;
	}
	/**
	 *Creates a new team object and sets it's state based on the arguments passed 
	 *to the constructor.
	 *@param Heros the ArrayList of heros in the team
	 *@param Items the inventory of the team
	 *@param Money the current amount of money the team has
	 */
	public Team(ArrayList<Hero> newHeroes, ArrayList<Item> newItems, int newMoney) {
		heroes = newHeroes;
		items = newItems;
		money = newMoney;
	}
	
	public ArrayList<Hero> getHeroes() {
		return heroes;
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	/**
	 * Takes a team name and generates a new team object.
	 * @param teamName The name of the team.
	 * @return true if the team's money supply is greater than or equal to amount
	 */
	public boolean canSpend(int amount) {
		return money >= amount;
	}
	/**
	 * Takes away a certain amount of the team's money (e.g. when it is spent)
	 * @param amount The amount of money to take away from the team
	 */
	public void decreaseMoney(int amount) {
		money = Math.max(money - Math.max(amount, 0), 0);
	}
	
	public int getMoney() {
		return money;
	}
	
	public void setMoney(int amount) {
		money = amount;
	}
	/**
	 * Increases the team's money supply by amount
	 * @param amount The amount to increase the team's money supply by
	 */
	public void increaseMoney(int amount) {
		money += amount;
	}
	/**
	 * Adds a hero to the team.
	 * @param hero Hero to add to the team.
	 */
	public void addHero(Hero hero) {
		if (heroes.size() < 3) {
			heroes.add(hero);
		} else {
			//TODO throw exception?
		}
	}

	public void setHeroes(ArrayList<Hero> newHeroes) {
		heroes = newHeroes;
	}
	/**
	 * returns whether the whole team is dead
	 * @return boolean
	 */
	public boolean isDead() {
		for (Hero hero : heroes) {
			if (!hero.isDead()) {
				return false;
			}
		}
		return true;

	}
	/**
	 * Checks if at least one hero of type 'type' is in the team. If at least one hero
	 * of the right type is found, returns true.
	 * @param type Type of hero to check for in the team
	 * @return True if at least one hero in the team is of type 'type'. Otherwise false.
	 */
	public boolean includesType(Type type) {
		for (Hero hero : heroes) {
			if (hero.getType() == type) {
				return true;
			}
		}
		return false;
	}
	
	public void removeHero(Hero hero) {
		heroes.remove(hero);
	}
	
	public void removeItem(Item item) {
		items.remove(item);
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	public void setInventory(ArrayList<Item> newItems) {
		items = newItems;
	}
	
	public String toString() {
		String string = "Your Team:\n";
		for (Hero hero : heroes) {
			string = string + "\n" + hero + "\n";
		}
		string = string + "\nYou have: ";
		if (items.isEmpty()) {
			string = string + "no items";
		} else {
			string = string + items;
		}
		return string + "\n\nYou have " + money + " gold pieces";
	}
}
