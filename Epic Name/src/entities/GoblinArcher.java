package entities;

import stats.Stats;
import weapons.Bow;
import weapons.PowerRod;
import world.World;

public class GoblinArcher extends Goblin {

	public GoblinArcher(double x, double y, World world) {
		super(x, y, world, new Stats(30,30,30,30,50));
		setWeapon(new Bow(this));
	}

}
