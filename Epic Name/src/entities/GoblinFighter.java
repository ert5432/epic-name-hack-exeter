package entities;

import stats.Stats;
import weapons.Bow;
import weapons.PowerRod;
import weapons.Sword;
import world.World;

public class GoblinFighter extends Goblin {

	public GoblinFighter(double x, double y, World world) {
		super(x, y, world, new Stats(30,30,30,30,50));
		setWeapon(new Sword(this));
	}

}
