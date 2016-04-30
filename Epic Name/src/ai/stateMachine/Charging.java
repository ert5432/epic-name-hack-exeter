package ai.stateMachine;

import ai.steering.Behavior;
import ai.steering.Pursue;
import ai.steering.SteeringOutput;
import entities.GameAgent;

public class Charging extends State {

	GameAgent target;
	Behavior steer;
	
	public Charging(GameAgent agent, StateMachine sm,GameAgent target) {
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
		SteeringOutput output=steer.getSteering();
		agent.move(output.linear);
		if(agent.canAct()&&agent.position.distanceTo(target.position)<100){
			agent.act(0);
		}
	}

	@Override
	public void exitState() {
		// TODO Auto-generated method stub
		agent.setMaxSpeed(GameAgent.normalMaxSpeed);
	}

}
