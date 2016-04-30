package weapons.entities;

import java.awt.Color;
import java.util.ArrayList;

import entities.GameAgent;
import entities.GameEntity;
import world.World;
import geometry.Shape;
import geometry.Vector2D;

public abstract class WeaponEntity extends GameEntity{

	protected GameAgent owner;
	protected int coolDown;
	protected int damage;
	protected ArrayList<GameAgent> lastHit;
	protected boolean hasHit;
	protected boolean mobile;
	protected int duration;
	
	public WeaponEntity(Shape shape,int coolDown,int duration,Color color,boolean mobile){
		super(new Vector2D(0,0), shape,null,1,1,1,1);
		this.owner=null;
		this.coolDown=coolDown;
		this.duration=duration;
		c=color;
		hasHit=false;
		this.mobile=mobile;
	}
	
	public WeaponEntity(double x,double y,Shape shape,World world,GameAgent owner,int coolDown,int duration,int damage,Color color,boolean mobile){
		super(x,y,shape,world,1,1,1,1);
		this.owner=owner;
		this.damage=damage;
		this.coolDown=coolDown;
		this.duration=duration;
		c=color;
		hasHit=false;
	}
	
	public WeaponEntity(Vector2D position,Shape shape,World world,GameAgent owner,int coolDown,int duration,int damage,Color color,boolean mobile){
		super(position,shape,world,1,1,1,1);
		this.owner=owner;
		this.damage=(int)Math.max(damage,0);
		this.coolDown=coolDown;
		this.duration=duration;
		c=color;
		hasHit=false;
		this.mobile=mobile;
	}
	
	public void update(double time){
		if(duration<=0)
			world.removeEntity(this);
		if(mobile)
			super.update(time);
		updateLastHit();
		duration--;
	}
	
	public abstract void addCopytoWorld(World world,Vector2D position,GameAgent owner,int damage);
	
	public int getCoolDown(){
		return coolDown;
	}
	
	public void updateLastHit(){
		lastHit=world.findAllIntersectedAgents(shape, owner);
	}
	
	public boolean getHasHit(){
		return hasHit;
	}
	
	public void setHasHit(boolean hasHit){
		this.hasHit=hasHit;
	}
	
	public void setMass(double mass){
		this.mass=mass;
	}
}
