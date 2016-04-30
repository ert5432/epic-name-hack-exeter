package actions.attacks;

import stats.DieRoll;
import world.World;
import entities.GameAgent;
import geometry.Vector2D;
import weapons.entities.RangedProjectile;

public class RangedAttack extends Attack {

	public RangedAttack(RangedProjectile a,DieRoll d,String primary,String secondary,GameAgent doer,World world) {
		super(a,d,primary,secondary,doer,world);
	}
	
	public void begin(){
		super.begin();
		double accuracyRange=((RangedProjectile)attack).accuracyRange/Math.pow(2, doer.stats.getStat(secondary).val/15.0);
		Vector2D direction=doer.heading.rotate(accuracyRange*Math.random()-accuracyRange/2);
		((RangedProjectile)attack).addCopytoWorld(world, null, direction,attack.getMaxVelocity(),doer,dice.roll()+doer.stats.getStat(primary).modifier);
	}

}
