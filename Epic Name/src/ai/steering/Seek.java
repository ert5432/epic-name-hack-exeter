package ai.steering;

import entities.GameAgent;
import geometry.Vector2D;

public class Seek extends FaceVelocity{

	public Vector2D target;
	
	public Seek(GameAgent character,Vector2D target){
		super(character);
		this.target=target;
	}
	
	public SteeringOutput getSteering() {
		SteeringOutput output=super.getSteering();
		output.linear=target.sub(character.position);
		output.linear.normalize();
		output.linear.imult(character.getMaxAcceleration());
		return output;
	}
	
}
