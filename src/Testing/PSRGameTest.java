package Testing;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Main.Constants;
import Main.Hero;
import Main.PSRGame;
import Main.Type;
import Main.Villain;
import Main.PSR;
import Main.RoundResult;

public class PSRGameTest {
	private Hero testHero;
	private Hero goodAtPSRTestHero;
	private Villain testVillain;
	private PSRGame testPSRGame;
	private PSRGame testPSRGameGood;
	private Random random = new Random(0xaaaa);
	
	@BeforeClass
	public static void beforeClass() {
		Constants.random = new Random();
	}
	
	@Before
	public void beforeEachTest() {
		testHero = new Hero("TestHero", Type.SWORDSMAN);
		goodAtPSRTestHero = new Hero("TestHeroPSR", Type.WIZARD);
		testVillain = new Villain(5);
		testPSRGame = new PSRGame(testHero, testVillain);
		testPSRGameGood = new PSRGame(goodAtPSRTestHero, testVillain);
		testPSRGame.setRandomPSR(false);
	}
	
	@Test
	public void testNoPSRError() {
		testPSRGame = new PSRGame(testHero, testVillain);
		testPSRGame.runGame();
		assertNull(testPSRGame.getLastRound());
		// Raise exception?
	}
	
	private PSR randomPSR() {
		int randInt = random.nextInt(3);
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
	
	private void testGame(PSRGame testPSRGame, PSR HeroPSR, PSR VillainPSR, RoundResult expectedResult) {
		testPSRGame.setHeroPSR(HeroPSR);
		if(VillainPSR != null) {
			testPSRGame.setRandomPSR(false);
			testPSRGame.setVillainPSR(VillainPSR);	
		}
		testPSRGame.runGame();
		assertEquals(testPSRGame.getLastRound(), expectedResult);
	}
	
	// Game draws are done in a seperate test case to avoid redundant code
	@Test
	public void testPaper() {
		testGame(testPSRGame, PSR.PAPER ,PSR.ROCK, RoundResult.WIN);
		testGame(testPSRGame, PSR.PAPER, PSR.SCISSORS, RoundResult.LOSE);
	}
	
	@Test
	public void testScissors() {
		testGame(testPSRGame, PSR.SCISSORS, PSR.PAPER, RoundResult.WIN);
		testGame(testPSRGame, PSR.SCISSORS, PSR.ROCK, RoundResult.LOSE);
	}
	
	@Test
	public void testRock() {
		testGame(testPSRGame, PSR.ROCK, PSR.SCISSORS, RoundResult.WIN);
		testGame(testPSRGame, PSR.ROCK, PSR.PAPER, RoundResult.LOSE);
	}
	
	@Test
	public void testDraw() {
		for (PSR psr : PSR.values()) {
			testPSRGame.setHeroPSR(psr);
			testPSRGame.setVillainPSR(psr);
			testPSRGame.runGame();
			assertEquals(testPSRGame.getLastRound(), RoundResult.DRAW);
		}
	}
	
	@Test
	public void testProbabilities() {
		int numTrials = 900;
		int wins = 0, draws = 0;
		int[] expected_wins = {270, 330};
		int[] expected_draws = {270, 330};
		Constants.random = random;
		
		testPSRGame.setRandomPSR(true);
		for(int i=0;i<numTrials;i++) {
			testPSRGame.setHeroPSR(randomPSR());
			testPSRGame.runGame();
			switch(testPSRGame.getLastRound()) {
			case DRAW:
				draws += 1;
				break;
			case WIN:
				wins += 1;
				break;
			default:
				break;
			}
		}
		assertTrue(wins >= expected_wins[0]);
		assertTrue(wins <= expected_wins[1]);
		assertTrue(draws >= expected_draws[0]);
		assertTrue(draws <= expected_draws[1]);
	}

}
