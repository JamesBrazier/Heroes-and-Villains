package Testing;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Main.Constants;
import Main.GNGame;
import Main.Hero;
import Main.RoundResult;
import Main.Type;
import Main.Villain;

public class GNGameTest {
	private Hero testHero;
	private Hero goodAtGNTestHero;
	private Villain testVillain;
	private GNGame testGNGame;
	private GNGame testGNGameGood;
	private Random random = new Random(0xaaaa);
	
	@BeforeClass
	public static void beforeClass() {
		Constants.random = new Random();
	}
	
	@Before
	public void setup() {
		testHero = new Hero("TestHero", Type.ROGUE);
		goodAtGNTestHero = new Hero("TestHeroPSR", Type.WIZARD);
		testVillain = new Villain(5);
		testGNGame = new GNGame(testHero, testVillain);
		testGNGameGood = new GNGame(goodAtGNTestHero, testVillain);
		testGNGame.setCorrectNumber(5);
		testGNGameGood.setCorrectNumber(5);
	}
	
	@Test
	public void testConstructor() {
		assertEquals(testGNGame.getNumGuesses(), 0);
		assertEquals(testGNGame.getMaxGuesses(), Constants.GTNNumberTries);
		assertEquals(testGNGameGood.getMaxGuesses(), Constants.GTNNumberTries + Constants.goodAtGTNModifier);
		assertNotNull(testGNGame.getCorrectNumber());
	}

	@Test
	public void testNewGuess() {
		testGNGame.newGuess(4);
		assertEquals(testGNGame.getLastGuess(), 4);
		assertEquals(testGNGame.getLastRound(), null);
	}

	@Test
	public void testIsHigher() {
		testGNGame.newGuess(6);
		assertEquals(testGNGame.isHigher(), true);
		testGNGame.newGuess(4);
		assertEquals(testGNGame.isHigher(), false);
		testGNGame.newGuess(5);
		assertEquals(testGNGame.isHigher(), false);
	}

	@Test
	public void testIsCorrect() {
		testGNGame.newGuess(6);
		assertEquals(testGNGame.isHigher(), true);
		testGNGame.newGuess(4);
		assertEquals(testGNGame.isHigher(), false);
		testGNGame.newGuess(5);
		assertEquals(testGNGame.isHigher(), false);
	}
	
	@Test
	public void testIsLower() {
		testGNGame.newGuess(6);
		assertEquals(testGNGame.isHigher(), true);
		testGNGame.newGuess(4);
		assertEquals(testGNGame.isHigher(), false);
		testGNGame.newGuess(5);
		assertEquals(testGNGame.isHigher(), false);
	}

	@Test
	public void testRunGame() {
		// Test when no guess has been made by the hero (at all)
		testGNGame.runGame();
		assertEquals(testGNGame.getLastRound(), RoundResult.DRAW);
		assertEquals(testGNGame.getNumGuesses(), 1);
	}
	
	@Test
	public void testRunGameLose() {
		testGNGame.setMaxGuesses(3);
		testGNGame.newGuess(0);
		testGNGame.runGame();
		assertEquals(testGNGame.getLastRound(), RoundResult.DRAW);
		assertEquals(testGNGame.getNumGuesses(), 1);
		// Hero loses
		testGNGame.newGuess(6);
		testGNGame.runGame();
		assertEquals(testGNGame.getLastRound(), RoundResult.DRAW);
		assertEquals(testGNGame.getNumGuesses(), 2);
		testGNGame.newGuess(10);
		testGNGame.runGame();
		assertEquals(testGNGame.getLastRound(), RoundResult.DRAW);
		assertEquals(testGNGame.getNumGuesses(), 3);
		testGNGame.newGuess(15);
		testGNGame.runGame();
		assertEquals(testGNGame.getLastRound(), RoundResult.LOSE);
		assertEquals(testGNGame.getNumGuesses(),4);
	}
	
	@Test
	public void testRunGameWin() {
		testGNGame.setMaxGuesses(3);
		testGNGame.newGuess(5);
		testGNGame.runGame();
		assertEquals(testGNGame.getLastRound(), RoundResult.WIN);
		assertEquals(testGNGame.getNumGuesses(), 1);
	}
	
	@Test
	public void testRunGameBoundary() {
		testGNGame.setMaxGuesses(3);
		testGNGame.newGuess(7);
		testGNGame.runGame();
		assertEquals(testGNGame.getLastRound(), RoundResult.DRAW);
		assertEquals(testGNGame.getNumGuesses(), 1);
		
		testGNGame.newGuess(8);
		testGNGame.runGame();
		assertEquals(testGNGame.getLastRound(), RoundResult.DRAW);
		assertEquals(testGNGame.getNumGuesses(), 2);
		
		testGNGame.newGuess(5);
		testGNGame.runGame();
		assertEquals(testGNGame.getLastRound(), RoundResult.WIN);
		assertEquals(testGNGame.getNumGuesses(), 3);
	}
	
	@Test
	public void testRunGameInvalid() {
		testGNGame.setMaxGuesses(3);
		testGNGame.newGuess(7);
		testGNGame.runGame();
		testGNGame.newGuess(8);
		testGNGame.runGame();
		testGNGame.newGuess(5);
		testGNGame.runGame();
		assertEquals(testGNGame.getLastRound(), RoundResult.WIN);
		assertEquals(testGNGame.getNumGuesses(), 3);
		
		// Any future guesses by the hero should not change the game state (even if guess is correct)
		testGNGame.newGuess(6);
		testGNGame.runGame();
		assertEquals(testGNGame.getLastRound(), RoundResult.WIN);
		
		testGNGame.setLastRound(RoundResult.LOSE);
		testGNGame.newGuess(5);
		testGNGame.runGame();
		assertEquals(testGNGame.getLastRound(), RoundResult.LOSE);
	}
}
