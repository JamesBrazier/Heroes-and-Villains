package Main;
import java.util.Random;
/**
 * @author jbr185 & tjc107
 * stores all the constants for the balancing of the game
 * and the random number generator
 */
public class Constants {
	//Random
	public static Random random;
	public static final int randomEventChance = 10; //1 out of value
	
	//Economy
	public static final int startingMoney = 100;
	public static final int powerupCost = 50;
	public static final int minHealingCost = 25;
	public static final int mapCost = 50;
	public static final double lowerCostModifier = 0.75;
	
	//Team
	public static final int teamSize = 3;
	public static final int inventorySize = 10;
	
	//Healing
	public static final int defaultRecoveryRate = 5;
	public static final int fastRecoveryRate = 3;
	public static final int defaultMaxHealth = 100;
	public static final int healthPerItem = 25;
	public static final int maxHealth = 150;
	
	//Damage
	public static final int defaultDamage = 20;
	public static final int damagePerItem = 5;
	public static final int maxDamage = 40;
	
	//Defense
	public static final int defaultDefense = 0;
	public static final int defensePerItem = 10;
	public static final int maxDefense = 20;
	
	//Villain
	public static final int villainHealthPerLevel = 20;
	public static final int villainBaseDamage = 20;
	public static final int villainDamagePerLevel = 5;
	public static final int villainBountyPerLevel = 25;
	public static final int villainBossModifier = 0;
	public static final int villainBaseHealth = 60;

	//MiniGames
	public static final int GTNHighestPossibleNumber = 20;
	public static final int GTNNumberTries = 4;
	public static final int goodAtGTNModifier = 2;
	public static final int goodAtDRModifier = 1;
	
	//Name Lists (healing item names length also affects highest healing level)
	public static final String[] healingItemNames = {"Basic Healing", "Average Healing", "Strong Healing", "Epic Healing"};
	public static final String[] cityNames = {"Seyda Neen", "Balmora", "Gnisis", "Ald'ruhn", "Sadrith Mora", "Vivec"};
	public static final String[] possibleNames = {"Sir Finklestein", "Thanos", "Alduin", "Dagoth Ur", "Donald Trump", "Larry"};
	public static final String[] possibleTaunts = {"Come at me", "ahhh! a fight!", "Great, I've always wanted you dead!", "You can't win!", "Fight me!", "You'll never win!"};
}
