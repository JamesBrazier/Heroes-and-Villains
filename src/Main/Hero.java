package Main;
/**
 * @author tjc107
 */
public class Hero {
	private String name;
	private Type type;
	private boolean dead = false;
	private int health;
	private int maxHealth;
	private int recoveryRate;
	private int damage;
	private int defense;
	private int healTurn;
	private City hospitalCity = null;
	
	/**
	 * Takes the heroes name and the type (class) of the hero (e.g. Wizard, 
	 * Rogue, ect...) Initializes the attributes of the hero. These include the 
	 * maxHealth, recoveryRate, damage and defense (damage mitigation) of the 
	 * hero.
	 * @param Name of the hero
	 * @param Type (class) of the hero
	 */
	public Hero(String heroName, Type heroType) {
		name = heroName;
		type = heroType;
		maxHealth = Constants.defaultMaxHealth;
		recoveryRate = Constants.defaultRecoveryRate;
		damage = Constants.defaultDamage;
		defense = Constants.defaultDefense;
		switch(heroType) {
		case ROGUE:
			//is a map
			//good at dice rolling
			break;
		case SWORDSMAN:
			damage += Constants.damagePerItem * 2;
			recoveryRate = Constants.fastRecoveryRate;
			break;
		case PALADIN:
			defense += Constants.defensePerItem;
			maxHealth += Constants.healthPerItem;
			break;
		case WIZARD:
			//good at number guessing
			maxHealth -= Constants.healthPerItem;
			break;
		}
		health = maxHealth;
	}
	/**
	 * Explicitly creates a new hero in a defined state based off all the inputed values.
	 * @param Name of the hero
	 * @param Type (special ability) of the hero
	 * @param Dead whether the hero had died
	 * @param Health the current health of the hero
	 * @param MaxHealth the maximum health of the hero
	 * @param RecoveryRate the turns the hero takes to heal
	 * @param HospitalCity the current city's hospital the hero is in, null if none
	 */
	public Hero(String heroName, Type heroType, boolean isDead, int currentHealth, int heroMaxHealth, int heroRecoveryRate, City currentHospitalCity) {
		name = heroName;
		type = heroType;
		dead = isDead;
		health = currentHealth;
		maxHealth = heroMaxHealth;
		recoveryRate = heroRecoveryRate;
		hospitalCity = currentHospitalCity;
	}
	/**
	 * Makes a hero take a certain amount of damage. If the hero's health goes under 0, the hero
	 * is marked as dead.
	 * @param amount The amount of health points to subtract from the Hero.
	 */
	public void takeDamage(int amount) {
		if(amount < 0)
			return;
		health = Math.max(0, health - Math.max(0, amount - defense));
		if(health <= 0) {
			dead = true;
		}
	}
	/**
	 * Restores a certain amount of the hero's HP. The hero's health will never go above it's
	 * max health.
	 * @param amount The amount of health points to give the Hero.
	 */
	public void heal(int amount) {
		health = Math.min(health + amount, maxHealth);
	}
	
	/**
	 * Puts the hero in a cities hospital to apply a healing item (over time).
	 * @param city The city to place hero in.
	 * @param healTurn The turn that the hero should exit hospital.
	 */
	public void hospitalize(City city, int healTurn) {
		hospitalCity = city;
		this.healTurn = healTurn;
	}
	/**
	 * takes the hero out of the hospital and returns true, if they have been 
	 * in there for the amount of turns equal to their recovery rate and if 
	 * the city is the city which hospital they are in, or false otherwise
	 * @param city the current city the team is in
	 * @param turn the current turn
	 * @return
	 */
	public  boolean unHospitalize(City city, int turn) {
		if (hospitalCity == city && (turn - healTurn) >= recoveryRate) {
			hospitalCity = null;
			healTurn = 0;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks whether a hero is currently with the main party, is in a hospital or dead.
	 * @return true if the hero is in hospital or is marked as dead. Returns false otherwise
	 */
	public boolean isDisabled() {
		return hospitalCity != null || dead;
	}
	
	/**
	 * Returns true if this hero is dead.
	 * @return true if the hero is dead. Otherwise false 
	 */
	public boolean isDead() {
		return dead;
	}
	
	public String getName() {
		return name;
	}
	
	public Type getType() {
		return type;
	}
	
	public int getHealth() {
		return health;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getRecoveryRate() {
		return recoveryRate;
	}
	
	public City getHospitalCity() {
		return hospitalCity;
	}
	
	public void setDead(boolean isDead) {
		dead = isDead;
	}
	
	public void setMaxHealth(int newMaxHealth) {
		maxHealth = newMaxHealth;
	}
	
	public void setRecoveryRate(int newRecoveryRate) {
		recoveryRate = newRecoveryRate;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getDefense() {
		return defense;
	}
	
	public int getHealTurn() {
		return healTurn;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void setDamage(int newDamage) {
		damage = newDamage;
	}
	
	public void setDefense(int newDefense) {
		defense = newDefense;
	}
	
	public String toString() {
		return type + " " + name;
	}
}
