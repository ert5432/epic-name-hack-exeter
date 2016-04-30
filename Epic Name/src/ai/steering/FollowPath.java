package ai.steering;

import entities.GameAgent;
import geometry.Vector2D;

public class FollowPath extends Seek {

	private Path path;
	private double pathOffset=.1;
	private double currentParam;
	private double predictTime=0.1;
	
	public FollowPath(GameAgent character, Path path) {
		super(character, null);
		this.path=path;
		currentParam=-1;
	}
	
	public SteeringOutput getSteering(){
		Vector2D futurePosition=character.position.add(character.getVelocity().mult(predictTime));
		currentParam=path.getParam(futurePosition, currentParam);
		double targetParam=currentParam+pathOffset;
		if(targetParam>path.getTotalDistance()){
			targetParam=pathOffset;
			currentParam=0;
		}
		target=path.getPosition(targetParam);
		return super.getSteering();
	}

}
