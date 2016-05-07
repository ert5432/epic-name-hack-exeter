package entities;

import java.awt.Color;

import ai.stateMachine.StateMachine;
import geometry.Shape;
import stats.Stats;
import world.World;

public class StateAgent extends GameAgent {

	private StateMachine sm;
	
	public StateAgent(double x, double y, Shape shape, World world, Stats stats) {
		super(x, y, shape, world, stats,Color.blue);
		sm=new StateMachine(this);
	}
	
	public void update(double time){
		super.update(time);
		sm.act();
	}
	
	public StateMachine getStateMachine(){
		return sm;
	}
	
	public void rotateHeadingToVelocity(){
		
	}

}