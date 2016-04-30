package weapons;

import java.awt.Color;

import stats.DieRoll;
import weapons.entities.RangedProjectile;
import weapons.entities.Swing;
import weapons.entities.Wave;
import actions.attacks.Attack;
import actions.attacks.AttackInfo;
import actions.attacks.BowAttack;
import actions.attacks.Destruction;
import actions.attacks.FlameBurst;
import actions.attacks.MeleeAttackInfo;
import actions.attacks.WaveAttack;
import actions.attacks.WaveAttackInfo;
import entities.GameAgent;
import geometry.Polygon;
import geometry.Vector2D;

public class PowerRod extends Weapon{

	public PowerRod(GameAgent owner) {
		super(owner, new AttackInfo[]{
				new Destruction(),
				new WaveAttackInfo(new Wave(Math.PI*2/3,40,150,20,3,Color.cyan),new DieRoll(2,3),"wis","int"),
				new MeleeAttackInfo(new Swing(new Polygon(new double[]{15,45,15},new double[]{5,5,-5},0,0),Math.PI,20),new DieRoll(3,2),"str","str"),
			});
	}

	@Override
	public boolean isRanged() {
		// TODO Auto-generated method stub
		return true;
	}

	

}
