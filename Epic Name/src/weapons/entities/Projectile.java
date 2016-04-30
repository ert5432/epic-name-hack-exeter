package weapons.entities;

import java.awt.Color;

import entities.GameAgent;
import world.World;
import geometry.Shape;
import geometry.Vector2D;

public class Projectile extends WeaponEntity {
	

	public Projectile(Shape shape,int coolDown,int duration){
		super(shape,coolDown,duration,Color.green,true);
	}
	
	public Projectile(double x, double y, Shape shape, World world,GameAgent owner,int coolDown,int duration,int damage) {
		super(x, y, shape, world, owner,coolDown,duration,damage,Color.green,true);
	}
	
	public Projectile(Vector2D position, Shape shape, World world,GameAgent owner, int coolDown,int duration,int damage) {
		super(position, shape, world, owner,coolDown,duration,damage, Color.green,true);
	}
		
	public Projectile(Vector2D position,Vector2D initialDirection,double velocity,Shape shape, World world,GameAgent owner,int coolDown,int duration,int damage){
		super(position, shape, world, owner,coolDown,duration,damage,Color.green,true);
		applyForce(initialDirection.normalize().mult(velocity));
	}
	
	public void update(double time){
		super.update(time);
		rotateHeadingtoVelocity(time);
		boolean flag=world.findIntersectedWall(shape.translate(velocity))==null;
		if(flag){
			super.update(time);
			shape.setPosition(position);
			rotateHeadingtoVelocity(time);
			if(!lastHit.isEmpty()){
				lastHit.get(0).damage(damage);
				world.removeEntity(this);
			}
		}
		else
			world.removeEntity(this);		
	}
	
	public void rotateHeadingtoVelocity(double time){
		double theta=heading.angleTo(velocity);
		heading=heading.rotate(theta);
		shape.rotate(theta);
	}
	
	public void addCopytoWorld(World world,Vector2D position,GameAgent owner,int damage){
		world.addEntity(new Projectile(position,shape.clone(),world,owner,coolDown,duration,damage));
	}
	
	public void addCopytoWorld(World world,Vector2D position,Vector2D direction,double velocity,GameAgent owner,int damage){
		world.addEntity(new Projectile(position,direction,velocity,shape.clone(),world,owner,coolDown,duration,damage));
	}
}

