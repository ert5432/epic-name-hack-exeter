 package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import graphics.Animation;

import graphics.*;

import stats.Stats;
import geometry.Shape;
import geometry.Vector2D;
import world.World;

public class Player extends GameAgent{

	public static final boolean RIGHT = true,LEFT=false;
	private Vector2D target;
	
	//dwang
	private Sprite sprite = new Sprite("sheet1", 12);
	private BufferedImage[] standing = {sprite.getSprite(2, 0) };
	private Animation standingAnim = new Animation(standing, 20);
	
	private BufferedImage[] walking= {sprite.getSprite(0, 0),sprite.getSprite(1, 0) };
	private Animation walkingAnim = new Animation(walking, 15);
	
	
	public Player(Vector2D position,Shape shape,World world,Stats stats){
		super(position,shape,world,stats);
		target=null;
		standingAnim.start();
		walkingAnim.start();
	}
	
	public Player(double x,double y,Shape shape,World world,Stats stats){
		super(x,y,shape,world,stats);
		target=null;
		standingAnim.start();
		walkingAnim.start();
	}
	
	public void update(double time){
		standingAnim.update();
		walkingAnim.update();
		super.update(time);
		/*if(velocity.magnitude()>1){
			applyForce(velocity.normalize().negative().mult(mass));
		}
		else if(!velocity.isZeroed()){
			velocity.zero();
		}*/
	}

	public void rotateHeadingtoVelocity(double time){
		if(target==null)
			return;
		Vector2D targetHeading=target.sub(position);
		double theta=heading.angleTo(targetHeading);
		if(Math.abs(theta)<0.00001||Double.isNaN(theta)){
			return;
		}
		if(Math.abs(theta)>maxTurnSpeed*time){
			theta=maxTurnSpeed*Math.signum(theta)*time;
		}
		setHeading(heading.rotate(theta));
		shape.rotate(theta);
	}

	public void setTarget(Vector2D target) {
		this.target=target;
	}
	
	public void render(Graphics g){
		//super.render(g);
		
	
		Graphics2D g2d = (Graphics2D)(g);
		g2d.rotate(rotation,position.x,position.y);
		g2d.scale(4, 4);
		
		if(velocity.isZeroed()){
			g2d.drawImage(standingAnim.getSprite(), (int)((position.x-20)/4.0), (int)((position.y-24)/4.0), null);
		}else{
			g2d.drawImage(walkingAnim.getSprite(), (int)((position.x-20)/4.0), (int)((position.y-24)/4.0), null);
		}
		
		
		
		g2d.scale(.25, .25);
		g2d.rotate(-rotation,position.x,position.y);
	}
	
	public void rotate(boolean direction,double time){
		double theta;
		if(direction==RIGHT)
			theta=maxTurnSpeed*time;
		else
			theta=-maxTurnSpeed*time;
		setHeading(heading.rotate(theta));
		shape.rotate(theta);
	}
}
