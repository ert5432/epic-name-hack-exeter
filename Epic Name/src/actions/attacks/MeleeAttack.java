package actions.attacks;

import stats.DieRoll;
import weapons.entities.WeaponEntity;
import world.World;
import entities.GameAgent;

public class MeleeAttack extends Attack{

	
	public MeleeAttack(WeaponEntity a, DieRoll d, String primaryStat, String secondaryStat, GameAgent doer, World world) {
		super(a, d, primaryStat, secondaryStat, doer, world);
		MIN_COOLDOWN=a.getDuration();
	}

	public void begin(){
		super.begin();
		attack.addCopytoWorld(world, null, doer,dice.roll()+doer.stats.getStat(primary).modifier);
	}
	
}
