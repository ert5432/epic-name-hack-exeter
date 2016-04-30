package ai.stateMachine;

import entities.GameAgent;

public class WaitForPlayer extends State {

	private GameAgent target;

	public WaitForPlayer(GameAgent agent, StateMachine sm,GameAgent target) {
		super(agent, sm);
		this.target=target;
	}

	@Override
	public void enterState() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute() {
		if(target!=null)
		if(agent.position.distanceTo(target.position)<1000){
			if(agent.getWeapon().isRanged())
				sm.changeState(new Archery(agent,sm,target));
		}
	}

	@Override
	public void exitState() {
		// TODO Auto-generated method stub

	}

}
