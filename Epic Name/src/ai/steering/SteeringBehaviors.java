package ai.steering;

import entities.GameAgent;
import geometry.Vector2D;

public class SteeringBehaviors {

	private GameAgent agent;
	private Vector2D target;
	private GameAgent pursuer;
	private GameAgent evader;
	private boolean seekOn,fleeOn,arriveOn,pursueOn,evadeOn;
	
	public SteeringBehaviors(GameAgent agent){
		this.agent=agent;
		seekOn=false;
		fleeOn=false;
		arriveOn=false;
		pursueOn=false;
		evadeOn=false;
	}
	
	public Vector2D calculate(){
		if(evader!=null&&evader.isDead()){
			pursueOff();
		}
		if(pursuer!=null&&pursuer.isDead()){
			evadeOff();
		}
		if((target==null&&(arriveOn||seekOn||fleeOn))||(pursuer==null&&evadeOn)||(evader==null&&pursueOn))
			return new Vector2D(0,0);
		else if(arriveOn)
			return arrive(target,slow);
		else if(seekOn)
			return seek(target);
		else if(fleeOn)
			return flee(target);
		else if(pursueOn)
			return pursue(evader);
		else if(evadeOn)
			return evade(pursuer);
		else
			return new Vector2D(0,0);
	}
	
	public void setTarget(Vector2D target){
		this.target=target;
	}
	
	public Vector2D seek(Vector2D target){
		Vector2D wantedVelocity=target.sub(agent.getPosition()).truncate(agent.getMaxVelocity());
		return wantedVelocity.sub(agent.getVelocity());
	}
	
	public Vector2D flee(Vector2D target){
		Vector2D wantedVelocity=agent.getPosition().sub(target).truncate(agent.getMaxVelocity());
		return wantedVelocity.sub(agent.getVelocity());
	}
	
	//Decelerations
	private static final int slow=3,normal=2,fast=1;
	
	public Vector2D arrive(Vector2D target,int deceleration){
		Vector2D toTarget=agent.getPosition().vectorTo(target);
		double dist=toTarget.magnitude();
		if(dist>0){
			final double tweaker=4;
			double speed=dist/(deceleration*tweaker);
			speed=Math.min(speed,agent.getMaxVelocity());
			Vector2D wantedVelocity=toTarget.div(dist).mult(speed);
			return wantedVelocity.sub(agent.getVelocity());
		}
		return new Vector2D(0,0);
	}
	
	public Vector2D pursue(GameAgent evader){
		Vector2D toEvader=evader.getPosition().sub(agent.getPosition());
		double relativeHeading=agent.getHeading().dot(evader.getHeading());
		if(toEvader.dot(agent.getHeading())>0&&relativeHeading<-0.95)//acos(0.95)=18 degrees, close to facing
				return seek(evader.getPosition());
		else{
			double timeLookedAhead=toEvader.magnitude()/(agent.getMaxVelocity()+evader.getSpeed());
			return seek(evader.getPosition().add(evader.getVelocity().mult(timeLookedAhead)));
		}
	}
	
	public Vector2D evade(GameAgent pursuer){
		Vector2D toPursuer=pursuer.getPosition().sub(agent.getPosition());
		double timeLookedAhead=toPursuer.magnitude()/(agent.getMaxVelocity()+pursuer.getSpeed());
		return flee(pursuer.getPosition().add(pursuer.getVelocity().mult(timeLookedAhead)));
	}
	
	public void seekOn(){
		seekOn=true;
	}
	
	public void seekOff(){
		seekOn=false;
	}
	
	public void fleeOn(){
		fleeOn=true;
	}
	
	public void fleeOff(){
		fleeOn=false;
	}
	
	public void arriveOn(){
		arriveOn=true;
	}
	
	public void arriveOff(){
		arriveOn=false;
	}
	
	public void pursueOn(GameAgent evader){
		this.evader=evader;
		pursueOn=true;
	}
	
	public void pursueOff(){
		pursueOn=false;
	}
	
	public void evadeOn(GameAgent pursuer){
		this.pursuer=pursuer;
		evadeOn=true;
	}
	
	public void evadeOff(){
		pursuer=null;
		evadeOn=false;
	}
}
