package Testing;

import static org.junit.Assert.*;

import java.util.Random;

import Main.Constants;
import Main.DRGame;
import Main.Hero;
import Main.RoundResult;
import Main.Type;
import Main.Villain;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DRGameTest {
	private Hero testHero;
	private Hero goodAtDRTestHero;
	private Villain testVillain;
	private DRGame testDRGame;
	
	// Seeds:
	// 0xaaaa - random.nextInt(6) == 1
	// 0xaaab - random.nextInt(6) == 5
	// 0xaaad - 2
	// 0xaaae - 3
	// 0xaaba - 4
	// 0xaade - 0
	private Random newRandom() {
		return new Random(0xaacf);
	}
	
	@BeforeClass
	public static void beforeClass() {
		Constants.random = new Random();
	}
	
	@Before
	public void beforeEachTest() {
		testHero = new Hero("TestHero", Type.WIZARD);
		goodAtDRTestHero = new Hero("TestHeroDR", Type.ROGUE);
		testVillain = new Villain(5);
	}
	
	@Test
	public void testWin() {
		testDRGame = new DRGame(testHero, testVillain);
		do {
			testDRGame.runGame();
		} while(!(testDRGame.getHeroDiceRoll() > testDRGame.getVillainDiceRoll()));
		assertEquals(testDRGame.getLastRound(), RoundResult.WIN);
	}
	
	@Test
	public void testDraw() {
		testDRGame = new DRGame(testHero, testVillain);
		do {
			testDRGame.runGame();
		} while(!(testDRGame.getHeroDiceRoll() == testDRGame.getVillainDiceRoll()));
		assertEquals(testDRGame.getLastRound(), RoundResult.DRAW);
	}
	
	@Test
	public void testLose() {
		testDRGame = new DRGame(testHero, testVillain);
		do {
			testDRGame.runGame();
		} while(!(testDRGame.getHeroDiceRoll() < testDRGame.getVillainDiceRoll()));
		assertEquals(testDRGame.getLastRound(), RoundResult.LOSE);
	}
	
	private int numWins(int numTrials, Hero newTestHero) {
		int wins = 0;
		testDRGame = new DRGame(newTestHero, testVillain);
		// Seed random number generator
		Constants.random = newRandom();
		
		for(int i=0;i<numTrials;i++) {
			testDRGame.runGame();
			if(testDRGame.getLastRound() == RoundResult.WIN) {
				wins += 1;
			}
		}
		return wins;
	}
	
	private int numDraws(int numTrials, Hero newTestHero) {
		int draws = 0;
		testDRGame = new DRGame(newTestHero, testVillain);
		// Seed random number generator
		Constants.random = newRandom();
		
		for(int i=0;i<numTrials;i++) {
			testDRGame.runGame();
			if(testDRGame.getLastRound() == RoundResult.DRAW) {
				draws += 1;
			}
		}
		return draws;
	}
	
	@Test
	public void testDiceRolls() {
		// Expected number of wins the hero should win according to probability if
		// one plays 720 games (RNG is seeded so results are reproducible)
		int trials = 720;
		int[] expected_wins = {270, 330};
		int[] expected_draws = {100, 140};
		assertTrue(numWins(trials, testHero) >= expected_wins[0]);
		assertTrue(numWins(trials, testHero) <= expected_wins[1]);
		assertTrue(numDraws(trials, testHero) >= expected_draws[0]);
		assertTrue(numDraws(trials, testHero) <= expected_draws[1]);
	}
	
	@Test
	public void testDiceRollsBetterHero() {
		// Expected number of wins the hero should win according to probability if
		// one plays 720 games (RNG is seeded so results are reproducable)
		int trials = 720;
		int[] expected_wins = {390, 450};
		int[] expected_draws = {80, 120};
		assertTrue(numWins(trials, goodAtDRTestHero) >= expected_wins[0]);
		assertTrue(numWins(trials, goodAtDRTestHero) <= expected_wins[1]);
		assertTrue(numDraws(trials, goodAtDRTestHero) >= expected_draws[0]);
		assertTrue(numDraws(trials, goodAtDRTestHero) <= expected_draws[1]);
	}

	/* Testing constructor is unnecessary because constructor does not
	 * override class from parent.
	@Test
	public void testDRGameHeroVillain() {
		fail("Not yet implemented");
	}*/

}
