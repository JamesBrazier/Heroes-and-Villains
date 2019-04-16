package Testing;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Main.Attribute;
import Main.City;
import Main.Constants;
import Main.Game;
import Main.Hero;
import Main.Item;
import Main.ItemPowerup;
import Main.RandomEvent;
import Main.Team;
import Main.Type;

public class GameTest {
	
	private Game game;

	@BeforeClass
	public static void setUpClass() throws Exception {
		Constants.random = new Random();
	}
	
	@Before
	public void setUp() throws Exception {
		City[] city = {new City("test", 1, false), new City("test2", 2, false)};
		Team team = new Team();
		team.addHero(new Hero("test", Type.ROGUE));
		game = new Game(city, city[0], team, 0);
	}

	@After
	public void tearDown() throws Exception {
		game = null;
	}

	@Test
	public void testPlayTurn() {
		game.playTurn();
		assertEquals(1, game.getTurn());
	}

	@Test
	public void testVisitCity() {
		game.visitCity(1);
		assertEquals(game.getCity().getName(), "test2");
	}

	@Test
	public void testGetPossibleCityIndices() {
		game.getCity().getVillain().setDefeated(true);
		assertTrue(game.getCity().isComplete());
		assertEquals(1, game.getPossibleCityIndices());
	}

	@Test
	public void testUseItem() {
		Item item = new ItemPowerup("test", 0, Attribute.MOREMAXHEALTH);
		Hero hero = game.getTeam().getHeroes().get(0);
		game.getTeam().addItem(item);
		game.useItem(hero, item);
		assertEquals(Constants.defaultMaxHealth + Constants.healthPerItem, hero.getMaxHealth());
		assertEquals(0, game.getTeam().getItems().size());
	}

	@Test
	public void testBuyItem() {
		Item item = new ItemPowerup();
		game.buyItem(item);
		assertEquals(Constants.startingMoney - Constants.powerupCost, game.getTeam().getMoney());
		assertEquals(item, game.getTeam().getItems().get(0));
	}

	@Test
	public void testSellItem() {
		Item item = new ItemPowerup();
		game.getTeam().addItem(item);
		assertEquals(item, game.getTeam().getItems().get(0));
		game.sellItem(item);
		assertEquals(Constants.startingMoney + (Constants.powerupCost / 2), game.getTeam().getMoney());
		assertEquals(0, game.getTeam().getItems().size());
	}

	@Test
	public void testRandomEvent() {
		RandomEvent testEvent = game.randomEvent();
		boolean result = false;
		for (RandomEvent event : RandomEvent.values()) {
			if (event == testEvent) {
				result = true;
			}
		}
		assertTrue(result);
	}

	@Test
	public void testAddRandomItem() {
		game.addRandomItem();
		assertEquals(1, game.getTeam().getItems().size());
	}

}
