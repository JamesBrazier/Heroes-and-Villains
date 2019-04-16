package Main;
/**
 * @author jbr185
 */
public class ItemHealing extends Item {
	
	private double amount;
	
	/**
	 * Generates a healing item off the given level
	 * @param level the power of the item
	 */
	public ItemHealing(int level) {
		level = Math.min(level, Constants.healingItemNames.length);
		setPrice(Constants.minHealingCost * level);
		amount = 0.25 * level;
		setName(Constants.healingItemNames[level - 1]);
	}
	/**
	 * Generates a healing item in a specific state
	 * @param itemName name of item
	 * @param itemPrice cost of the item
	 * @param itemAmount percentage of health healed
	 */
	public ItemHealing(String itemName, int itemPrice, float itemAmount) {
		super(itemName, itemPrice);
		amount = itemAmount;
	}
	
	@Override
	/**
	 * takes in a hero object and restores the percentage of health stated my the item
	 * @param hero the hero the health is restored to
	 */
	public void use(Hero hero) {
		double maxHealth = hero.getMaxHealth();
		Double healthRestored = maxHealth * amount;
		hero.heal(healthRestored.intValue());
	}
	/**
	 * checks if the hero can use the healing item
	 * @param hero
	 */
	public boolean canUse(Hero hero) {
		if (hero.getHealth() < hero.getMaxHealth()) {
			return true;
		} else {
			return false;
		}
	}
}
