package Testing;

import static org.junit.Assert.*;

import java.util.Random;
import java.util.ArrayList;

import Main.Constants;
import Main.Hero;
import Main.Item;
import Main.ItemHealing;
import Main.Team;
import Main.Type;

import org.junit.Test;
import org.junit.Before;

public class TeamTest {
	private Team testTeam;
	
	private Random newRandom() {
		return new Random(0xaaaa);
	}
	
	@Before
	public void setup() {
		testTeam = new Team();
		testTeam.setMoney(10);
	}
	
	@Test
	public void testConstructor() {
		Team newTestTeam = new Team();
		assertTrue(testTeam.getHeroes().isEmpty());
		assertTrue(testTeam.getItems().isEmpty());
		assertEquals(newTestTeam.getMoney(), Constants.startingMoney);
	}
	
	@Test
	public void testCanSpendNormal() {
		assertTrue(testTeam.canSpend(1));
		assertTrue(testTeam.canSpend(9));
	}
	
	@Test
	public void testCanSpendBoundaries() {
		assertTrue(testTeam.canSpend(0));
		assertTrue(testTeam.canSpend(10));
	}
	
	@Test
	public void testCanSpendInvalid() {
		assertFalse(testTeam.canSpend(11));
		assertFalse(testTeam.canSpend(100));
	}
	
	/* What should happen here? (Need to discuss)
	@Test
	public void testCanSpendNegative() {
		testTeam.setMoney(10);
		assertFalse(testTeam.canSpend(-1));
	}*/
	
	@Test
	public void testDecreaseMoneyNormal() {
		testTeam.decreaseMoney(1);
		assertEquals(testTeam.getMoney(), 9);
		testTeam.decreaseMoney(8);
		assertEquals(testTeam.getMoney(), 1);
	}
	
	@Test
	public void testDecreaseMoneyBoundary() {
		testTeam.decreaseMoney(0);
		assertEquals(testTeam.getMoney(), 10);
		testTeam.decreaseMoney(10);
		assertEquals(testTeam.getMoney(), 0);
	}
	
	@Test
	public void testDecreaseMoneyBelowZero() {
		testTeam.decreaseMoney(15);
		assertEquals(testTeam.getMoney(), 0);
		// Decrease money when money is already zero
		testTeam.decreaseMoney(10);
		assertEquals(testTeam.getMoney(), 0);
	}
	
	@Test
	public void testDecreaseMoneyNegative() {
		// Nothing should happen
		testTeam.decreaseMoney(-5);
		assertEquals(testTeam.getMoney(), 10);
	}
	
	@Test
	public void testIncreaseMoneyNormal() {
		testTeam.increaseMoney(20);
		assertEquals(testTeam.getMoney(), 30);
		testTeam.increaseMoney(1);
		assertEquals(testTeam.getMoney(), 31);
		testTeam.increaseMoney(10000);
		assertEquals(testTeam.getMoney(), 10031);
	}
	
	@Test
	public void testIncreaseMoneyBoundary() {
		testTeam.increaseMoney(0);
		assertEquals(testTeam.getMoney(), 10);
		testTeam.setMoney(0);
		testTeam.increaseMoney(5);
		assertEquals(testTeam.getMoney(), 5);
	}
	
	@Test
	public void testAddHero() {
		testTeam.addHero(new Hero("Hero1", Type.ROGUE));
		assertEquals(testTeam.getHeroes().get(0).getName(), "Hero1");
		assertEquals(testTeam.getHeroes().size(), 1);
		testTeam.addHero(new Hero("Hero2", Type.ROGUE));
		assertEquals(testTeam.getHeroes().get(0).getName(), "Hero1");
		assertEquals(testTeam.getHeroes().get(1).getType(), Type.ROGUE);
		assertEquals(testTeam.getHeroes().size(), 2);
	}
	
	@Test
	public void testAddItem() {
		Item item = new ItemHealing(1);
		Item item2 = new ItemHealing(1);
		testTeam.addItem(item);
		assertEquals(testTeam.getItems().get(0), item);
		assertEquals(testTeam.getItems().size(), 1);
		testTeam.addItem(item2);
		assertEquals(testTeam.getItems().get(1), item2);
		assertEquals(testTeam.getItems().size(), 2);
	}
	
	/*@Test
	public void testRemoveRandomItem_1() {
		Item item = new ItemHealing(1);
		testTeam.addItem(item);
		assertEquals(testTeam.getItems().size(), 1);
		testTeam.removeRandomItem();
		assertEquals(testTeam.getItems().size(), 0);
	}*/
	
	/*@Test
	public void testRemoveRandomItem_Big() {
		// Should throw an error (Not implemented)
		// testTeam.removeRandomItem();
		Random random = newRandom();
		Item item = new ItemHealing(1);
		Item item2 = new ItemHealing(1);
		Item item3 = new ItemHealing(1);
		// RNG passed into team is seeded so test becomes reproducible
		Team newTestTeam = new Team("Test", random);
		int tests = 0;
		ArrayList<Integer> combinations = new ArrayList<Integer>();
		
		
		for(int i=0;i<60;i++) {
			newTestTeam.addItem(item);
			newTestTeam.addItem(item2);
			newTestTeam.addItem(item3);
			newTestTeam.removeRandomItem();
			assertEquals(newTestTeam.getItems().size(), 2);
			if(!newTestTeam.getItems().contains(item))
				tests += 1;
			if(!newTestTeam.getItems().contains(item2))
				tests += 2;
			if(!newTestTeam.getItems().contains(item3))
				tests += 4;
			if(!combinations.contains(tests))
				combinations.add(tests);
			tests = 0;
			newTestTeam.removeRandomItem();
			newTestTeam.removeRandomItem();
			assertEquals(newTestTeam.getItems().size(), 0);
		}
		assertEquals(combinations.size(), 3);
	}*/
}
