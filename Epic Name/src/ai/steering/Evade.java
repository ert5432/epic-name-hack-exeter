package ai.steering;

import entities.GameAgent;
import geometry.Vector2D;

public class Evade extends Flee {

	private double maxPrediction;
		
	protected GameAgent target;
		
	public Evade(GameAgent character,GameAgent target){
		super(character,null);
		this.target=target;
		maxPrediction=5;
	}
		
	public SteeringOutput getSteering(){
		Vector2D direction=target.position.sub(character.position);
		double dist=direction.magnitude();
		
		double speed=character.getVelocity().magnitude();
		double prediction;
		if(speed<=dist/maxPrediction)
			prediction=maxPrediction;
		else
			prediction=dist/speed;
		super.target=target.position.clone();
		super.target.iadd(target.getVelocity().mult(prediction));
		return super.getSteering();
	}
	
}
