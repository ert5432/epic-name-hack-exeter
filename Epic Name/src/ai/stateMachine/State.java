package ai.stateMachine;

import entities.GameAgent;

public abstract class State {

	GameAgent agent;
	StateMachine sm;
	
	public State(GameAgent agent,StateMachine sm){
		this.agent=agent;
		this.sm=sm;
	}
	
	public abstract void enterState();
	
	public abstract void execute();
	
	public abstract void exitState();
	
}
