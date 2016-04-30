package entities;

import geometry.Vector2D;

import java.awt.Polygon;

public abstract class PositionEntity implements Entity{

	public Vector2D position;
	
	public PositionEntity(Vector2D position){
		this.position=position;
	}
	
	public PositionEntity(double x,double y){
		position=new Vector2D(x,y);
	}
	
	public Vector2D getPosition(){
		return position;
	}
}
