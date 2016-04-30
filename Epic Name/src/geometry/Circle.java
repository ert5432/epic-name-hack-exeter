package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Circle extends Shape{

	private double radius;
	private double rotation;
	
	public Circle(double x,double y,double radius){
		position=new Vector2D(x,y);
		this.radius=radius;
		boundingRadius=radius;
		rotation=0;
	}
	
	public Circle(double radius){
		position=new Vector2D(0,0);
		this.radius=radius;
		boundingRadius=radius;
		rotation=0;
	}

	public Area toArea() {
		return new Area(new Ellipse2D.Double(position.getX()-radius,position.getY()-radius,radius*2,radius*2));
	}

	public void rotate(double theta) {
		rotation+=theta;
		rotation=modulus(rotation, 2*Math.PI);
	}
	
	public Shape translate(Vector2D translation){
		return new Circle(position.getX()+translation.getX(),position.getY()+translation.getY(),radius);
	}
	
	public double getRadius(){
		return radius;
	}
	
	public boolean intersects(Line line){
		Vector2D toStart=position.sub(line.getPoint1());
		Vector2D toP2=line.getPoint2().sub(line.getPoint1());
		double length=toP2.magnitude();
		//normalize
		toP2.idiv(length);
		double dist=toStart.dot(toP2);
		if(dist<0)
			dist=0;
		else if(dist>length)
			dist=length;
		Vector2D closestPoint=line.getPoint1().add(toP2.mult(dist));
		Vector2D toPoint=position.sub(closestPoint);
		return toPoint.magnitudeSq()<=radius*radius;
	}
	
	public boolean includes(Vector2D point){
		return position.sub(point).magnitudeSq()<=radius*radius;
	}

	@Override
	public Shape clone() {
		return new Circle(position.x,position.y,radius);
	}
	
	public void render(Graphics g){
		g.fillOval((int)(position.getX()-radius),(int)(position.getY()-radius),(int)radius*2,(int)radius*2);
		g.setColor(Color.black);
		g.drawLine((int)position.x, (int)position.y, (int)(position.x+radius*Math.cos(rotation)), (int)(position.y+radius*Math.sin(rotation)));
	}

	@Override
	public void applyStretch(double mult) {
		radius*=mult;
		boundingRadius*=mult;
	}

	@Override
	public Shape stretch(double mult) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
