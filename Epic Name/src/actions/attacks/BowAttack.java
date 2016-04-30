package actions.attacks;

import stats.DieRoll;
import world.World;
import entities.GameAgent;
import geometry.Vector2D;
import weapons.entities.RangedProjectile;

public class BowAttack extends MissleAttackInfo{

	public BowAttack(RangedProjectile a,DieRoll d) {
		super(a,d,"str","dex");
	}

}
