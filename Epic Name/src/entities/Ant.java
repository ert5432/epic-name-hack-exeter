package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ai.stateMachine.Charging;
import ai.stateMachine.StateMachine;
import ai.stateMachine.WaitForPlayer;
import geometry.Circle;
import geometry.Shape;
import graphics.Animation;
import graphics.Sprite;
import stats.Stats;
import weapons.PowerRod;
import world.World;

public class Ant extends StateAgent{
	
	private Sprite sprite = new Sprite("ant", 14);
	private BufferedImage[] standing = {sprite.getSprite(2, 0) };
	private Animation standingAnim = new Animation(standing, 20);
	
	private BufferedImage[] walking= {sprite.getSprite(0, 0),sprite.getSprite(1, 0) };
	private Animation walkingAnim = new Animation(walking, 15);

	public Ant(double x, double y, World world) {
		super(x, y, new Circle(24), world, new Stats(15,15,15,15,30));
		walkingAnim.start();
		standingAnim.start();
		setWeapon(new PowerRod(this));
		StateMachine sm=getStateMachine();
		sm.changeState(new WaitForPlayer(this,sm,world.player));
	}
	
	public void update(double time){
		super.update(time);
		
		walkingAnim.update();
		
	}

	public void render(Graphics g){
		Graphics2D g2d = (Graphics2D)(g);
		g2d.rotate(rotation, position.x, position.y);
		g2d.scale(4, 4);

		if(this.velocity.isZeroed()){
			g2d.drawImage(standingAnim.getSprite(), (int) ((position.x - 20) / 4.0), (int) ((position.y - 24) / 4.0), null);
		}else{
			g2d.drawImage(walkingAnim.getSprite(), (int) ((position.x - 20) / 4.0), (int) ((position.y - 24) / 4.0), null);
		}
		
		
		g2d.scale(.25, .25);
		g2d.rotate(-rotation,position.x,position.y);
	}
	
}
