 package entities;

import java.awt.Color;
import java.awt.Graphics;

import stats.Stats;
import geometry.Shape;
import geometry.Vector2D;
import world.World;

public class Player extends GameAgent{

	public static final boolean RIGHT = true,LEFT=false;
	private Vector2D target;
	public double rotation;
	
	public Player(Vector2D position,Shape shape,World world,Stats stats){
		super(position,shape,world,stats);
		target=null;
		rotation=0;
	}
	
	public Player(double x,double y,Shape shape,World world,Stats stats){
		super(x,y,shape,world,stats);
		target=null;
		rotation=0;
	}
	
	public void update(double time){
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
		heading=heading.rotate(theta);
		shape.rotate(theta);
	}

	public void setTarget(Vector2D target) {
		this.target=target;
	}
	
	public void render(Graphics g){
		super.render(g);
	}
	
	public void rotate(boolean direction,double time){
		double theta;
		if(direction==RIGHT)
			theta=maxTurnSpeed*time;
		else
			theta=-maxTurnSpeed*time;
		heading=heading.rotate(theta);
		shape.rotate(theta);
		rotation+=theta;
		rotation=Shape.modulus(rotation,Math.PI*2);
	}
}
