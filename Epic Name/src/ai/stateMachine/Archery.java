package ai.stateMachine;

import ai.steering.Behavior;
import ai.steering.Face;
import ai.steering.Pursue;
import ai.steering.SteeringOutput;
import entities.GameAgent;

public class Archery extends State {

	GameAgent target;
	Behavior pursue;
	Behavior face;
	public int range=300;
	
	public Archery(GameAgent agent, StateMachine sm,GameAgent target) {
		super(agent, sm);
		this.target=target;
	}

	@Override
	public void enterState() {
		// TODO Auto-generated method stub
		pursue=new Pursue(agent,target);
		face=new Face(agent,target);
		agent.setMaxSpeed(2);
	}

	@Override
	public void execute() {
		if(agent.position.distanceTo(target.position)<range){
			agent.turn(face.getSteering().angular);
			if(agent.canAct())
				agent.act(1);
		}
		else{
			SteeringOutput output=pursue.getSteering();
			agent.move(output.linear);
			agent.turn(output.angular);
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
