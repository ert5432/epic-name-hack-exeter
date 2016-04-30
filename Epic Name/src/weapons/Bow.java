package weapons;

import stats.DieRoll;
import weapons.entities.RangedProjectile;
import entities.GameAgent;
import geometry.Polygon;
import geometry.Vector2D;
import actions.attacks.Attack;
import actions.attacks.AttackInfo;
import actions.attacks.BowAttack;
import actions.attacks.RangedAttack;

public class Bow extends Weapon {

	public Bow(GameAgent owner) {
		super(owner,new AttackInfo[]{
			new BowAttack(new RangedProjectile(new Vector2D(0,10),new Polygon(new double[]{15,45,15},new double[]{5,5,-5},0,0),5,Math.PI/4,20,100),new DieRoll(3,2)),
			new BowAttack(new RangedProjectile(new Vector2D(0,10),new Polygon(new double[]{15,45,15},new double[]{5,5,-5},0,0),5,Math.PI/4,20,100),new DieRoll(3,2)),
			new BowAttack(new RangedProjectile(new Vector2D(0,10),new Polygon(new double[]{15,45,15},new double[]{5,5,-5},0,0),5,Math.PI/4,20,100),new DieRoll(3,2))
		});
	}

	@Override
	public boolean isRanged() {
		// TODO Auto-generated method stub
		return true;
	}
}
