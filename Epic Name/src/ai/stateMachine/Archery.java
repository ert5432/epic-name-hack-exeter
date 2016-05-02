package ai.stateMachine;

import ai.steering.Behavior;
import ai.steering.Pursue;
import ai.steering.SteeringOutput;
import entities.GameAgent;

public class Archery extends State {

	GameAgent target;
	Behavior steer;
	
	public Archery(GameAgent agent, StateMachine sm,GameAgent target) {
		super(agent, sm);
		this.target=target;
	}

	@Override
	public void enterState() {
		// TODO Auto-generated method stub
		steer=new Pursue(agent,target);
		agent.setMaxSpeed(2);
	}

	@Override
	public void execute() {
		if(agent.position.distanceTo(target.position)<300){
			//agent.rotation=agent.position.angleTo(target.getPosition());
			if(agent.canAct())
				agent.act(1);
		}
		else{
			SteeringOutput output=steer.getSteering();
			agent.move(output.linear);
		}
		if(agent.position.distanceTo(target.position)>1000){
			sm.changeState(new WaitForPlayer(agent,sm,target));
		}
	}

	@Override
	public void exitState() {
		// TODO Auto-generated method stub
		agent.setMaxSpeed(GameAgent.normalMaxSpeed);
	}

}
