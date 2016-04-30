package weapons;

import stats.DieRoll;
import weapons.entities.Stab;
import weapons.entities.Swing;

import java.awt.Graphics;

import actions.attacks.Attack;
import actions.attacks.AttackInfo;
import actions.attacks.MeleeAttackInfo;
import entities.GameAgent;
import geometry.Polygon;

public class Sword extends Weapon{

	public Sword(GameAgent owner) {
		super(owner,new AttackInfo[]{
			new MeleeAttackInfo(new Swing(new Polygon(new double[]{15,45,15},new double[]{5,5,-5},0,0),-Math.PI,20),new DieRoll(3,2),"str","str"),
			new MeleeAttackInfo(new Stab(new Polygon(new double[]{15,45,15},new double[]{5,5,-5}),20,12),new DieRoll(3,2),"str","str"),
			new MeleeAttackInfo(new Swing(new Polygon(new double[]{15,45,15},new double[]{5,5,-5}),Math.PI,20),new DieRoll(3,2),"str","str")
		});
	}
	
	public void renderSprite(Graphics g,int x,int y){
		
	}
}
