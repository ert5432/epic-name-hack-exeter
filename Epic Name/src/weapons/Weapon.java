package weapons;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
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
	protected BufferedImage image;
	
	public Weapon(GameAgent owner,AttackInfo[] attacks) {
		this.owner=owner;
		this.numAttacks=attacks.length;
		this.attacks=attacks;
	}
	
	public AttackInfo[] getAttacks(){
		return attacks;
	}
	
	public void renderSprite(Graphics g,int x,int y){
		if(image!=null)
			g.drawImage(image, x, y, null);
	}
	
	public abstract boolean isRanged();
}
