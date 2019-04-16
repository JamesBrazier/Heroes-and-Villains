package Testing;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Main.Attribute;
import Main.Constants;
import Main.Hero;
import Main.ItemPowerup;
import Main.Type;

public class ItemPowerupTest {

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
	public void testDefense() {
		ItemPowerup item = new ItemPowerup("test", 0, Attribute.MOREDEFENSE);
		item.use(hero);
		assertEquals(Constants.defensePerItem, hero.getDefense());
	}
	
	@Test
	public void testRecovery() {
		ItemPowerup item = new ItemPowerup("test", 0, Attribute.LESSRECOVERYTURNS);
		item.use(hero);
		assertEquals(Constants.fastRecoveryRate, hero.getRecoveryRate());
	}
	
	@Test
	public void testDamage() {
		ItemPowerup item = new ItemPowerup("test", 0, Attribute.MOREDAMAGE);
		item.use(hero);
		assertEquals(Constants.defaultDamage + Constants.damagePerItem, hero.getDamage());
	}
	
	@Test
	public void testHealth() {
		ItemPowerup item = new ItemPowerup("test", 0, Attribute.MOREMAXHEALTH);
		item.use(hero);
		assertEquals(Constants.defaultMaxHealth + Constants.healthPerItem, hero.getMaxHealth());
	}
	
	@Test
	public void testMaxDefense() {
		ItemPowerup item = new ItemPowerup("test", 0, Attribute.MOREDEFENSE);
		hero.setDefense(Constants.maxDefense);
		assertFalse(item.canUse(hero));
	}
	
	@Test
	public void testMaxRecovery() {
		ItemPowerup item = new ItemPowerup("test", 0, Attribute.LESSRECOVERYTURNS);
		hero.setRecoveryRate(Constants.fastRecoveryRate);
		assertFalse(item.canUse(hero));
	}
	
	@Test
	public void testMaxDamage() {
		ItemPowerup item = new ItemPowerup("test", 0, Attribute.MOREDAMAGE);
		hero.setDamage(Constants.maxDamage);;
		assertFalse(item.canUse(hero));
	}
	
	@Test
	public void testMaxHealth() {
		ItemPowerup item = new ItemPowerup("test", 0, Attribute.MOREMAXHEALTH);
		hero.setMaxHealth(Constants.maxHealth);;
		assertFalse(item.canUse(hero));
	}

}
