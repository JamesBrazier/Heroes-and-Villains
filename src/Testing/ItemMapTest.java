package Testing;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Main.City;
import Main.Constants;
import Main.Hero;
import Main.ItemMap;
import Main.Type;

public class ItemMapTest {

	private Hero hero;
	private City city;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		Constants.random = new Random();
	}
	
	@Before
	public void setUp() throws Exception {
		hero = new Hero("test", Type.ROGUE);
		city = new City("test", 1, false);
	}
	
	@After
	public void tearDown() throws Exception {
		hero = null;
		city = null;
	}

	@Test
	public void testUse1() {
		ItemMap item = new ItemMap(city);
		item.use(hero);
		for (int i = 0; i < 4; i++) {
			assertTrue(city.getDiscovered()[i]);
		}
	}

	@Test
	public void testCanUse1() {
		ItemMap item = new ItemMap(city);
		assertTrue(item.canUse(hero));
	}
	
	@Test
	public void testCanUse2() {
		ItemMap item = new ItemMap(city);
		for (int i = 0; i < 4; i++) {
			city.setDiscovered(i);
		}
		assertFalse(item.canUse(hero));
	}

}
