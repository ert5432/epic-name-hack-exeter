package ai.stateMachine;

import ai.steering.Behavior;
import ai.steering.FollowPath;
import ai.steering.Path;
import ai.steering.SteeringOutput;
import entities.GameAgent;
import geometry.Vector2D;

public class Patrolling extends State{

	Path patrol;
	Behavior steer;
	
	public Patrolling(GameAgent agent,StateMachine sm,Path patrol){
		super(agent,sm);
		this.patrol=patrol;
	}

	public void enterState() {
		steer=new FollowPath(agent,patrol);
		agent.setMaxSpeed(2);
	}

	public void execute() {
		SteeringOutput output=steer.getSteering();
		agent.move(output.linear);
	}

	public void exitState() {
		agent.setMaxSpeed(GameAgent.normalMaxSpeed);
	}
	
}
