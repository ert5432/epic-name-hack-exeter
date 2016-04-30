package actions.attacks;

import stats.DieRoll;
import world.World;
import entities.GameAgent;
import geometry.Vector2D;
import weapons.entities.RangedProjectile;
import weapons.entities.ThrownProjectile;

public class LightThrowAttack extends ThrowAttackInfo{

	public LightThrowAttack(ThrownProjectile a,DieRoll d) {
		super(a,d,"dex","dex");
	}
}
