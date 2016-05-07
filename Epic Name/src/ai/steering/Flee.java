package ai.steering;

import entities.GameAgent;
import geometry.Vector2D;

public class Flee extends FaceVelocity{

	public Vector2D target;
	
	public Flee(GameAgent character,Vector2D target){
		super(character);
		this.target=target;
	}
	
	public SteeringOutput getSteering() {
		SteeringOutput output=super.getSteering();
		output.linear=character.position.sub(target);
		output.linear.normalize();
		output.linear.imult(character.getMaxAcceleration());
		return output;
	}
	
}
