package actions.attacks;

import stats.DieRoll;
import weapons.entities.WeaponEntity;
import world.World;
import actions.Action;
import entities.GameAgent;

public class MeleeAttackInfo extends AttackInfo{

	public MeleeAttackInfo(WeaponEntity a, DieRoll d, String primaryStat,String secondaryStat) {
		super(a, d, primaryStat, secondaryStat);
	}

	@Override
	public Action createAction(GameAgent doer, World world) {
		return new MeleeAttack(attack,damageRoll,primaryStat,secondaryStat,doer,world);
	}

}
