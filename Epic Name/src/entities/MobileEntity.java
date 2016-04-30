package entities;

import geometry.Vector2D;

public class MobileEntity extends PositionEntity{

	protected Vector2D velocity;
	protected Vector2D acceleration=new Vector2D(0,0);
	protected double maxVelocity;
	protected double maxTurnSpeed;
	protected double maxAcceleration;
	protected double maxForce;
	protected double mass;
	
	public MobileEntity(double x,double y,double mass){
		super(new Vector2D(x,y));
		velocity=new Vector2D(0,0);
		this.mass=mass;
	}
	
	public MobileEntity(double x,double y,Vector2D velocity,double mass){
		super(new Vector2D(x,y));
		this.velocity=velocity;
		this.mass=mass;
	}
	
	public MobileEntity(Vector2D position,double mass){
		super(position);
		velocity=new Vector2D(0,0);
		this.mass=mass;
	}
	
	public MobileEntity(Vector2D position,Vector2D velocity,double mass,double maxVelocity,
			double maxTurnSpeed,double maxForce){
		super(position);
		this.velocity=velocity;
		this.mass=mass;
		this.maxVelocity=maxVelocity;
		this.maxTurnSpeed=maxTurnSpeed;
		this.maxForce=maxForce;
		this.maxAcceleration=maxForce/mass;
	}

	public void update(double time){
		position.iadd(velocity.mult(time));
		velocity.iadd(acceleration.mult(time));
		acceleration.zero();
		if(maxVelocity>0)
			velocity=velocity.truncate(maxVelocity);
	}
	
	public void applyForce(Vector2D force){
		acceleration.iadd(force.div(mass));
	}
	
	public double getMass(){
		return mass;
	}
	
	public Vector2D getMomentum(){
		return velocity.mult(mass);
	}
	
	public Vector2D getVelocity(){
		return velocity;
	}
	
	public double getMaxVelocity(){
		return maxVelocity;
	}
}




