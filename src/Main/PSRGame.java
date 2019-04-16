package Main;
/**
 * @author tjc107
 */
public class PSRGame extends MiniGame {
	
	private PSR villainPSR;
	private boolean randomPSR = true; // For tests
	private PSR heroPSR = null;
	
	public PSRGame(Hero aHero, Villain aVillain) {
		super(aHero, aVillain);
	}
	
    /**
	 * Makes a random choice from: Paper, Scissors, Rock and
	 * returns the corrosponding PSR enum. This is how the villain 
	 * decides what to do in a game of PSR.
	 * @return A PSR enum.
	 */
	private PSR randomPSR() {
		if (randomPSR) {
			int randInt = Constants.random.nextInt(3);
			PSR randPSR = null;
			switch(randInt) {
			case 0:
				randPSR = PSR.PAPER;
				break;
			case 1:
				randPSR = PSR.SCISSORS;
				break;
			case 2:
				randPSR = PSR.ROCK;
				break;
			default:
				break;
			}
			return randPSR;
		}
		else 
			return villainPSR;
	}
	/**
	 * Plays a game of PSR and returns whether the Hero or Villain won
	 * @param heroPSR PSR enum which represents the heroes move in the PSR game.
	 * @param villainPSR PSR enum which represents the villains move in the PSR game.
	 * @return A RoundResult where RoundResult.WIN is when the hero beat the villain, RoundResult.LOSE is
	 * when the villain won and RoundResult.DRAW is when the hero and villain made the same choice (draW)
	 */
	private RoundResult psrResult(PSR heroPSR, PSR villainPSR) {
		RoundResult result = null;
		
		if(heroPSR == villainPSR)
			result = RoundResult.DRAW;
		else {
			switch(heroPSR) {
			case PAPER:
				switch(villainPSR) {
				case ROCK:
					result = RoundResult.WIN;
					break;
				case SCISSORS:
					result = RoundResult.LOSE;
					break;
				default:
					break;
				}
				break;
			case ROCK:
				switch(villainPSR) {
				case PAPER:
					result = RoundResult.LOSE;
					break;
				case SCISSORS:
					result = RoundResult.WIN;
					break;
				default:
					break;
				}
				break;
			case SCISSORS:
				switch(villainPSR) {
				case PAPER:
					result = RoundResult.WIN;
					break;
				case ROCK:
					result = RoundResult.LOSE;
					break;
				default:
					break;
				}
				break;
			default:
				break;
			}		
		}
		return result;
	}
	
	public void runGame() {
		villainPSR = randomPSR();
		if(heroPSR != null) {
			RoundResult result = psrResult(heroPSR, villainPSR);
			setLastRound(result);
		} else {
			setLastRound(null);
		}
	}
	
	public void setHeroPSR(PSR newHeroPSR) {
		heroPSR = newHeroPSR;
	}
	
	public PSR getHeroPSR() {
		return heroPSR;
	}
	
	public PSR getVillainPSR() {
		return villainPSR;
	}

	public void setVillainPSR(PSR villainPSR) {
		this.villainPSR = villainPSR;
	}

	// For tests
	public boolean isRandomPSR() {
		return randomPSR;
	}

	// For tests
	public void setRandomPSR(boolean randomPSR) {
		this.randomPSR = randomPSR;
	}
}