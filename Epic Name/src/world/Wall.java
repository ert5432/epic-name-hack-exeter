package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import entities.GameAgent;
import geometry.Line;
import geometry.Polygon;
import geometry.Shape;
import geometry.Vector2D;
import graphics.Renderable;

public class Wall implements Renderable{

	private Line[] sides;
	private Polygon shape;
	private Vector2D[] normals;
	private Color color;
	
	public Wall(Polygon shape,Color c) {
		sides=shape.toLines();
		this.shape=shape;
		normals=calculateNormals(sides);
		color=c;
	}

	public Wall(Polygon shape){
		sides=shape.toLines();
		this.shape=shape;
		normals=calculateNormals(sides);
		color=Color.black;
	}
	
	public void render(Graphics g){
		g.setColor(color);
		shape.render(g);
	}
	
	public Shape getShape(){
		return shape;
	}
	
	public boolean contains(Vector2D point){
		return shape.toArea().contains(point.toPoint());
	}
	
	public boolean contains(Shape other){
		return shape.intersects(other);
	}
	
	public Vector2D getNormal(Shape other){
		if(!shape.intersects(other))
			return new Vector2D(0,0);
		else{
			Vector2D result=new Vector2D(0,0);
			for(int i=0;i<sides.length;i++){
				if(other.intersects(sides[i]))
					result.iadd(normals[i]);
			}
			//if shape intersects a side, return resulting normal
			if(!result.isZeroed()){
				return result.normalize();
			}
			else{
			//normal along vector from center to shape if shape is inside wall
				shape.intersects(other);
				result=other.getPosition().sub(shape.getPosition());
				if(!result.isZeroed()){
					return result.normalize();
				}
				else
					return new Vector2D(1,0);//default
			}
		}
		
	}
	
	public static Vector2D[] calculateNormals(Line[] sides){
		Vector2D[] normals=new Vector2D[sides.length];
		
		//calculate normals of sides
		for(int i=0;i<sides.length;i++){
			Vector2D ab=sides[i].getPoint2().sub(sides[i].getPoint1());//find vector from a to b
			Vector2D ac;//find vector from a to c
			if(i<sides.length-1)
				ac=sides[i+1].getPoint2().sub(sides[i].getPoint1());
			else
				ac=sides[0].getPoint1().sub(sides[i].getPoint1());
			Vector2D n=new Vector2D(ab.y,-ab.x);//take perpendicular to ab
			if(n.dot(ac)>0){
				normals[i]=n.negative().normalize();
			}
			else
				normals[i]=n.normalize();
			
		}
		return normals;
	}
	
	public void setShape(Polygon s){
		shape=s;
	}
}
