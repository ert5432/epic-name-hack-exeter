package weapons;

import stats.DieRoll;
import weapons.entities.RangedProjectile;
import entities.GameAgent;
import geometry.Polygon;
import geometry.Vector2D;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import actions.attacks.Attack;
import actions.attacks.AttackInfo;
import actions.attacks.BowAttack;
import actions.attacks.RangedAttack;

public class Bow extends Weapon {

	BufferedImage image;
	
	public Bow(GameAgent owner) {
		super(owner,new AttackInfo[]{
			new BowAttack(new RangedProjectile(new Vector2D(0,10),new Polygon(new double[]{15,45,15},new double[]{5,5,-5},0,0),5,Math.PI/4,20,100),new DieRoll(3,2)),
			new BowAttack(new RangedProjectile(new Vector2D(0,10),new Polygon(new double[]{15,45,15},new double[]{5,5,-5},0,0),5,Math.PI/4,20,100),new DieRoll(3,2)),
			new BowAttack(new RangedProjectile(new Vector2D(0,10),new Polygon(new double[]{15,45,15},new double[]{5,5,-5},0,0),5,Math.PI/4,20,100),new DieRoll(3,2))
		});
		try {
			image = ImageIO.read(new File("res/arrow.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void renderSprite(Graphics g,int x,int y){
		Graphics2D g2=(Graphics2D) g;
		g2.scale(5, 5);
		g2.drawImage(image, x/5+1, y/5+4, null);
		g2.scale(1/5.0, 1/5.0);
	}

	@Override
	public boolean isRanged() {
		// TODO Auto-generated method stub
		return true;
	}
}
