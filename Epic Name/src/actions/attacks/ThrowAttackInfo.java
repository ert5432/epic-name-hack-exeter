package actions.attacks;

import actions.Action;
import entities.GameAgent;
import geometry.Vector2D;
import stats.DieRoll;
import weapons.entities.RangedProjectile;
import weapons.entities.ThrownProjectile;
import world.World;

public class ThrowAttackInfo extends AttackInfo {

	public ThrowAttackInfo(ThrownProjectile a, DieRoll d, String primary,String secondary) {
		super(a, d, primary, secondary);
	}
	
	public Action createAction(GameAgent doer,World world){
		return new ThrowAttack((ThrownProjectile)attack,damageRoll,primaryStat,secondaryStat,doer,world);
	}

}
