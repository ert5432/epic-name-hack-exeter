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
	protected Weapon weapon;
	private ActionInfo[] actionList=new ActionInfo[0];
	protected Action curAction=null;
	private boolean dead;
	public boolean phasing=false;
	
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
		if(!phasing)
			world.handleCollisions(this,time);
		
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
		if(!velocity.isZeroed()){
		if(velocity.magnitudeSq()<0.01)
			return;
		double theta=heading.angleTo(velocity);
		if(Math.abs(theta)<0.00001||Double.isNaN(theta)){
			return;
		}
		if(Math.abs(theta)>maxTurnSpeed*time){
			theta=maxTurnSpeed*Math.signum(theta)*time;
		}
		setHeading(heading.rotate(theta));
		shape.rotate(theta);
		}
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
		if(weapon!=null)
			loadActionList(w.getAttacks());
		else
			loadActionList(new ActionInfo[0]);
	}
	
	public Weapon getWeapon(){
		return weapon;
	}
	
	public void loadActionList(ActionInfo[] list){
		actionList=list;
	}
	
	public void act(int i){
		if(canAct()&&i>=0&&i<actionList.length){
			curAction=actionList[i].createAction(this, world);
			curAction.begin();
		}
	}
	
	public boolean canAct(){
		return curAction==null&&!isDead();
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
