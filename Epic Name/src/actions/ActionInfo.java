package actions;

import world.World;
import entities.GameAgent;

/**
 *Holds the information for an action
 */
public abstract class ActionInfo {
	
	public abstract Action createAction(GameAgent doer,World world);
	
}
