package entities;

import geometry.Shape;
import geometry.Vector2D;

import java.awt.Color;
import java.awt.Graphics;

import stats.Stats;
import actions.Action;
import actions.ActionInfo;
import weapons.Weapon;
import world.Wall;
import world.World;

public class GameAgent extends GameEntity{

	public static final double normalMaxSpeed=3,normalMaxTurnSpeed=Math.PI/36,normalMaxForce=15;
	private double id;
	protected int coolDown;
	public int hp;
	public Stats stats;
	private Weapon weapon;
	private ActionInfo[] actionList;
	protected Action curAction=null;
	private boolean dead;
	
	public GameAgent(Vector2D position,Shape shape,World world,Stats stats){
		super(position,shape,world,5,normalMaxSpeed,normalMaxTurnSpeed,
				normalMaxForce);
		coolDown=0;
		hp=stats.maxHp;
		this.stats=stats;
		dead=false;
		c=Color.green;
	}
	
	public GameAgent(double x,double y,Shape shape,World world,Stats stats){
		super(new Vector2D(x,y),shape,world,5,normalMaxSpeed,normalMaxTurnSpeed,
				normalMaxForce);
		id=Math.random();
		coolDown=0;
		hp=stats.maxHp;
		this.stats=stats;
		dead=false;
		c=Color.green;
	}
	
	public GameAgent(Vector2D position,Shape shape,World world,Stats stats,Color c){
		super(position,shape,world,5,normalMaxSpeed,normalMaxTurnSpeed,
				normalMaxForce,c);
		coolDown=0;
		hp=stats.maxHp;
		this.stats=stats;
		dead=false;
	}
	
	public GameAgent(double x,double y,Shape shape,World world,Stats stats,Color c){
		super(new Vector2D(x,y),shape,world,5,normalMaxSpeed,normalMaxTurnSpeed,
				normalMaxForce,c);
		id=Math.random();
		coolDown=0;
		hp=stats.maxHp;
		this.stats=stats;
		dead=false;
	}
	
	public void setMaxSpeed(double max){
		maxVelocity=max;
	}
	
	public void update(double time){
		Vector2D agentNormal=world.intersectAgentNormal(shape.translate(velocity),this);
		if(agentNormal!=null){
			Vector2D agentForce=agentNormal.normalize().mult(Math.abs(velocity.add(acceleration.mult(time)).dot(agentNormal.negative()))/agentNormal.magnitude()*mass*2);
			this.applyForce(agentForce);
		}
		Vector2D wallNormal=world.intersectsWallNormal(shape.translate(velocity));
		if(wallNormal!=null){
			Vector2D wallForce=wallNormal.normalize().mult(Math.abs(velocity.add(acceleration.mult(time)).dot(wallNormal.negative()))/wallNormal.magnitude()*mass*2);
			this.applyForce(wallForce);
		}
		super.update(time);
		shape.setPosition(position);
		rotateHeadingtoVelocity(time);
		if(velocity.magnitude()>1){
			applyForce(velocity.normalize().negative().mult(mass));
		}
		else if(!velocity.isZeroed()){
			applyForce(velocity.negative().mult(mass));
		}
		if(curAction!=null){
			curAction.execute();
		}
		
	}
	
	public void rotateHeadingtoVelocity(double time){
		if(velocity.magnitudeSq()<0.01)
			return;
		double theta=heading.angleTo(velocity);
		if(Math.abs(theta)<0.00001||Double.isNaN(theta)){
			return;
		}
		if(Math.abs(theta)>maxTurnSpeed*time){
			theta=maxTurnSpeed*Math.signum(theta)*time;
		}
		heading=heading.rotate(theta);
		shape.rotate(theta);
	}
	
	public void addCoolDown(int c){
		coolDown+=c;
	}
	
	public void damage(int dam){
		hp-=dam;
		if(hp<=0)
			die();
	}
	
	public void setWeapon(Weapon w){
		weapon=w;
		loadActionList(w.getAttacks());
	}
	
	public Weapon getWeapon(){
		return weapon;
	}
	
	public void loadActionList(ActionInfo[] list){
		actionList=list;
	}
	
	public void act(int i){
		if(curAction==null&&i>=0&&i<actionList.length&&!dead){
			curAction=actionList[i].createAction(this, world);
			curAction.begin();
		}
	}
	
	public void exitAction(){
		curAction=null;
	}

	private void die(){
		world.removeEntity(this);
		dead=true;
	}

	public World getWorld() {
		return world;
	}
	
	public boolean isDead(){
		return dead;
	}
	
	public void move(Vector2D move){
		if(curAction==null||curAction.isMobile()){
			if(move.magnitudeSq()>(maxAcceleration)*(maxAcceleration)){
				move.truncate(maxAcceleration);
			}
			applyForce(move.mult(mass));
		}
	}

	public double getMaxAcceleration() {
		return maxAcceleration;
	}

	public Action getCurAction() {
		return curAction;
	}
	
}
