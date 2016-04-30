package entities;

import geometry.Shape;
import geometry.Vector2D;

import java.awt.Color;
import java.awt.Graphics;

import graphics.Renderable;
import world.World;

public class GameEntity extends MobileEntity implements Renderable{

	public Vector2D heading;
	public Shape shape;
	public Color c;
	public World world;
	protected double id;
	
	public GameEntity(Vector2D position,Shape shape,World world,double mass,double maxSpeed,
			double maxTurnSpeed, double maxForce){
		super(position,new Vector2D(0,0),mass,maxSpeed,maxTurnSpeed,
				maxForce);
		heading=new Vector2D(1,0);
		this.shape=shape;
		shape.setPosition(position);
		this.world=world;
		c=Color.blue;
	}
	
	public GameEntity(double x,double y,Shape shape,World world,double mass,double maxSpeed,
			double maxTurnSpeed,double maxForce){
		super(new Vector2D(x,y),new Vector2D(0,0),mass,maxSpeed,maxTurnSpeed,
				maxForce);
		heading=new Vector2D(1,0);
		this.shape=shape;
		shape.setPosition(position);
		id=Math.random();
		this.world=world;
		c=Color.blue;
	}
	
	public GameEntity(Vector2D position,Shape shape,World world,double mass,double maxSpeed,
			double maxTurnSpeed, double maxForce,Color c){
		super(position,new Vector2D(0,0),mass,maxSpeed,maxTurnSpeed,
				maxForce);
		heading=new Vector2D(1,0);
		this.shape=shape;
		shape.setPosition(position);
		this.world=world;
		this.c=c;
	}
	
	public GameEntity(double x,double y,Shape shape,World world,double mass,double maxSpeed,
			double maxTurnSpeed,double maxForce,Color c){
		super(new Vector2D(x,y),new Vector2D(0,0),mass,maxSpeed,maxTurnSpeed,
				maxForce);
		heading=new Vector2D(1,0);
		this.shape=shape;
		shape.setPosition(position);
		id=Math.random();
		this.world=world;
		this.c=c;
	}
	
	public void setHeading(Vector2D heading){
		this.heading=heading;
	}
	
	public void update(double time){
		super.update(time);
		shape.setPosition(position);
	}

	public Vector2D getHeading(){
		return heading.normalize();
	}
	
	public double getSpeed(){
		return velocity.magnitude();
	}
	
	public boolean contains(Shape s){
		return shape.intersects(s);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(c);
		shape.render(g);
	}
}

