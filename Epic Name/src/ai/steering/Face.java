package ai.steering;

import entities.GameAgent;
import geometry.Vector2D;

public class Face implements Behavior{

	public GameAgent character;
	public Vector2D target;
	public GameAgent targetCharacter;
	
	public Face(GameAgent character,Vector2D target){
		this.character=character;
		this.target=target;
		
	}
	
	public Face(GameAgent character,GameAgent target){
		this.character=character;
		targetCharacter=target;
		this.target=null;
	}
	
	public SteeringOutput getSteering() {
		SteeringOutput output=new SteeringOutput();
		
		if(character!=null)
			target=targetCharacter.position;
		if(target!=null){
			output.angular=character.heading.angleTo(target.sub(character.position));
		}
		
		
		return output;
	}

}
