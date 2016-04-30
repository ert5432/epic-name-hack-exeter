package actions.attacks;

import actions.Action;
import stats.DieRoll;
import world.World;
import entities.GameAgent;
import geometry.Vector2D;
import weapons.entities.RangedProjectile;

public class RangedAttackInfo extends AttackInfo {

	public RangedAttackInfo(RangedProjectile a,DieRoll d,String primary,String secondary) {
		super(a,d,primary,secondary);
	}
	
	public Action createAction(GameAgent doer,World world){
		return new RangedAttack((RangedProjectile) attack,damageRoll,primaryStat,secondaryStat,doer,world);
		
	}

}
