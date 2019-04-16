package Main;
/**
 * @author tjc107
 */
public class GNGame extends MiniGame {
	// Number is from 1 to Constants.GTNHighestPossibleNumber (inclusive)
	private int correctNumber;
	private int maxGuesses = Constants.GTNNumberTries;
	private int lastGuess = -1;
	private int numGuesses = 0;
	
	/**
	 * Constructor for guess the number game. If a hero who is good at guess the number game
	 * is participating in this minigame, the player will get extra guesses in each game against
	 * the villain.
	 * @param aHero Hero that is playing this minigame
	 * @param aVillain Villain that is playing this minigame
	 */
	public GNGame(Hero aHero, Villain aVillain) {
		super(aHero, aVillain);
		if(aHero.getType() == Type.WIZARD)
			maxGuesses += Constants.goodAtGTNModifier;
		correctNumber = Constants.random.nextInt(Constants.GTNHighestPossibleNumber) + 1;
	}
	/**
	 * Sets the guess made by the hero. Call runGame() after running this to see if this guess was the
	 * correct one.
	 * @param guess The guess made by the hero in one round of guess the number.
	 */
	public void newGuess(int guess) {
		lastGuess = guess;
	}
	/**
	 * Returns true the last guess made in the guess the number game was higher than the correct number
	 * @return true if the heroes last guess was higher than the correct number
	 */
	public boolean isHigher() {
		return lastGuess > correctNumber;
	}
	/**
	 * Returns true the last guess made in the guess the number game was lower than the correct number
	 * @return true if the heroes last guess was lower than the correct number
	 */
	public boolean isLower() {
		return lastGuess < correctNumber;
	}
	/**
	 * Returns true if the last guess made by the player was the correct one. Returns false otherwise.
	 * @return true if the heroes last guess was equal to the correct number
	 */
	public boolean isCorrect() {
		return lastGuess == correctNumber;
	}
	public int getCorrectNumber() {
		return correctNumber;
	}
	public void runGame() {
		// If the right guess has been made, or the max number of guesses have already been made,
		// future guesses should not change the game state
		if(numGuesses > maxGuesses || getLastRound() == RoundResult.WIN)
			return;
			
		if (numGuesses == maxGuesses) {
			setLastRound(RoundResult.LOSE);
		} else if (isCorrect()) {
			setLastRound(RoundResult.WIN);
		} else {
			setLastRound(RoundResult.DRAW);
		}
		numGuesses += 1;
	}

	public void setCorrectNumber(int newCorrectNumber) {
		correctNumber = newCorrectNumber;
	}

	public int getMaxGuesses() {
		return maxGuesses;
	}

	public void setMaxGuesses(int newMaxGuesses) {
		maxGuesses = newMaxGuesses;
	}

	public int getLastGuess() {
		return lastGuess;
	}

	public void setLastGuess(int newLastGuess) {
		lastGuess = newLastGuess;
	}

	public int getNumGuesses() {
		return numGuesses;
	}

	public void setNumGuesses(int newNumGuesses) {
		numGuesses = newNumGuesses;
	}
}
