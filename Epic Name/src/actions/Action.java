package actions;

import world.World;

import entities.GameAgent;

public abstract class Action {
	
	protected GameAgent doer;
	protected World world;
	protected long startTime;
	protected long endTime;
	
	public Action(GameAgent doer,World world){
		this.doer=doer;
		this.world=world;
	}
	
	/**
	 * When it is overrided, must 
	 */
	public void begin(){
		startTime=world.time;
		endTime=startTime+getDuration();
	}
	
	public void execute(){
		if(world.time>=endTime)
			end();
	}
	
	public void end(){
		doer.exitAction();
	}
	
	public abstract boolean isMobile();

	public abstract int getDuration();
}
