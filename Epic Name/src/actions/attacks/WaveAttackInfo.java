package actions.attacks;

import actions.Action;
import entities.GameAgent;
import geometry.Vector2D;
import stats.DieRoll;
import weapons.entities.RangedProjectile;
import weapons.entities.WeaponEntity;
import weapons.entities.Wave;
import world.World;

public class WaveAttackInfo extends AttackInfo {
	
	public WaveAttackInfo(Wave a, DieRoll d, String primaryStat, String secondaryStat){
		super(a, d, primaryStat, secondaryStat);
	}
	
	public Action createAction(GameAgent doer,World world){
		return new WaveAttack((Wave) attack,damageRoll,primaryStat,secondaryStat,doer,world);
	}

}
