package ai.steering;

import entities.GameAgent;
import geometry.Vector2D;

public class Arrive extends FaceVelocity{

	public Vector2D target;
	
	public double slowRadius;
	public double targetRadius;
	
	public double maxSpeed;
	
	public Arrive(GameAgent character, Vector2D target) {
		super(character);
		this.target=target;
		slowRadius=5;
		targetRadius=.5;
		maxSpeed=character.getMaxVelocity();
	}
	
	public SteeringOutput getSteering(){
		SteeringOutput output=super.getSteering();
		Vector2D direction=target.sub(character.position);
		double distSq=direction.magnitudeSq();
		System.out.println(target);
		System.out.println(character.position);
		System.out.println(distSq);
		output.linear=new Vector2D(0,0);
		if(distSq<targetRadius*targetRadius)
			return output;
		
		double targetSpeed;
		if(distSq>slowRadius*slowRadius)
			targetSpeed=maxSpeed;
		else
			targetSpeed=maxSpeed*Math.sqrt(distSq)/(slowRadius);
		
		Vector2D targetVelocity=direction.normalize();
		targetVelocity.imult(targetSpeed);
		
		output.linear=targetVelocity.sub(character.getVelocity());
		output.linear.idiv(.5);
		
		output.linear=output.linear.truncate(character.getMaxAcceleration());
		System.out.println(output.linear);
		return output;
	}

}
