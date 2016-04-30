package weapons.entities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import world.World;
import entities.GameAgent;
import geometry.PartialArc;
import geometry.Shape;
import geometry.Vector2D;

public class Wave extends WeaponEntity {

	public HashSet<GameAgent> allHit;
	public double waveAngle;
	public double startRadius;
	public double endRadius;
	public double thickness;
	public double waveSpeed;
	public PartialArc wave;
	
	public Wave(World world,double waveAngle,double startRadius,double endRadius,double thickness,double waveSpeed,Vector2D center,GameAgent owner,int damage,Color color) {
		super(center, new PartialArc(center,startRadius,startRadius-thickness,waveAngle,owner.heading.angleToOrigin()),world,owner,(int)((endRadius-startRadius)/waveSpeed)+5,(int)((endRadius-startRadius)/waveSpeed),damage,color,false);
		this.waveAngle=waveAngle;
		this.startRadius=startRadius;
		this.endRadius=endRadius;
		this.thickness=thickness;
		this.waveSpeed=waveSpeed;
		allHit=new HashSet<GameAgent>();
		wave=(PartialArc)shape;
	}
	
	public Wave(double waveAngle,double startRadius,double endRadius,double thickness,double waveSpeed,Color color){
		super(new PartialArc(0,0,startRadius,startRadius-thickness,waveAngle,0),(int)((endRadius-startRadius)/waveSpeed)+5,(int)((endRadius-startRadius)/waveSpeed),color,false);
		this.waveAngle=waveAngle;
		this.startRadius=startRadius;
		this.endRadius=endRadius;
		this.thickness=thickness;
		this.waveSpeed=waveSpeed;
		wave=(PartialArc)shape;
	}

	@Override
	public void addCopytoWorld(World world, Vector2D position, GameAgent owner,int damage) {
		world.addEntity(new Wave(world,waveAngle,startRadius,endRadius,thickness,waveSpeed,position,owner,damage,c));
	}
	
	public void addCopytoWorld(World world, Vector2D position,GameAgent owner,double waveSpeed,double maxRange,int damage){
		world.addEntity(new Wave(world,waveAngle,startRadius,maxRange,thickness,waveSpeed,position,owner,damage,c));
	}
	
	@Override
	public void update(double time){
		super.update(time);
		for(GameAgent e:lastHit){
			if(!allHit.contains(e)){
				e.damage(damage);
				System.out.println(endRadius+"       sdfg    "+damage);
				allHit.add(e);
			}
		}
		wave.expand(waveSpeed*time);		
	}
	
	public double getNormalRange(){
		return endRadius;
	}
	
	public double getNormalSpeed(){
		return waveSpeed;
	}
}
