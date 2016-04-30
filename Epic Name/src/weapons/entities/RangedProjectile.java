package weapons.entities;

import world.World;
import entities.GameAgent;
import geometry.Shape;
import geometry.Vector2D;

public class RangedProjectile extends Projectile{

	public double accuracyRange;
	private Vector2D relativePosition;
	
	public RangedProjectile(Vector2D relativePosition,Vector2D direction,Shape shape, World world,GameAgent owner,
			double maxSpeed,double accuracyRange,int coolDown,int flyTime,int damage) {
		super(owner.position.add(relativePosition),direction,maxSpeed,shape, world,owner,coolDown,flyTime,damage);
		this.accuracyRange=accuracyRange;
		this.maxVelocity=maxSpeed;
		heading=direction.clone();
		shape.rotate(-heading.angleTo(new Vector2D(1,0)));
		velocity=heading.mult(maxSpeed);
	}

	public RangedProjectile(Vector2D relativePosition,Shape shape,double maxSpeed,
			double accuracyRange,int coolDown,int flyTime) {
		super(shape,coolDown,flyTime);
		this.relativePosition=relativePosition;
		this.accuracyRange=accuracyRange;
		this.maxVelocity=maxSpeed;
	}
	
	public void addCopytoWorld(World world,Vector2D position,GameAgent owner,int damage){
		world.addEntity(new RangedProjectile(relativePosition.clone(),owner.heading.clone(),shape.clone(),world,owner,this.maxVelocity,
				accuracyRange,coolDown,duration,damage));
	}
	
	public void addCopytoWorld(World world,Vector2D position,Vector2D direction,double velocity,GameAgent owner,int damage){
		world.addEntity(new RangedProjectile(relativePosition.clone(),direction,shape.clone(),world,owner,velocity,
				accuracyRange,coolDown,duration,damage));
	}
	
	public double getAccuracyRange(){
		return accuracyRange;
	}
}
