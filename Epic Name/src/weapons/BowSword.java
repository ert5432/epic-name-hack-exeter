package weapons;

import stats.DieRoll;
import entities.GameAgent;
import geometry.Polygon;
import geometry.Vector2D;
import weapons.entities.RangedProjectile;
import weapons.entities.Stab;
import weapons.entities.Swing;
import actions.attacks.Attack;
import actions.attacks.AttackInfo;
import actions.attacks.BowAttack;
import actions.attacks.MeleeAttackInfo;

public class BowSword extends Weapon {

	public BowSword(GameAgent owner) {
		super(owner,new AttackInfo[]{
			new MeleeAttackInfo(new Swing(new Polygon(new double[]{15,45,15},new double[]{5,5,-5},0,0),-Math.PI,20),new DieRoll(3,2),"str","str"),
			new MeleeAttackInfo(new Stab(new Polygon(new double[]{15,45,15},new double[]{5,5,-5}),20,12),new DieRoll(3,2),"str","str"),
			new MeleeAttackInfo(new Swing(new Polygon(new double[]{15,45,15},new double[]{5,5,-5},0,0),Math.PI,20),new DieRoll(3,2),"str","str"),
			new BowAttack(new RangedProjectile(new Vector2D(0,10),new Polygon(new double[]{15,45,15},new double[]{5,5,-5},0,0),5,Math.PI/4,50,100),new DieRoll(3,2)),
			new BowAttack(new RangedProjectile(new Vector2D(0,10),new Polygon(new double[]{15,45,15},new double[]{5,5,-5},0,0),5,Math.PI/4,50,100),new DieRoll(3,2)),
		});
	}

	@Override
	public boolean isRanged() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
