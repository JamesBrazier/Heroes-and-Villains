package Testing;

import static org.junit.Assert.*;

import Main.MiniGame;
import Main.Type;
import Main.Hero;
import Main.Villain;
import Main.DRGame;
import Main.RoundResult;
import Main.Constants;

import org.junit.Before;
import org.junit.Test;

public class MiniGameTest {
	private MiniGame testMiniGame;
	private MiniGame testMiniGameMoreDamage;
	private MiniGame testMiniGameLessDamageTaken;
	private MiniGame testMiniGameWeak;
	
	private Hero testHero;
	private Hero testHeroMoreDamage;
	private Hero testHeroLessDamageTaken;
	
	private Villain testVillain;
	private Villain testVillainWeak;

	@Before
	public void beforeTest() {
		testHero = new Hero("Joan", Type.WIZARD);
		testHeroMoreDamage = new Hero("Joan", Type.SWORDSMAN);
		testHeroLessDamageTaken = new Hero("Joan", Type.PALADIN);
		
		testVillain = new Villain(5);
		testVillainWeak = new Villain(1);
		
		// DRGame does not override any methods of Minigame, so functionally it should be equivalent to
		// a minimal subclass of MiniGame
		testMiniGame = new DRGame(testHero, testVillain);
		testMiniGameMoreDamage = new DRGame(testHeroMoreDamage, testVillain);
		testMiniGameLessDamageTaken = new DRGame(testHeroLessDamageTaken, testVillain);
		testMiniGameWeak = new DRGame(testHeroLessDamageTaken, testVillainWeak);
		
		testHero.setMaxHealth(250);
		testHero.setHealth(250);
		testHeroMoreDamage.setMaxHealth(250);
		testHeroMoreDamage.setHealth(250);
		testHeroLessDamageTaken.setMaxHealth(250);
		testHeroLessDamageTaken.setHealth(250);
		
		testVillain.setMaxHealth(250);
		testVillain.setHealth(250);
		testVillainWeak.setMaxHealth(250);
		testVillainWeak.setHealth(250);
	}
	
	@Test
	public void testMiniGame() {
		assertEquals(testMiniGame.getHero(), testHero);
		assertEquals(testMiniGame.getVillain(), testVillain);
	}
	
	
	@Test
	public void testNormalMiniGameWin() {	
		testMiniGame.setLastRound(RoundResult.WIN);
		testMiniGame.resolveGameEffects();
		assertEquals(testHero.getHealth(), 250);
		assertEquals(testVillain.getHealth(), 250 - Constants.defaultDamage);
	}
	
	@Test
	public void testNormalMiniGameLose() {	
		testMiniGame.setLastRound(RoundResult.LOSE);
		testMiniGame.resolveGameEffects();
		assertEquals(testHero.getHealth(), 250 - testVillain.getDamage());
		assertEquals(testVillain.getHealth(), 250);
	}
	
	@Test
	public void testNormalMiniGameDraw() {
		testMiniGame.setLastRound(RoundResult.DRAW);
		testMiniGame.resolveGameEffects();
		assertEquals(testHero.getHealth(), 250);
		assertEquals(testVillain.getHealth(), 250);
	}
	
	@Test
	public void testNormalMiniGameUndecided() {
		testMiniGame.resolveGameEffects();
		assertEquals(testHero.getHealth(), 250);
		assertEquals(testVillain.getHealth(), 250);
	}

	@Test
	public void testMoreDamageMiniGameWin() {	
		testMiniGameMoreDamage.setLastRound(RoundResult.WIN);
		testMiniGameMoreDamage.resolveGameEffects();
		assertEquals(testHeroMoreDamage.getHealth(), 250);
		assertEquals(testVillain.getHealth(), 250 - (Constants.defaultDamage + Constants.damagePerItem * 2));
	}
	
	@Test
	public void testLessDamageTakenMiniGameWin() {	
		testMiniGameLessDamageTaken.setLastRound(RoundResult.LOSE);
		testMiniGameLessDamageTaken.resolveGameEffects();
		assertEquals(testHeroLessDamageTaken.getHealth(), 250 - (testVillain.getDamage() - 
				Constants.defensePerItem * 2));
		assertEquals(testVillain.getHealth(), 250);
	}
	@Test
	public void testNegativeVillainDamage() {
		testVillainWeak.setDamage(0);
		testMiniGameWeak.setLastRound(RoundResult.LOSE);
		testMiniGameWeak.resolveGameEffects();
		// If the Villain is extremely weak (or constants unbalanced) and a hero is very strong
		// negative damage could be done to the hero. This should not increase or decrease the 
		// hero's health
		assertEquals(testHeroLessDamageTaken.getHealth(), 250);
		assertEquals(testVillainWeak.getHealth(), 250);
	}
}
