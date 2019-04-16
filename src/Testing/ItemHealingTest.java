package Testing;


import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Main.Hero;
import Main.Type;
import Main.ItemHealing;

public class ItemHealingTest {
	
	private Hero hero;

	@Before
	public void setUp() throws Exception {
		hero = new Hero("test", Type.ROGUE);
	}

	@After
	public void tearDown() throws Exception {
		hero = null;
	}

	@Test
	public void testUse1() {
		ItemHealing item = new ItemHealing(1);
		hero.takeDamage(25);;
		item.use(hero);
		assertEquals(100, hero.getHealth());
	}
	
	@Test
	public void testCanUse1() {
		ItemHealing item = new ItemHealing(1);
		assertFalse(item.canUse(hero));
	}
	
	@Test
	public void testCanUse2() {
		ItemHealing item = new ItemHealing(1);
		hero.takeDamage(25);
		assertTrue(item.canUse(hero));
	}
	
	@Test
	public void testUse2() {
		ItemHealing item = new ItemHealing(1);
		item.use(hero);
		assertEquals(100, hero.getHealth());
	}

}
