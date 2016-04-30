package weapons;

import stats.DieRoll;
import weapons.entities.Stab;
import weapons.entities.Swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import actions.attacks.Attack;
import actions.attacks.AttackInfo;
import actions.attacks.MeleeAttackInfo;
import entities.GameAgent;
import geometry.Polygon;

public class Sword extends Weapon{

	BufferedImage image;
	
	public Sword(GameAgent owner) {
		super(owner,new AttackInfo[]{
			new MeleeAttackInfo(new Swing(new Polygon(new double[]{15,45,15},new double[]{5,5,-5},0,0),-Math.PI,20),new DieRoll(3,2),"str","str"),
			new MeleeAttackInfo(new Stab(new Polygon(new double[]{15,45,15},new double[]{5,5,-5}),20,12),new DieRoll(3,2),"str","str"),
			new MeleeAttackInfo(new Swing(new Polygon(new double[]{15,45,15},new double[]{5,5,-5}),Math.PI,20),new DieRoll(3,2),"str","str")
		});
		try {
			image = ImageIO.read(new File("res/sword.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void renderSprite(Graphics g,int x,int y){
		Graphics2D g2=(Graphics2D) g;
		g2.drawImage(image, x, y, 80, 80, null);
	}

	@Override
	public boolean isRanged() {
		// TODO Auto-generated method stub
		return false;
	}
}
