package Main;
/**
 * @author tjc107
 */
public class DRGame extends MiniGame {
	private int heroDiceRoll;
	private int villainDiceRoll;
	
	public DRGame(Hero aHero, Villain aVillain) {
		super(aHero, aVillain);
	}
	
	/**
	 * Generates a random number between 1 and 6 (simulating a dice roll)
	 * @return random number between 1 and 6.
	 */
	private int diceRoll() {
		return Constants.random.nextInt(6) + 1;
	}

	public int getHeroDiceRoll() {
		return heroDiceRoll;
	}
	
	public int getVillainDiceRoll() {
		return villainDiceRoll;
	}
	
	public void runGame() {
		heroDiceRoll = diceRoll();
		villainDiceRoll = diceRoll();
		
		if(getHero().getType() == Type.ROGUE) {
			heroDiceRoll = Math.min(6, heroDiceRoll + Constants.goodAtDRModifier);
		}
		
		if(heroDiceRoll > villainDiceRoll)
			setLastRound(RoundResult.WIN);
		else if (heroDiceRoll < villainDiceRoll)
			setLastRound(RoundResult.LOSE);
		else
			setLastRound(RoundResult.DRAW);
	}

	public void setHeroDiceRoll(int newHeroDiceRoll) {
		heroDiceRoll = newHeroDiceRoll;
	}

	public void setVillainDiceRoll(int newVillainDiceRoll) {
		villainDiceRoll = newVillainDiceRoll;
	}
}
