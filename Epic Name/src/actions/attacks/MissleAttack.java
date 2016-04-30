package actions.attacks;

import stats.DieRoll;
import weapons.entities.RangedProjectile;
import world.World;
import entities.GameAgent;
import geometry.Vector2D;

public class MissleAttack extends Attack{

	public MissleAttack(RangedProjectile a,DieRoll d,String primary,String secondary,GameAgent doer,World world) {
		super(a,d,primary,secondary,doer,world);
	}
	
	public void begin(){
		super.begin();
		double accuracyRange=((RangedProjectile)attack).accuracyRange/Math.pow(2, doer.stats.getStat(secondary).val/15.0);
		Vector2D direction=doer.heading.rotate(accuracyRange*Math.random()-accuracyRange/2);
		((RangedProjectile)attack).addCopytoWorld(world, null, direction,Math.min(doer.stats.getStat(primary).val/5.0+0.5,attack.getMaxVelocity()),doer,dice.roll()+doer.stats.getStat(primary).modifier);
	}

}
