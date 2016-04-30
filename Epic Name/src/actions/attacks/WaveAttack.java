package actions.attacks;

import entities.GameAgent;
import geometry.Vector2D;
import stats.DieRoll;
import weapons.entities.RangedProjectile;
import weapons.entities.WeaponEntity;
import weapons.entities.Wave;
import world.World;

public class WaveAttack extends Attack {
	
	public WaveAttack(Wave a, DieRoll d, String primaryStat, String secondaryStat,GameAgent doer,World world){
		super(a, d, primaryStat, secondaryStat,doer,world);
	}
	
	public void begin(){
		super.begin();
		Wave attack=(Wave)this.attack;
		double speed=attack.getNormalSpeed()*doer.stats.getStat(primary).val/15+1;
		double maxRange=Math.max(attack.getNormalRange()*doer.stats.getStat(secondary).val/30,10);
		attack.addCopytoWorld(world, doer.position.clone(), doer, speed, maxRange+attack.startRadius,dice.roll()+doer.stats.getStat(primary).modifier);
	}

}
