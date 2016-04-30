package weapons;

import stats.DieRoll;
import entities.GameAgent;
import geometry.Polygon;
import geometry.Vector2D;
import weapons.entities.RangedProjectile;
import weapons.entities.ThrownProjectile;
import actions.attacks.Attack;
import actions.attacks.AttackInfo;
import actions.attacks.HeavyThrowAttack;

public class Stone extends Weapon{

	public Stone(GameAgent owner) {
		super(owner,new AttackInfo[]{
				new HeavyThrowAttack(new ThrownProjectile(new Vector2D(0,10),new Polygon(new double[]{15,45,15},new double[]{5,5,-5},0,0),5,Math.PI/4,50,100,5),new DieRoll(3,2)),
				new HeavyThrowAttack(new ThrownProjectile(new Vector2D(0,10),new Polygon(new double[]{15,45,15},new double[]{5,5,-5},0,0),5,Math.PI/4,50,100,5),new DieRoll(3,2)),
				new HeavyThrowAttack(new ThrownProjectile(new Vector2D(0,10),new Polygon(new double[]{15,45,15},new double[]{5,5,-5},0,0),5,Math.PI/4,50,100,5),new DieRoll(3,2))
			});
	}

	@Override
	public boolean isRanged() {
		// TODO Auto-generated method stub
		return true;
	}

}
