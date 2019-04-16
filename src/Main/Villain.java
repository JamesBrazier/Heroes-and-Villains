package Main;
import java.lang.Math;
import java.util.Arrays;
import java.util.Collections;
/**
 * @author tjc107
 */
public class Villain {
	
	private String name;
	private String taunt;
	private int level;
	private int health;
	private int maxHealth;
	private int bounty;
	private MiniGameType[] miniGames = {MiniGameType.DRGAME, MiniGameType.PSRGAME, MiniGameType.GNGAME};
	private int damage;
	private boolean defeated;
	
	/**
	 * Take in a level as an integer and bases the strength of the Villain on it, randomly 
	 * picks a name and taunt from a list of names and taunts, initializes the bounty 
	 * and randomly generates the list of games that the villain can play
	 * @param villainLevel How powerful the villain is.
	 */
	public Villain(int villainLevel) {
		name = randomName();
		taunt = randomTaunt();
		maxHealth = Constants.villainBaseHealth;
		health = maxHealth;
		level = villainLevel;
		bounty = villainLevel * Constants.villainBountyPerLevel;
		miniGames = randomMiniGames();
		damage = Constants.villainBaseDamage + villainLevel * Constants.villainDamagePerLevel;
		defeated = false;
	}
	/**
	 * generates a villain in a specified state based of the inputed values
	 * @param newName
	 * @param newTaunt
	 * @param currentHealth
	 * @param villainMaxHealth
	 * @param villainLevel
	 * @param newMiniGames
	 * @param isDefeated
	 */
	public Villain(String newName, String newTaunt, int currentHealth, int villainMaxHealth, int villainLevel, MiniGameType[] newMiniGames, boolean isDefeated) {
		name = newName;
		taunt = newTaunt;
		maxHealth = villainMaxHealth;
		health = currentHealth;
		level = villainLevel;
		bounty = villainLevel * Constants.villainBountyPerLevel;
		miniGames = newMiniGames;
		damage = Constants.villainBaseDamage + villainLevel * Constants.villainDamagePerLevel;
		defeated = isDefeated;
	}
	
	/**
	 * Returns a random taunt from the list of possible taunts that are in the constants class.
	 * @return A random taunt as a String
	 */
	private String randomTaunt() {
		int numPossibleTaunts = Constants.possibleTaunts.length;
		int i = Constants.random.nextInt(numPossibleTaunts);
		return Constants.possibleTaunts[i];
	}
	
	/**
	 * Returns a random name from the list of possible names that are stored in the constants class.
	 * @return A random name as a String
	 */
	private String randomName() {
		int numPossibleNames = Constants.possibleNames.length;
		int i = Constants.random.nextInt(numPossibleNames);
		return Constants.possibleNames[i];
	}
	
	private MiniGameType randomMiniGame() {
		MiniGameType miniGame = null;
		switch(Constants.random.nextInt(3)) {
		case 0:
			miniGame = MiniGameType.DRGAME;
			break;
		case 1:
			miniGame = MiniGameType.PSRGAME;
			break;
		case 2:
			miniGame = MiniGameType.GNGAME;
			break;
		}
		return miniGame;
	}
	
	/**
	 * Either returns an ArrayList of all minigames in the program or a single minigame (the villain's favourite)
	 * @return An ArrayList of minigames
	 */
	private MiniGameType[] randomMiniGames() {
		boolean hasFavourite = Constants.random.nextBoolean();
		if(hasFavourite)
			Arrays.fill(miniGames, randomMiniGame());
		else {
			Collections.shuffle(Arrays.asList(miniGames));
		}
		return miniGames;
	}
	
	/**
	 * Makes the villain take damage
	 * @param amount Amount of damage the villain will take.
	 */
	public void takeDamage(int amount) {
		health = Math.max(health - Math.max(amount, 0), 0);
		if(health == 0) {
			defeated = true;
		}
	}
	
	public String getName() {
		return name;
	}

	public String getTaunt() {
		return taunt;
	}

	public boolean getDefeated() {
		return defeated;
	}

	public void setDefeated(boolean isDefeated) {
		defeated = isDefeated;
	}

	public int getLevel() {
		return level;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int newHealth) {
		health = newHealth;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}

	public int getBounty() {
		return bounty;
	}

	public void setBounty(int newBounty) {
		bounty = newBounty;
	}

	public MiniGameType[] miniGames() {
		return miniGames;
	}

	public int getDamage() {
		return damage;
	}

	public void setMaxHealth(int newMaxHealth) {
		maxHealth = newMaxHealth;
	}
	
	public String toString() {
		return name + ": " + taunt + " (level " + level + ")\nHealth: " + health + "/" + maxHealth + "\nDamage: " + damage + " Bounty: " + bounty;
	}

	// For tests
	public void setDamage(int damage) {
		this.damage = damage;
	}
}
