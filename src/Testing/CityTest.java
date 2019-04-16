package Testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Main.City;
import Main.Constants;
import Main.Item;
import Main.Location;
import Main.Villain;

public class CityTest {
	
	private City city;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		Constants.random = new Random();
	}
	
	@Before
	public void setUp() throws Exception {
		Location[] temp1 = {Location.POWERUPDEN, Location.HOSPITAL, Location.LAIR, Location.SHOP};
		boolean[] temp2 = {true, true, true, true};
		city = new City("test", temp1, temp2, new ArrayList<Item>(), new Villain(1), 1);
	}

	@After
	public void tearDown() throws Exception {
		city = null;
	}

	@Test
	public void testGetLocation() {
		assertEquals(Location.POWERUPDEN, city.getLocation(0));
		assertEquals(Location.HOSPITAL, city.getLocation(1));
		assertEquals(Location.LAIR, city.getLocation(2));
		assertEquals(Location.SHOP, city.getLocation(3));
	}

	@Test
	public void testIsComplete() {
		city.getVillain().setDefeated(true);
		assertTrue(city.isComplete());
	}
	
	@Test
	public void testIsIncomplete() {
		assertFalse(city.isComplete());
	}

}
