package weapons;

import java.util.Random;

import actions.attacks.Attack;
import actions.attacks.AttackInfo;
import weapons.entities.Projectile;
import world.World;
import entities.GameAgent;

public abstract class Weapon {
	
	private GameAgent owner;
	private AttackInfo[] attacks;
	private int numAttacks;
	
	
	public Weapon(GameAgent owner,AttackInfo[] attacks) {
		this.owner=owner;
		this.numAttacks=attacks.length;
		this.attacks=attacks;
	}
	
	public AttackInfo[] getAttacks(){
		return attacks;
	}
}
