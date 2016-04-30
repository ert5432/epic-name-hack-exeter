package actions.attacks;

import stats.DieRoll;
import world.World;
import entities.GameAgent;
import geometry.Vector2D;
import weapons.entities.RangedProjectile;

public class CrossbowAttack extends MissleAttackInfo{

	public CrossbowAttack(RangedProjectile a,DieRoll d) {
		super(a,d,"dex","dex");
	}
}
