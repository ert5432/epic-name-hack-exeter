package ai.stateMachine;

import world.Wall;
import entities.GameAgent;
import geometry.Line;

public class WaitForPlayer extends State {

	private GameAgent target;
	boolean seenTarget=false;
	int range;
	
	
	public WaitForPlayer(GameAgent agent, StateMachine sm,GameAgent target) {
		super(agent, sm);
		this.target=target;
	}
	
	public WaitForPlayer(GameAgent agent, StateMachine sm,GameAgent target,int range) {
		super(agent, sm);
		this.target=target;
		this.range=range;
	}

	@Override
	public void enterState() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute() {
		if(target!=null){
			
		if(agent.position.distanceTo(target.position)<750&&!seenTarget){
			seenTarget=true;	
			Line l=new Line(agent.getPosition(),target.getPosition());
				loop:for(Wall w:agent.world.getWalls()){
					for(Line ol:w.getShape().toLines())
						if(l.intersects(ol)){
							seenTarget=false;
							break loop;	
					}
				}
		}
		else if(agent.position.distanceTo(target.position)>1000&&seenTarget)
			seenTarget=false;
			
			if(agent.getWeapon().isRanged()&&seenTarget){
				Archery arch=new Archery(agent,sm,target);
				arch.range=range;
				sm.changeState(arch);
			}
		}
	}

	@Override
	public void exitState() {
		// TODO Auto-generated method stub

	}

}
