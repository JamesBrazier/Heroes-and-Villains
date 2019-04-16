package Testing;

import static org.junit.Assert.*;

import java.util.Random;

import Main.Hero;
import Main.Type;
import Main.City;
import Main.Constants;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HeroTests {
	private Hero testHero;
	private Hero fasterHealthHero;
	private Hero moreMaxHealthHero;
	private Hero testHeroLessMaxHealth ;
	
	private void resetTestHero() {
		testHero = new Hero("TestHero", Type.WIZARD);
	}
	
	@BeforeClass
	public static void beforeClass() {
		Constants.random = new Random();
	}
	
	@Before
	public void setUp() throws Exception {
		testHero = new Hero("TestHero", Type.WIZARD);
		fasterHealthHero = new Hero("FasterHealthTestHero", Type.SWORDSMAN);
		moreMaxHealthHero =  new Hero("MoreMaxHealthHero", Type.PALADIN);
	}

	@Test
	public void testInitialization() {
		testHero = new Hero("TestHero", Type.ROGUE);
		testHeroLessMaxHealth = new Hero("TestHeroLessHealth", Type.WIZARD);
		fasterHealthHero = new Hero("FasterHealthTestHero", Type.SWORDSMAN);
		moreMaxHealthHero = new Hero("MoreMaxHealthHero", Type.PALADIN);
		
		assertEquals(testHeroLessMaxHealth.getMaxHealth(), Constants.defaultMaxHealth - 
				Constants.healthPerItem);
		assertEquals(testHero.getName(), "TestHero");
		assertEquals(testHero.getType(), Type.ROGUE);
		assertEquals(testHero.isDead(), false);
		assertNull(testHero.getHospitalCity());
		assertEquals(testHero.getMaxHealth(), Constants.defaultMaxHealth);
		assertEquals(testHero.getHealth(), testHero.getMaxHealth());

		assertEquals(testHero.getRecoveryRate(), Constants.defaultRecoveryRate);
		assertEquals(moreMaxHealthHero.getMaxHealth(), Constants.defaultMaxHealth + Constants.healthPerItem);
		assertEquals(moreMaxHealthHero.getHealth(), Constants.defaultMaxHealth + Constants.healthPerItem);
		assertEquals(fasterHealthHero.getRecoveryRate(), Constants.fastRecoveryRate);
	}
	
	@Test
	public void testTakeDamage() {
		// Blue sky scenario
		testHero.setHealth(10);
		testHero.takeDamage(1);
		assertEquals(testHero.getHealth(), 9);
		// Zero damage (Boundary value)
		testHero.setHealth(100);
		testHero.takeDamage(0);
		assertEquals(testHero.getHealth(), 100);
		// Invalid argument
		testHero.setHealth(100);
		testHero.takeDamage(-1);
		assertEquals(testHero.getHealth(), 100);
		// Blue sky scenario
		testHero.setHealth(100);
		testHero.takeDamage(99);
		assertEquals(testHero.isDead(), false);
		assertEquals(testHero.getHealth(), 1);
		// Boundary value (when damage taken brings the hero to exactly 0 health points)
		testHero.setHealth(100);
		testHero.takeDamage(100);
		assertEquals(testHero.isDead(), true);
		assertEquals(testHero.getHealth(), 0);
		// Blue sky scenario (when the damage done to a hero is equal or more than the heroes health points
		testHero.setHealth(20);
		testHero.takeDamage(25);
		assertEquals(testHero.isDead(), true);
		assertEquals(testHero.getHealth(), 0);
		// If hero takes damage when dead, hero should stay dead
		testHero.takeDamage(25);
		assertEquals(testHero.isDead(), true);
		assertEquals(testHero.getHealth(), 0);
		// Will the class behave properly when damage is applied more than once?
		resetTestHero();
		assertEquals(testHero.isDead(), false);
		testHero.setHealth(70);
		testHero.takeDamage(35);
		assertEquals(testHero.isDead(), false);
		testHero.takeDamage(35);
		assertEquals(testHero.isDead(), true);
	}

	@Test
	public void testHeal() {
		// Blue sky scenario, no boundaries tested
		testHero.setMaxHealth(50);
		testHero.setHealth(20);
		testHero.heal(20);
		assertEquals(testHero.getHealth(), 40);
		// When zero is passed into the heal function, zero health should be healed
		testHero.setHealth(40);
		testHero.heal(0);
		assertEquals(testHero.getHealth(), 40);
		// Healing by a minus amount should not do anything at all
		testHero.setMaxHealth(50);
		testHero.setHealth(20);
		testHero.heal(-10);
		// When the healing points bring the hero to exactly maximum health (boundary)
		testHero.setMaxHealth(50);
		testHero.setHealth(20);
		testHero.heal(30);
		assertEquals(testHero.getHealth(), testHero.getMaxHealth());
		// When the healing points bring the hero to above maximum health, the hero's final health should not exceed it's
		// maximum
		testHero.setMaxHealth(100);
		testHero.setHealth(80);
		testHero.heal(50);
		assertEquals(testHero.getHealth(), testHero.getMaxHealth());
		assertEquals(testHero.getHealth(), 100);
	}

	@Test
	public void testHospitalize() {
		// Hospitalize hero when hero is not hospitalized 
		assertNull(testHero.getHospitalCity());
		City dummyCity = new City("dummy", 1, true);
		testHero.hospitalize(dummyCity, 1);
		assertEquals(testHero.getHospitalCity(), dummyCity);
		// Hospitalize when hero is already hospitalized
		assertNotNull(testHero.getHospitalCity());
		City dummyCity2 = new City("dummy2", 1, true);
		testHero.hospitalize(dummyCity2, 1);
		assertEquals(testHero.getHospitalCity(), dummyCity2);
	}

	@Test
	public void testUnHospitalize() {
		// Blue sky scenario
		City dummyCity = new City("dummy", 1, true);
		testHero.hospitalize(dummyCity, 1);
		assertNotNull(testHero.getHospitalCity());
		testHero.unHospitalize(dummyCity, 100);
		assertNull(testHero.getHospitalCity());
		// Function does nothing if hero is already not hospitalized
		testHero.unHospitalize(dummyCity, 100);
		assertNull(testHero.getHospitalCity());
	}

	@Test
	public void testIsDisabled() {
		// Hero is disabled when in hospital
		assertEquals(testHero.isDisabled(), false);
		City dummyCity = new City("dummy", 1, true);
		testHero.hospitalize(dummyCity, 1);
		assertEquals(testHero.isDisabled(), true);
		// Hero is disabled when dead, even if not in hospital)
		resetTestHero();
		testHero.setHealth(1);
		testHero.takeDamage(25);
		assertEquals(testHero.isDead(), true);
		assertEquals(testHero.isDisabled(), true);
		// Hero is disabled when dead and in hospital
		resetTestHero();
		testHero.hospitalize(dummyCity, 1);
		testHero.setHealth(1);
		testHero.takeDamage(25);
		assertEquals(testHero.isDisabled(), true);
	}

	@Test
	public void testIsDead() {
		// Hero should not spawn dead
		assertEquals(testHero.isDead(), false);
		// If hero is dead, should report that hero is dead
		testHero.setDead(true);
		assertEquals(testHero.isDead(), true);
		// If hero is not dead, should report that hero is dead
		testHero.setDead(false);
		assertEquals(testHero.isDead(), false);
	}

}

