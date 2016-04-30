package geometry;

import java.awt.Graphics;

public class Line {

	private Vector2D p1,p2;
	
	public Line(Vector2D p1,Vector2D p2) {
		this.p1=p1;
		this.p2=p2;
	}

	public Vector2D[] getEndPoints(){
		return new Vector2D[]{p1,p2};
	}
	
	public Vector2D getPoint1(){
		return p1;
	}
	
	public Vector2D getPoint2(){
		return p2;
	}
	
	public boolean intersects(Line other){
		Vector2D q1=other.getPoint1(),q2=other.getPoint2();
		//orientations
		double a=Math.signum((p2.getY()-p1.getY())*(q1.getX()-p2.getX())-(q1.getY()-p2.getY())*(p2.getX()-p1.getX()));
		double b=Math.signum((p2.getY()-p1.getY())*(q2.getX()-p2.getX())-(q2.getY()-p2.getY())*(p2.getX()-p1.getX()));
		
		double m=Math.signum((q2.getY()-q1.getY())*(p1.getX()-q2.getX())-(p1.getY()-q2.getY())*(q2.getX()-q1.getX()));
		double n=Math.signum((q2.getY()-q1.getY())*(p2.getX()-q2.getX())-(p2.getY()-q2.getY())*(q2.getX()-q1.getX()));
		if(a!=b&&m!=n)
			return true;
		else if(a==0&&b==0){
			double x1=Math.min(p1.getX(), p2.getX()),x2=Math.max(p1.getX(), p2.getX()),
					x3=Math.min(q1.getX(), q2.getX()),x4=Math.max(q1.getX(), q2.getX());
			double y1=Math.min(p1.getY(), p2.getY()),y2=Math.max(p1.getY(), p2.getY()),
					y3=Math.min(q1.getY(), q2.getY()),y4=Math.max(q1.getY(), q2.getY());
			if(((x3>x1&&x3<x2)||(x4>x1&&x4<x2)||(x1>x3&&x1<x4)||(x2>x3&&x2<x4))&&
					((y3>y1&&y3<y2)||(y4>y1&&y4<y2)||(y1>y3&&y1<y4)||(y2>y3&&y2<y4)))
				return true;
		}
		return false;
	}
	
	public Vector2D nearestPoint(Vector2D point){
		double lengthSq=p1.distanceToSq(p2);
		if(lengthSq==0)
			return p1;
		double t=point.sub(p1).dot(p2.sub(p1))/lengthSq;
		if(t<0)
			return p1;
		else if(t>1)
			return p2;
		Vector2D projection=p1.add(p2.sub(p1).mult(t));
		return projection;
	}
	
	public double distanceTo(Vector2D point){
		return point.distanceTo(nearestPoint(point));
	}
	
	public double distanceToSq(Vector2D point){
		return point.distanceToSq(nearestPoint(point));
	}
	
	public String toString(){
		return "Line["+p1+","+p2+"]";
	}
	
	public void render(Graphics g){
		g.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
	}
}
