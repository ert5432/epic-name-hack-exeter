package actions.attacks;

import entities.GameAgent;
import geometry.Vector2D;
import stats.DieRoll;
import weapons.entities.RangedProjectile;
import weapons.entities.ThrownProjectile;
import world.World;

public class ThrowAttack extends Attack {

	public ThrowAttack(ThrownProjectile a, DieRoll d, String primary,String secondary,GameAgent doer,World world) {
		super(a, d, primary, secondary,doer,world);
	}
	
	public void begin(){
		super.begin();
		double accuracyRange=((ThrownProjectile)attack).accuracyRange/Math.pow(2, doer.stats.getStat(secondary).val/15.0);
		Vector2D direction=doer.heading.rotate(accuracyRange*Math.random()-accuracyRange/2);
		((ThrownProjectile)attack).addCopytoWorld(world, null, direction,4*(doer.stats.getStat(primary).val/30+0.5)/attack.getMass(),doer,doer.stats.getStat(primary).modifier);
	}

}
