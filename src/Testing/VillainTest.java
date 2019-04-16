package Testing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Main.Villain;
import Main.Constants;
import Main.MiniGameType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class VillainTest {
	private Villain testVillain;
	
	private Random newRandom() {
		return new Random(0xaaaa);
	}
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testVillain() {
		boolean allNames = true;
		boolean allTaunts = true;
		boolean villainAllMiniGames = false;
		boolean villainFavouriteMiniGames = false;
		ArrayList<MiniGameType> miniGameTypes;
		
		// seed random number generator
		Constants.random = newRandom();
		
		// Basic initialization tests
		testVillain = new Villain(1);
		assertEquals(testVillain.getDefeated(), false);
		
		// Check that strength and health scale with villain level. also check that the level is set correctly
		// and that health is initialized to maxHealth
		for(int i=1;i<20;i++) {
			testVillain = new Villain(i);
			assertEquals(testVillain.getLevel(), i);
			assertEquals(testVillain.getDamage(), Constants.villainBaseDamage + 
					i * Constants.villainDamagePerLevel);
			assertEquals(testVillain.getHealth(), Constants.villainBaseHealth);
			assertEquals(testVillain.getHealth(), testVillain.getMaxHealth());
		}
		
		// If enough villains are created, every single possible name and taunt that a villain can have should
		// appear at least once. The random number generator is seeded to make sure this is the case.
		Set<String> recordedNames = new HashSet<String>();
		Set<String> recordedTaunts = new HashSet<String>();
		
		
		for(int i=1;i<50;i++) {
			testVillain = new Villain(i);
			recordedNames.add(testVillain.getName());
			recordedTaunts.add(testVillain.getTaunt());
			miniGameTypes = new ArrayList<>(Arrays.asList(testVillain.miniGames()));
			
		}
		
		// If enough villains are created, there should be at least one villain who has a favourite game he plays
		// and at least one villain who plays anything. This test makes sure this is the case. The random number 
		// generator is seeded to make this reproducable. All villains must fall into one of the 2 catergories or
		// this test will fail.
		for(int i=0;i<20;i++) {
			testVillain = new Villain(i);
			miniGameTypes = new ArrayList<>(Arrays.asList(testVillain.miniGames()));
			if(miniGameTypes.contains(MiniGameType.DRGAME) && miniGameTypes.contains(MiniGameType.GNGAME) &&
					miniGameTypes.contains(MiniGameType.PSRGAME))
				villainAllMiniGames = true;
			else if(miniGameTypes.get(0) == miniGameTypes.get(1) && 
					miniGameTypes.get(1) == miniGameTypes.get(2))
				villainFavouriteMiniGames = true;
			else
				fail("Invalid villain MiniGame combinations created");
		}
		
		assertEquals(villainAllMiniGames, true);
		assertEquals(villainFavouriteMiniGames, true);
		
		for(String name : Constants.possibleNames) {
			if(!recordedNames.contains(name)) {
				allNames = false;
			}
		}
		
		for(String taunt : Constants.possibleTaunts) {
			if(!recordedTaunts.contains(taunt)) {
				allTaunts = false;
			}
		}
		
		assertEquals(allNames, true);
		assertEquals(allTaunts, true);
	}

	@Test
	public void testTakeDamage() {
		testVillain = new Villain(5);
		testVillain.setHealth(100);
		testVillain.setMaxHealth(100);
		// Make sure setHealth correctly set the health
		assertEquals(testVillain.getHealth(), 100);
		// Blue sky scenario
		testVillain.takeDamage(75);
		assertEquals(testVillain.getHealth(), 25);
		assertEquals(testVillain.getDefeated(), false);
		
		// Villain is defeated (amount of damage taken is greater than the amount of health the villain has left)
		testVillain = new Villain(5);
		testVillain.setMaxHealth(100);
		testVillain.setHealth(10);
		testVillain.takeDamage(15);
		assertEquals(testVillain.getDefeated(), true);
		
		// Boundary case
		testVillain = new Villain(5);
		testVillain.setMaxHealth(100);
		testVillain.setHealth(10);
		testVillain.takeDamage(10);
		assertEquals(testVillain.getDefeated(), true);
		
		// Zero damage taken should result in no change in the villain's health
		testVillain = new Villain(5);
		testVillain.setMaxHealth(100);
		testVillain.setHealth(20);
		testVillain.takeDamage(0);
		assertEquals(testVillain.getHealth(), 20);
		assertEquals(testVillain.getDefeated(), false);
		
		// Negative damage should do nothing
		testVillain = new Villain(5);
		testVillain.setMaxHealth(100);
		testVillain.setHealth(40);
		testVillain.takeDamage(-5);
		assertEquals(testVillain.getHealth(), 40);
		assertEquals(testVillain.getDefeated(), false);
		
		/* Obselete, no longer needed
		  
		// Villain beaten 2 times
		testVillain = new Villain(2, 200);
		testVillain.setMaxHealth(20);
		testVillain.setHealth(20);
		testVillain.takeDamage(30);
		testVillain.takeDamage(30);
		assertEquals(testVillain.getHealth(), 20);
		assertEquals(testVillain.getTimesBeaten(), 2);
		
		// Villain beaten 3 times
		testVillain = new Villain(5, 200);
		testVillain.setMaxHealth(20);
		testVillain.setHealth(20);
		testVillain.takeDamage(30);
		testVillain.takeDamage(30);
		testVillain.takeDamage(30);
		assertEquals(testVillain.getHealth(), 20);
		assertEquals(testVillain.getTimesBeaten(), 3);*/
	}

}
