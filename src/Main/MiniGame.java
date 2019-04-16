package Main;
/**
 * @author tjc107
 */
public abstract class MiniGame {
	private Hero hero;
	private Villain villain;
	private RoundResult lastRound = null;
	
	/**
	 * Constructor for the minigame object
	 * @param aHero The hero that is playing this minigame.
	 * @param aVillain The villain that is playing this minigame.
	 */
	public MiniGame(Hero aHero, Villain aVillain) {
		hero = aHero;
		villain = aVillain;
	}
	/**
	 * Runs one round of the game and determines who won and lost. The result of the game is stored
	 * in the lastRound variable and can be accessed through the lastRound getter
	 */
	public abstract void runGame();
	/**
	 * If the last minigame played was won this function applies damage to the villain and increments the number 
	 * of times beaten if the villain's health drops below zero. If the game was lost then it applies damage to 
	 * the hero.
	 */
	public void resolveGameEffects() {
		// If statement to handle case where game has not been played yet
		if(lastRound == null)
			return;
		
		int amount;
		
		switch(lastRound) {
		case DRAW:
			break;
		case LOSE:
			amount = villain.getDamage();
			hero.takeDamage(amount);
			break;
		case WIN:
			amount = hero.getDamage();
			villain.takeDamage(amount);
			break;
		}
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero newHero) {
		hero = newHero;
	}

	public Villain getVillain() {
		return villain;
	}
	
	/**
	 * Gets the result of the last round (e.g. dice roll, PSR battle, number guess) of this 
	 * minigame that was last played.
	 * @return The result of the last round (e.g. a dice roll, a number guess, ect...
	 */
	public RoundResult getLastRound() {
		return lastRound;
	}

	public void setVillain(Villain newVillain) {
		villain = newVillain;
	}

	public void setLastRound(RoundResult newLastRound) {
		lastRound = newLastRound;
	}
}
