package ai.stateMachine;

import ai.steering.Behavior;
import ai.steering.Pursue;
import ai.steering.SteeringOutput;
import entities.GameAgent;

public class Charging extends State {

	GameAgent target;
	Behavior steer;
	public int range=100;
	
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
		agent.turn(output.angular);
		if(agent.canAct()&&agent.position.distanceTo(target.position)<range){
			agent.act(1);
		}
		if(agent.position.distanceTo(target.position)>1000){
			sm.changeState(new WaitForPlayer(agent,sm,target,range));
		}
	}

	@Override
	public void exitState() {
		// TODO Auto-generated method stub
		agent.setMaxSpeed(GameAgent.normalMaxSpeed);
	}

}
