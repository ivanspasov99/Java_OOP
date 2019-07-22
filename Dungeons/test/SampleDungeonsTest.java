import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import bg.uni.sofia.fmi.mjt.dungeon.Direction;
import bg.uni.sofia.fmi.mjt.dungeon.GameEngine;
import bg.uni.sofia.fmi.mjt.dungeon.treasure.*;
import org.junit.Before;
import org.junit.Test;

import bg.uni.sofia.fmi.mjt.dungeon.actor.Enemy;
import bg.uni.sofia.fmi.mjt.dungeon.actor.Hero;

public class SampleDungeonsTest {
	private Hero hero;
	private char[][] map;
	private Enemy[] enemies;
	private Enemy[] enemies2;
	private Treasure[] treasures;
	private GameEngine gameEngine;
	private GameEngine gameEngine2;

	@Before
	public void setup() {
		hero = new Hero("hero", 100, 100);

		map = new char[][]{"###".toCharArray(),
				"TS.".toCharArray(),
				"#EG".toCharArray()};
		enemies = new Enemy[]{
				new Enemy("enemy", 100, 50, new Weapon("enemy weapon", 30), new Spell("spell", 34, 10))};
		treasures = new Treasure[]{new Weapon("strong weapon", 50)};
		gameEngine = new GameEngine(map, hero, enemies, treasures);
		enemies2 = new Enemy[]{
				new Enemy("enemy2", 100, 10, new Weapon("bam", -1), null)
		};
		gameEngine2 = new GameEngine(map, hero, enemies2, treasures);
	}
	// ENEMIES 2 & gameEngine2
	@Test
	public void getEnemyAndHeroDamages(){
		assertEquals(10, gameEngine2.enemies[0].getMana());
		assertEquals(100, gameEngine2.enemies[0].getHealth());
		assertEquals(-1, gameEngine2.enemies[0].getWeapon().getDamage());
		//assertEquals(0, gameEngine2.enemies[0].getSpell().getDamage());
	}
	// ENEMIES & gameEngine2
	@Test
	public void testEnemyGetDamage(){
		gameEngine.getHero().equip(new Weapon("Baam", 1));
		gameEngine.makeMove(Direction.DOWN);
		assertEquals(20, gameEngine.enemies[0].getMana());
		assertEquals(97, gameEngine.enemies[0].getHealth());
		assertEquals(30, gameEngine.enemies[0].getWeapon().getDamage());
		assertEquals(34, gameEngine.enemies[0].getSpell().getDamage());

		gameEngine.getHero().takeHealing(new HealthPotion(1000).heal());
	}
	@Test
	public void HeroManaPotionBeforeAndAfterFight(){
		gameEngine.getHero().takeMana(new ManaPotion(100).heal());
		assertEquals(100, gameEngine.getHero().getMana());

		gameEngine.getHero().equip(new Weapon("Baam", 20));
		gameEngine.getHero().learn(new Spell("spell1", 30, 50));
		String moveMessage = gameEngine.makeMove(Direction.DOWN);

		assertEquals("Hero is dead! Game over!", moveMessage);
		assertEquals(0, gameEngine.getHero().getMana());
		gameEngine.getHero().takeMana(new ManaPotion(50).heal());
		assertEquals(50, gameEngine.getHero().getMana());
	}
	@Test
	public void HeroHealingBeforeAndAfterFight() {
		gameEngine.getHero().takeHealing(new HealthPotion(100).heal());
		assertEquals(100, gameEngine.getHero().getHealth());

		gameEngine.getHero().equip(new Weapon("Baam", 20));
		gameEngine.getHero().learn(new Spell("spell1", 30, 50));
		String moveMessage = gameEngine.makeMove(Direction.DOWN);

		assertEquals("Hero is dead! Game over!", moveMessage);
		assertEquals(-2, gameEngine.getHero().getHealth());
		gameEngine.getHero().takeHealing(new HealthPotion(10).heal());
		assertEquals(-2, gameEngine.getHero().getHealth());
		gameEngine.getHero().takeHealing(new HealthPotion(10).heal());
		assertEquals(-2, gameEngine.getHero().getHealth());

	}
	@Test
	public void EnemyGetDamage() {

		assertEquals(30, gameEngine.enemies[0].getWeapon().getDamage());
		// assertEquals(null, gameEngine.enemies[0].getSpell().getDamage()); ?
	}
	@Test
	public void testMoveToEmptyBlock() {
		String moveMessage = gameEngine.makeMove(Direction.RIGHT);

		assertEquals("You moved successfully to the next position.", moveMessage);
		assertEquals('.', gameEngine.getMap()[1][1]);
		assertEquals('H', gameEngine.getMap()[1][2]);

	}

	@Test
	public void testMoveToObstacle() {
		gameEngine.makeMove(Direction.RIGHT);
		String moveMessage = gameEngine.makeMove(Direction.UP);

		assertEquals("Wrong move. There is an obstacle and you cannot bypass it.", moveMessage);
		assertEquals('H', gameEngine.getMap()[1][2]);
	}

	@Test
	public void testMoveToTreasure() {
		String moveMessage = gameEngine.makeMove(Direction.LEFT);

		assertEquals("Weapon found! Damage points: 50", moveMessage);
		assertEquals('.', gameEngine.getMap()[1][1]);
		assertEquals('H', gameEngine.getMap()[1][0]);

		assertEquals("strong weapon", gameEngine.getHero().getWeapon().getName());
		assertEquals(50, gameEngine.getHero().getWeapon().getDamage());

		gameEngine.getHero().equip(new Weapon("halbadier", 51));
		assertEquals("halbadier", gameEngine.getHero().getWeapon().getName());
		assertEquals(51, gameEngine.getHero().getWeapon().getDamage());

		gameEngine.getHero().equip(new Weapon("halbadiers", 49));
		assertEquals(51, gameEngine.getHero().getWeapon().getDamage());
	}
	@Test
	public void testFightWithEnemyWithoutWeaponsAndLoose() {
		String moveMessage = gameEngine.makeMove(Direction.DOWN);

		assertEquals("Hero is dead! Game over!", moveMessage);
		assertEquals('E', gameEngine.getMap()[2][1]);
		assertEquals('H', gameEngine.getMap()[1][1]);

		assertEquals(30, gameEngine.enemies[0].getWeapon().getDamage());

	}
	@Test
	public void testFightWithWeaponsEnemyAndLoose() {

		gameEngine.getHero().learn(new Spell("Baam", 20, 50 ));
		String moveMessage = gameEngine.makeMove(Direction.DOWN);

		assertEquals("Hero is dead! Game over!", moveMessage);
		assertEquals('E', gameEngine.getMap()[2][1]);
		assertEquals('H', gameEngine.getMap()[1][1]);

	}
	@Test
	public void testFightWithWeaponsEnemyAndWin() {
		gameEngine.getHero().equip(new Weapon("Baam", 35));
		gameEngine.getHero().learn(new Spell("spell1", 30, 50));
		String moveMessage = gameEngine.makeMove(Direction.DOWN);

		assertEquals("Enemy died.", moveMessage);
		assertEquals('H', gameEngine.getMap()[2][1]);
		assertEquals('.', gameEngine.getMap()[1][1]);

		moveMessage = gameEngine.makeMove(Direction.RIGHT);
		assertEquals("You have successfully passed through the dungeon. Congrats!", moveMessage);
		assertEquals('H', gameEngine.getMap()[2][2]);
		assertEquals('.', gameEngine.getMap()[2][1]);

	}
}
