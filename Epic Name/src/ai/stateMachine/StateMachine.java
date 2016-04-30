package ai.stateMachine;

import entities.GameAgent;

public class StateMachine {

	State state;
	GameAgent agent;
	
	public StateMachine(GameAgent agent){
		this.agent=agent;
		state=null;
	}
	
	public void act(){
		if(state!=null)
			state.execute();
	}
	
	public void changeState(State newState){
		state.exitState();
		newState.enterState();
		this.state=newState;
	}
	
}
