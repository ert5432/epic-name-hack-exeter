package actions.attacks;

import actions.Action;
import stats.DieRoll;
import weapons.entities.RangedProjectile;
import world.World;
import entities.GameAgent;
import geometry.Vector2D;

public class MissleAttackInfo extends AttackInfo{

	public MissleAttackInfo(RangedProjectile a,DieRoll d,String primary,String secondary) {
		super(a,d,primary,secondary);
	}
	
	public Action createAction(GameAgent doer,World world){
		return new MissleAttack((RangedProjectile) attack,damageRoll,primaryStat,secondaryStat,doer,world);
	}

}
