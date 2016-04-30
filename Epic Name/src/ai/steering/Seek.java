package ai.steering;

import entities.GameAgent;
import geometry.Vector2D;

public class Seek implements Behavior{

	public GameAgent character;
	public Vector2D target;
	
	public Seek(GameAgent character,Vector2D target){
		this.character=character;
		this.target=target;
	}
	
	public SteeringOutput getSteering() {
		SteeringOutput output=new SteeringOutput();
		output.linear=target.sub(character.position);
		output.linear.normalize();
		output.linear.imult(character.getMaxAcceleration());
		return output;
	}
	
}
